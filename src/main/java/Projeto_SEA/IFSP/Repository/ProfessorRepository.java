package Projeto_SEA.IFSP.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import Projeto_SEA.IFSP.Model.Professor;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {

    Page<Professor> findByNomeContainingIgnoreCase(
        String nome,
        Pageable pageable
);

}
