package Projeto_SEA.IFSP.Repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Projeto_SEA.IFSP.Model.Aluno;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long>{
    Aluno findByEmail(String email);
    Page<Aluno> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    Optional<Aluno> findByProntuario(
        String prontuario);
}
