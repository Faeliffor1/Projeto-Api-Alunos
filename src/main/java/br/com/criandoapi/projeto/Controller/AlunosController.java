package br.com.criandoapi.projeto.Controller;

import br.com.criandoapi.projeto.Model.Alunos;
import br.com.criandoapi.projeto.Service.AlunosService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/alunos")
public class AlunosController {

    private final AlunosService alunosService;

    public AlunosController(AlunosService alunosService) {
        this.alunosService = alunosService;
    }

    @GetMapping
    public List<Alunos> listaUsuarios() {
        return alunosService.listarAlunos();
    }

    @PostMapping
    public Alunos LancarNotas(@RequestBody Alunos alunos) {
        return alunosService.salvarAluno(alunos);
    }

    @PutMapping
    public Alunos EditarNotas(@RequestBody Alunos alunos) {
        return alunosService.editarAluno(alunos);
    }

    @DeleteMapping("/{id}")
    public void ExcluirNota(@PathVariable Integer id) {
        alunosService.excluirAluno(id);
    }

    // ✅ Endpoints extras com regras de negócio
    @GetMapping("/media-turma")
    public double mediaTurma() {
        return alunosService.calcularMediaTurma();
    }

    @GetMapping("/acima-media")
    public List<Alunos> acimaMedia() {
        return alunosService.alunosAcimaDaMedia();
    }

    @GetMapping("/frequencia-baixa")
    public List<Alunos> frequenciaBaixa() {
        return alunosService.alunosComFrequenciaBaixa();
    }
    @GetMapping("/medias-disciplinas")
    public Map<String, Double> mediasDisciplinas() {
        return alunosService.calcularMediasDisciplinas();
    }


    @Configuration
    public class CorsConfig {
        @Bean
        public WebMvcConfigurer corsConfigurer() {
            return new WebMvcConfigurer() {
                @Override
                public void addCorsMappings(CorsRegistry registry) {
                    registry.addMapping("/**")
                            .allowedOrigins("*")
                            .allowedMethods("GET","POST","PUT","DELETE");
                }
            };
        }
    }

}
