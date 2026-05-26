package Projeto_SEA.IFSP.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Projeto_SEA.IFSP.Enum.AreaAtuacao;
import Projeto_SEA.IFSP.Model.Disciplina;

@Repository
public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {
    List<Disciplina> findByArea(AreaAtuacao area);

    List<Disciplina>
    findByNomeContainingIgnoreCase(
            String nome
    );
}
