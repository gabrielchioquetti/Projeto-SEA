package Projeto_SEA.IFSP.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Projeto_SEA.IFSP.Model.Aluno;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long>{
    Aluno findByEmail(String email);
}
