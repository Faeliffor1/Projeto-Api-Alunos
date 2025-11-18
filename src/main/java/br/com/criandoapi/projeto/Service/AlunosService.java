package br.com.criandoapi.projeto.Service;

import br.com.criandoapi.projeto.DAO.AlunosDAO;
import br.com.criandoapi.projeto.Model.Alunos;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AlunosService {

    private final AlunosDAO alunosDAO;

    public AlunosService(AlunosDAO alunosDAO) {
        this.alunosDAO = alunosDAO;
    }

    // CRUD básico
    public List<Alunos> listarAlunos() {
        return alunosDAO.findAll();
    }

    public Alunos salvarAluno(Alunos aluno) {
        return alunosDAO.save(aluno);
    }

    public Alunos editarAluno(Alunos aluno) {
        return alunosDAO.save(aluno);
    }

    public void excluirAluno(Integer id) {
        alunosDAO.deleteById(id);
    }

    // ✅ Regras de negócio

    // Média individual (tratando nulos)
    public double calcularMediaAluno(Alunos aluno) {
        double soma = 0;
        int count = 0;

        if (aluno.getPortugues() != null) { soma += aluno.getPortugues(); count++; }
        if (aluno.getMatematica() != null) { soma += aluno.getMatematica(); count++; }
        if (aluno.getCiencias() != null) { soma += aluno.getCiencias(); count++; }
        if (aluno.getGeografia() != null) { soma += aluno.getGeografia(); count++; }
        if (aluno.getHistoria() != null) { soma += aluno.getHistoria(); count++; }

        return count > 0 ? soma / count : 0;
    }

    public double calcularMediaDisciplina(String disciplina) {
        List<Alunos> alunos = alunosDAO.findAll();
        if (alunos.isEmpty()) return 0;

        return switch (disciplina.toLowerCase()) {
            case "portugues" -> alunos.stream()
                    .mapToDouble(a -> a.getPortugues() != null ? a.getPortugues() : 0)
                    .average().orElse(0);
            case "matematica" -> alunos.stream()
                    .mapToDouble(a -> a.getMatematica() != null ? a.getMatematica() : 0)
                    .average().orElse(0);
            case "ciencias" -> alunos.stream()
                    .mapToDouble(a -> a.getCiencias() != null ? a.getCiencias() : 0)
                    .average().orElse(0);
            case "geografia" -> alunos.stream()
                    .mapToDouble(a -> a.getGeografia() != null ? a.getGeografia() : 0)
                    .average().orElse(0);
            case "historia" -> alunos.stream()
                    .mapToDouble(a -> a.getHistoria() != null ? a.getHistoria() : 0)
                    .average().orElse(0);
            default -> 0;
        };
    }



    public Map<String, Double> calcularMediasDisciplinas() {
        Map<String, Double> medias = new HashMap<>();
        medias.put("portugues", calcularMediaDisciplina("portugues"));
        medias.put("matematica", calcularMediaDisciplina("matematica"));
        medias.put("ciencias", calcularMediaDisciplina("ciencias"));
        medias.put("geografia", calcularMediaDisciplina("geografia"));
        medias.put("historia", calcularMediaDisciplina("historia"));
        return medias;
    }





    // Média geral da turma
    public double calcularMediaTurma() {
        List<Alunos> alunos = alunosDAO.findAll();
        if (alunos.isEmpty()) {
            return 0; // evita erro quando não há alunos
        }
        return alunos.stream()
                .mapToDouble(this::calcularMediaAluno)
                .average()
                .orElse(0);
    }

    // Alunos acima da média da turma
    public List<Alunos> alunosAcimaDaMedia() {
        double mediaTurma = calcularMediaTurma();
        return alunosDAO.findAll().stream()
                .filter(a -> calcularMediaAluno(a) > mediaTurma)
                .collect(Collectors.toList());
    }

    // Alunos com frequência abaixo de 75%
    public List<Alunos> alunosComFrequenciaBaixa() {
        return alunosDAO.findAll().stream()
                .filter(a -> a.getFrequencia() != null && a.getFrequencia() < 75)
                .collect(Collectors.toList());
    }
}
