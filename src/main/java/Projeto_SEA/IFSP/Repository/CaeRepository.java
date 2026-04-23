package Projeto_SEA.IFSP.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Projeto_SEA.IFSP.Model.Cae;

@Repository
public interface CaeRepository extends JpaRepository<Cae, Long> {
}
