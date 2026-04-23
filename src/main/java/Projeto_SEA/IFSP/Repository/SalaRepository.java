package Projeto_SEA.IFSP.Repository;

import org.springframework.stereotype.Repository;

import Projeto_SEA.IFSP.Model.Sala;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
public class SalaRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save (Sala sala){
        Query query = em.createNativeQuery( "INSERT INTO salas (nome, capacidade, tipo) VALUES (:nome, :capacidade, :tipo)");
        query.setParameter("nome", sala.getNome());
        query.setParameter("capacidade", sala.getCapacidade());
        query.setParameter("tipo", sala.getTipo());

        query.executeUpdate();
    }
    
}
