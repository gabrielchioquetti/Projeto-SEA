package Projeto_SEA.IFSP.Repository;

import java.time.LocalTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import Projeto_SEA.IFSP.Model.DiaSemana;
import Projeto_SEA.IFSP.Model.Horario;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Long>{
    @Query("""
        SELECT COUNT(h) > 0 FROM Horario h
        WHERE h.diaSemana = :dia
        AND (
            (:inicio < h.horaFim AND :fim > h.horaInicio)
        )
        AND (
            h.sala.id_sala = :salaId
            OR h.professor.id_professor = :professorId
            OR h.turma.id = :turmaId
        )
    """)
    boolean existeConflito(
        DiaSemana dia,
        LocalTime inicio,
        LocalTime fim,
        Long salaId,
        Long professorId,
        Long turmaId
    );
}
