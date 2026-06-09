package Projeto_SEA.IFSP.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Projeto_SEA.IFSP.Enum.AreaAtuacao;
import Projeto_SEA.IFSP.Model.Disciplina;

@Repository
public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {
    List<Disciplina> findByArea(AreaAtuacao area);

<<<<<<< HEAD
    Page<Disciplina> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
=======
    List<Disciplina>
    findByNomeContainingIgnoreCase(
            String nome
    );
>>>>>>> 2b6f2445d58bf2d1a27559a6f6321a82de3ff9df
}
