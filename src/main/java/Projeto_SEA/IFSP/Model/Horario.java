package Projeto_SEA.IFSP.Model;

import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "horarios")
public class Horario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "dia_semana")
    @NotNull(message = "Dia da semana é obrigatório")
    private DiaSemana diaSemana;

    @Column(name = "hora_inicio")
    @NotNull(message = "Hora de início é obrigatória")
    private LocalTime horaInicio;

    @Column(name = "hora_fim")
    @NotNull(message = "Hora de fim é obrigatória")
    private LocalTime horaFim;

    @ManyToOne
    @JoinColumn(name = "turma_id")
    @NotNull(message = "Turma é obrigatória")
    private Turma turma;

    @ManyToOne
    @JoinColumn(name = "disciplina_id")
    @NotNull(message = "Disciplina é obrigatória")
    private Disciplina disciplina;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    @NotNull(message = "Professor é obrigatório")
    private Professor professor;

    @ManyToOne
    @JoinColumn(name = "sala_id")
    @NotNull(message = "Sala é obrigatória")
    private Sala sala;

    public Horario() {}

    public Horario(Long id, DiaSemana diaSemana, LocalTime horaInicio, LocalTime horaFim,
                   Turma turma, Disciplina disciplina, Professor professor, Sala sala) {
        this.id = id;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.turma = turma;
        this.disciplina = disciplina;
        this.professor = professor;
        this.sala = sala;
    }

    @AssertTrue(message = "Hora fim deve ser depois da hora início")
    public boolean isHorarioValido() {
        if (horaInicio == null || horaFim == null) return true;
        return horaFim.isAfter(horaInicio);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DiaSemana getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(DiaSemana diaSemana) {
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