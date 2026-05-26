package Projeto_SEA.IFSP.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import Projeto_SEA.IFSP.Model.Professor;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    List<Professor> findByNomeContainingIgnoreCase(
        String nome
    );
}
