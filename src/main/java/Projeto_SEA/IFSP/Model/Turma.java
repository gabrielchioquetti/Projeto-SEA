package Projeto_SEA.IFSP.Model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class Turma {

@Id
@GeneratedValue
private Long id;

private String nome;

@Enumerated(EnumType.STRING)
private Periodo periodo;

private String curso;

@ManyToOne
private Sala sala;

@ManyToMany
@JoinTable(
    name = "turma_disciplina",
    joinColumns = @JoinColumn(name = "turma_id"),
    inverseJoinColumns = @JoinColumn(name = "disciplina_id")
)
private List<Disciplina> disciplinas = new ArrayList<>();


public Turma() {

}

public Turma(Long id, String nome, Periodo periodo, String curso, Sala sala, List<Disciplina> disciplinas) {
    this.id=id;
    this.nome=nome;
    this.periodo=periodo;
    this.curso=curso;
    this.sala=sala;
    this.disciplinas=disciplinas;
}

public Long getId() {
    return id;
}

public void setId(Long id) {
    this.id = id;
}

public String getNome() {
    return nome;
}

public void setNome(String nome) {
    this.nome = nome;
}

public Periodo getPeriodo() {
    return periodo;
}

public void setPeriodo(Periodo periodo) {
    this.periodo = periodo;
}

public String getCurso() {
    return curso;
}

public void setCurso(String curso) {
    this.curso = curso;
}

public Sala getSala() {
    return sala;
}

public void setSala(Sala sala) {
    this.sala = sala;
}

public List<Disciplina> getDisciplinas() {
    return disciplinas;
}

public void setDisciplinas(List<Disciplina> disciplinas) {
    this.disciplinas = disciplinas;
}

}





