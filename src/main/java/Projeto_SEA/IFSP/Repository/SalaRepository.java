package Projeto_SEA.IFSP.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Projeto_SEA.IFSP.Model.Sala;

@Repository
public interface SalaRepository extends JpaRepository<Sala, Long>{
    Page<Sala> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}
