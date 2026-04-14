package Projeto_SEA.IFSP.Model;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "cae")
public class Cae {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cae")
    private Long id_cae;
    
    @Column(name = "nome_cae")
    @NotBlank(message = "Nome é obrigatório")
    private String nome;
    
    @Column(name = "email_cae")
    @NotBlank(message = "E-mail é obrigatório")
    @Email(message = "E-mail inválido")
    private String email;

    @Column(name = "prontuario_cae")
    @NotBlank(message = "Prontuário é obrigatório")
    private String prontuario;

    @Column(name = "senha_cae")
    @NotBlank(message = "Senha é obrigatório")
    private String senha;

    @Column(name = "img_cae")
    private String img_cae;
    
    @Transient
    private MultipartFile file;

    public Cae(){

    }
    public Cae(Long id_cae, String nome, String email, String prontuario, String senha, String img_cae){
        this.id_cae = id_cae;
        this.nome = nome;
        this.email = email;
        this.prontuario = prontuario;
        this.senha = senha;
        this.img_cae = img_cae;
    }

    public Long getId_cae() {
        return id_cae;
    }

    public void setId_cae(Long id_cae) {
        this.id_cae = id_cae;
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

    public String getImg_cae() {
        return img_cae;
    }

    public void setImg_cae(String img_cae) {
        this.img_cae = img_cae;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
