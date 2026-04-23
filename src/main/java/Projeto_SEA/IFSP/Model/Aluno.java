package Projeto_SEA.IFSP.Model;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "alunos")
public class Aluno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_aluno")
    private Long id_aluno;
    
    @Column(name = "nome_aluno")
    @NotBlank(message = "Nome é obrigatório")
    private String nome;
    
    @Column(name = "email_aluno")
    @NotBlank(message = "E-mail é obrigatório")
    @Email(message = "E-mail inválido")
    private String email;

    @Column(name = "prontuario_aluno")
    @NotBlank(message = "Prontuário é obrigatório")
    private String prontuario;

    @Column(name = "senha_aluno")
    @NotBlank(message = "Senha é obrigatório")
    private String senha;

    @Column(name = "img_aluno")
    private String img_aluno;

    @ManyToOne
    @JoinColumn(name = "id_turma")
    private Turma turma;

    @Transient
    private MultipartFile file;

    public Aluno(){

    }
    
    public Aluno(long id_aluno, String nome, String email, String prontuario, String senha, String img_aluno, Turma turma){
        this.id_aluno = id_aluno;
        this.nome = nome;
        this.email = email;
        this.prontuario = prontuario;
        this.senha = senha;
        this.img_aluno = img_aluno;
        this.turma=turma;
    }

    public long getId_aluno() {
        return id_aluno;
    }
    public void setId_aluno(long id_aluno) {
        this.id_aluno = id_aluno;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getProntuario() {
        return prontuario;
    }
    public void setProntuario(String prontuario) {
        this.prontuario = prontuario;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public String getImg_aluno() {
        return img_aluno;
    }
    public void setImg_aluno(String img_aluno) {
        this.img_aluno = img_aluno;
    }
    public Turma getTurma() {
        return turma;
    }
    public void setTurma(Turma turma) {
        this.turma = turma;
    }
}
