package Projeto_SEA.IFSP.Model;

import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Horario {

    @Id
    @GeneratedValue
    private Long id;

    private String diaSemana;
    private LocalTime horaInicio;
    private LocalTime horaFim;

    @ManyToOne
    private Turma turma;

    @ManyToOne
    private Disciplina disciplina;

    @ManyToOne
    private Professor professor;

    @ManyToOne
    private Sala sala;

     public Horario(){

    }

    public Horario(Long id, String diaSemana, LocalTime horaInicio, LocalTime horaFim, Turma turma, Disciplina disciplina, Professor professor, Sala sala){
        this.id=id;
        this.diaSemana=diaSemana;
        this.horaInicio=horaInicio;
        this.horaFim=horaFim;
        this.turma=turma;
        this.disciplina=disciplina;
        this.professor=professor;
        this.sala=sala;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(LocalTime horaFim) {
        this.horaFim = horaFim;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }
}
