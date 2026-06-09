package Projeto_SEA.IFSP.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Projeto_SEA.IFSP.Model.Turma;

@Repository
public interface TurmaRepository extends JpaRepository<Turma, Long>{
    Page<Turma> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}
