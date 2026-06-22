package Projeto_SEA.IFSP.Repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Projeto_SEA.IFSP.Model.Cae;

@Repository
public interface CaeRepository extends JpaRepository<Cae, Long> {
    Page<Cae> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    Optional<Cae> findByProntuario(
        String prontuario);
}
