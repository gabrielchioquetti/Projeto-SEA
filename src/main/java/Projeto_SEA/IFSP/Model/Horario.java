package Projeto_SEA.IFSP.Model;

import java.sql.Time;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "horarios")
public class Horario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_horario")
    private Long id_horario;

    @Column(name = "periodo")
    @NotBlank
    private String periodo;

    @Column(name = "p_aula")
    @NotNull
    private LocalTime p_aula;

    @Column(name = "sg_aula")
    @NotNull
    private LocalTime sg_aula;

    @Column(name = "t_aula")
    @NotNull
    private LocalTime t_aula;

    @Column(name = "qa_aula")
    @NotNull
    private LocalTime qa_aula;

    @Column(name = "qi_aula", nullable = true)
    private LocalTime qi_aula;

    @Column(name = "sx_aula", nullable = true)
    private LocalTime sx_aula;

     public Horario(){

    }

    public Horario(Long id_horario, String periodo, LocalTime p_aula, LocalTime sg_aula, LocalTime t_aula, LocalTime qa_aula, LocalTime qi_aula, LocalTime sx_aula){
        this.id_horario=id_horario;
        this.periodo=periodo;
        this.p_aula=p_aula;
        this.sg_aula=sg_aula;
        this.t_aula=t_aula;
        this.qa_aula=qa_aula;
        this.qi_aula=qi_aula;
        this.sx_aula=sx_aula;

    }

    public Long getId_horario() {
        return id_horario;
    }

    public void setId_horario(Long id_horario) {
        this.id_horario = id_horario;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public LocalTime getP_aula() {
        return p_aula;
    }

    public void setP_aula(LocalTime p_aula) {
        this.p_aula = p_aula;
    }

    public LocalTime getSg_aula() {
        return sg_aula;
    }

    public void setSg_aula(LocalTime sg_aula) {
        this.sg_aula = sg_aula;
    }

    public LocalTime getT_aula() {
        return t_aula;
    }

    public void setT_aula(LocalTime t_aula) {
        this.t_aula = t_aula;
    }

    public LocalTime getQa_aula() {
        return qa_aula;
    }

    public void setQa_aula(LocalTime qa_aula) {
        this.qa_aula = qa_aula;
    }

    public LocalTime getQi_aula() {
        return qi_aula;
    }

    public void setQi_aula(LocalTime qi_aula) {
        this.qi_aula = qi_aula;
    }

    public LocalTime getSx_aula() {
        return sx_aula;
    }

    public void setSx_aula(LocalTime sx_aula) {
        this.sx_aula = sx_aula;
    }
}
