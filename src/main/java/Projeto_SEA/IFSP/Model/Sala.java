package Projeto_SEA.IFSP.Model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table (name = "salas")
public class Sala {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sala")
    private Long id_sala;

    @Column(name = "nome")
    @NotBlank(message = "Nome é obrigatório!")
    private String nome;

    @Column(name = "capacidade")
    @NotNull
    @Min(1)
    @Max(40)
    private int capacidade;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    @NotNull(message = "Tipo é obrigatório")
    private TipoSala tipo;

    @OneToMany(mappedBy = "sala")
    @JsonIgnore
    private List<Turma> turmas = new ArrayList<>();

    public Sala(){}

    public Sala (Long id_sala, String nome, int capacidade, TipoSala tipo){
        this.id_sala = id_sala;
        this.nome = nome;
        this.capacidade = capacidade;
        this.tipo = tipo;
    }

    public Long getId_sala() {
        return id_sala;
    }

    public void setId_sala(Long id_sala) {
        this.id_sala = id_sala;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public TipoSala getTipo() {
        return tipo;
    }

    public void setTipo(TipoSala tipo) {
        this.tipo = tipo;
    }
    
}
