package Projeto_SEA.IFSP.Model;

import Projeto_SEA.IFSP.Enum.TipoUsuario;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@MappedSuperclass
public abstract class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    protected Long id;

    @Column(name = "nome")
    @NotBlank(message = "Nome é obrigatório")
    protected String nome;

    @Column(name = "email")
    @NotBlank(message = "E-mail é obrigatório")
    @Email(message = "E-mail inválido")
    protected String email;

    @Column(name = "prontuario")
    @NotBlank(message = "Prontuário é obrigatório")
    protected String prontuario;

    @Column(name = "senha")
    @NotBlank(message = "Senha é obrigatória")
    protected String senha;

    @Column(name = "imagem")
    protected String imagem;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_usuario")
    protected TipoUsuario tipoUsuario;

    public Usuario() {

    }

    public Usuario(Long id, String nome, String email, String prontuario, String senha, String imagem, TipoUsuario tipoUsuario) {
        
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.prontuario = prontuario;
        this.senha = senha;
        this.imagem = imagem;
        this.tipoUsuario = tipoUsuario;
    }

    // GETTERS E SETTERS

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

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}