package Projeto_SEA.IFSP.Model;

import Projeto_SEA.IFSP.Enum.TipoUsuario;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "alunos")
public class Aluno extends Usuario {

    @ManyToOne
    @JoinColumn(name = "id_turma")
    private Turma turma;

    public Aluno() {

    }

    public Aluno(Long id, String nome, String email, String prontuario,
                 String senha, String imagem, Turma turma) {

        super(id, nome, email, prontuario, senha, imagem, TipoUsuario.ALUNO);

        this.turma = turma;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }
}