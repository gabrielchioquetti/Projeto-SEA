package Projeto_SEA.IFSP.Model;

import java.util.List;

import Projeto_SEA.IFSP.Enum.AreaAtuacao;
import Projeto_SEA.IFSP.Enum.TipoUsuario;
import jakarta.persistence.*;

@Entity
@Table(name = "professores")
public class Professor extends Usuario {

    @Enumerated(EnumType.STRING)
    private AreaAtuacao area;

    @ManyToMany
    @JoinTable(
        name = "disciplina_professor",
        joinColumns = @JoinColumn(name = "id_professor"),
        inverseJoinColumns = @JoinColumn(name = "id_disciplina")
    )
    private List<Disciplina> disciplinas;

    public Professor() {

    }

    public Professor(Long id, String nome, String email, String prontuario,
                     String senha, String imagem,
                     AreaAtuacao area) {

        super(id, nome, email, prontuario, senha, imagem, TipoUsuario.PROFESSOR);

        this.area = area;
    }

    public AreaAtuacao getArea() {
        return area;
    }

    public void setArea(AreaAtuacao area) {
        this.area = area;
    }

    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(List<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }
}