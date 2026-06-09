package Projeto_SEA.IFSP.Repository;

<<<<<<< HEAD
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
=======
import java.util.List;

>>>>>>> 2b6f2445d58bf2d1a27559a6f6321a82de3ff9df
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Projeto_SEA.IFSP.Model.Turma;

@Repository
public interface TurmaRepository extends JpaRepository<Turma, Long>{
<<<<<<< HEAD
    Page<Turma> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
=======
    List<Turma> findByNomeContainingIgnoreCase(
        String nome
    );
>>>>>>> 2b6f2445d58bf2d1a27559a6f6321a82de3ff9df
}
