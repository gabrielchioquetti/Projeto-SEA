package Projeto_SEA.IFSP.Repository;

import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import Projeto_SEA.IFSP.Enum.DiaSemana;
import Projeto_SEA.IFSP.Model.Horario;
import Projeto_SEA.IFSP.Model.Professor;
import Projeto_SEA.IFSP.Model.Sala;
import Projeto_SEA.IFSP.Model.Turma;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Long> {

    

    @Query("""
                SELECT COUNT(h) > 0
                FROM Horario h
                WHERE h.diaSemana = :dia

                AND (
                    (h.sala.id_sala = :salaId
                     AND :inicio < h.horaFim
                     AND :fim > h.horaInicio)

                    OR

                    (h.professor.id = :professorId
                     AND :inicio < h.horaFim
                     AND :fim > h.horaInicio)

                    OR

                    (h.turma.id = :turmaId
                     AND :inicio < h.horaFim
                     AND :fim > h.horaInicio)
                )
            """)
    boolean existeConflito(
            DiaSemana dia,
            LocalTime inicio,
            LocalTime fim,
            Long salaId,
            Long professorId,
            Long turmaId);

    @Transactional
    @Modifying
    void deleteByProfessor(Professor professor);

    @Transactional
    @Modifying
    void deleteBySala(Sala sala);

    List<Horario> findByTurma(Turma turma);

    List<Horario> findByProfessor(Professor professor);

    List<Horario> findByProfessorAndDiaSemana(
            Professor professor,
            DiaSemana diaSemana);

    List<Horario> findByDiaSemanaOrderByHoraInicio(DiaSemana hoje);

    List<Horario> findAllByOrderByDiaSemanaAscHoraInicioAsc();

    List<Horario> findByTurmaIdOrderByDiaSemanaAscHoraInicioAsc(Long turmaId);

    List<Horario> findByProfessorIdOrderByDiaSemanaAscHoraInicioAsc(Long professorId);
}