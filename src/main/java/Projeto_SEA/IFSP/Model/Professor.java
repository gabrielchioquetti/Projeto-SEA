package Projeto_SEA.IFSP.Model;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "professores")
public class Professor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_professor")
    private Long id_professor;
    
    @Column(name = "nome_professor")
    @NotBlank(message = "Nome é obrigatório")
    private String nome;
    
    @Column(name = "email_professor")
    @NotBlank(message = "E-mail é obrigatório")
    @Email(message = "E-mail inválido")
    private String email;

    @Column(name = "prontuario_professor")
    @NotBlank(message = "Prontuário é obrigatório")
    private String prontuario;

    @Column(name = "senha_professor")
    @NotBlank(message = "Senha é obrigatório")
    private String senha;

    @Column(name = "area_atuacao")
    @NotBlank(message = "Área de atuação é obrigatório")
    private String area;

    @Column(name = "img_professor")
    private String img_professor;
    
    @Transient
    private MultipartFile file;

    @ManyToMany
    @JoinTable(
        name = "disciplina_professor",
        joinColumns = @JoinColumn(name = "id_professor"),
        inverseJoinColumns = @JoinColumn(name = "id_disciplina")
    )
    @NotEmpty(message = "Selecione pelo menos uma disciplina")
    private List<Disciplina> disciplinas;

    public Professor(){

    }

    public Professor(String nome, String email, String prontuario, String senha, String area, String img_professor){
        this.nome = nome;
        this.email = email;
        this.prontuario = prontuario;
        this.senha = senha;
        this.area = area;
        this.img_professor = img_professor;
    }

    public Long getId_professor() {
        return id_professor;
    }

    public void setId_professor(Long id_professor) {
        this.id_professor = id_professor;
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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getImg_professor() {
        return img_professor;
    }

    public void setImg_professor(String img_professor) {
        this.img_professor = img_professor;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(List<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }
}
