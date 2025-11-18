package br.com.criandoapi.projeto.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "Alunos")
public class Alunos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nome", length = 200, nullable = false)
    private String nome;

    // notas das matérias (0 a 10, mas sem validação automática)
    @Column(name = "portugues")
    private Integer portugues;

    @Column(name = "matematica")
    private Integer matematica;

    @Column(name = "ciencias")
    private Integer ciencias;

    @Column(name = "geografia")
    private Integer geografia;

    @Column(name = "historia")
    private Integer historia;

    // frequência (pode ser percentual ou número de faltas)
    @Column(name = "frequencia")
    private Integer frequencia;

    // getters e setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Integer getPortugues() { return portugues; }
    public void setPortugues(Integer portugues) { this.portugues = portugues; }

    public Integer getMatematica() { return matematica; }
    public void setMatematica(Integer matematica) { this.matematica = matematica; }

    public Integer getCiencias() { return ciencias; }
    public void setCiencias(Integer ciencias) { this.ciencias = ciencias; }

    public Integer getGeografia() { return geografia; }
    public void setGeografia(Integer geografia) { this.geografia = geografia; }

    public Integer getHistoria() { return historia; }
    public void setHistoria(Integer historia) { this.historia = historia; }

    public Integer getFrequencia() { return frequencia; }
    public void setFrequencia(Integer frequencia) { this.frequencia = frequencia; }
}
