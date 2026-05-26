package Projeto_SEA.IFSP.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Projeto_SEA.IFSP.Model.Turma;

@Repository
public interface TurmaRepository extends JpaRepository<Turma, Long>{
    List<Turma> findByNomeContainingIgnoreCase(
        String nome
    );
}
