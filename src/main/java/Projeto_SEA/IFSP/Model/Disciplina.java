package Projeto_SEA.IFSP.Model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "disciplinas")
public class Disciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_disciplina")
    private Long idDisciplina;

    @Column(name = "nome_disciplina")
    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @Column(name = "carga_horaria")
    @NotNull(message = "Carga horária é obrigatória")
    @Min(value = 1, message = "Carga horária deve ser maior que 0")
    private Integer carga;

    @ManyToMany(mappedBy = "disciplinas")
    @JsonIgnore
    private List<Professor> professores = new ArrayList<>();

    @ManyToMany(mappedBy = "disciplinas")
    @JsonIgnore
    private List<Turma> turmas = new ArrayList<>();

    public Disciplina() {}

    public Disciplina(String nome, Integer carga) {
        this.nome = nome;
        this.carga = carga;
    }

    public Long getIdDisciplina() {
        return idDisciplina;
    }

    public void setIdDisciplina(Long idDisciplina) {
        this.idDisciplina = idDisciplina;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getCarga() {
        return carga;
    }

    public void setCarga(Integer carga) {
        this.carga = carga;
    }

    public List<Professor> getProfessores() {
        return professores;
    }

    public void setProfessores(List<Professor> professores) {
        this.professores = professores;
    }
}
