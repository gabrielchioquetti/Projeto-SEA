package Projeto_SEA.IFSP.Repository;

import org.springframework.stereotype.Repository;

import Projeto_SEA.IFSP.Model.Disciplina;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
public class DisciplinaRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(Disciplina disciplina){
        Query query = em.createNativeQuery("INSERT INTO disciplinas (nome_disciplina,carga_horaria) VALUES(:nome_disciplina,:carga_horaria)");
        query.setParameter("nome_disciplina", disciplina.getNome());
        query.setParameter("carga_horaria", disciplina.getCarga());
        query.executeUpdate();
    }
}
