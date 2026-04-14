package Projeto_SEA.IFSP.Repository;

import org.springframework.stereotype.Repository;

import Projeto_SEA.IFSP.Model.Cae;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
public class CaeRepository {
    
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(Cae cae){
        Query query = em.createNativeQuery("INSERT INTO cae (nome_cae,email_cae,prontuario_cae,senha_cae,img_cae) VALUES(:nome_cae,:email_cae,:prontuario_cae, :senha_cae, :img_cae)");
        query.setParameter("nome_cae", cae.getNome());
        query.setParameter("email_cae", cae.getEmail());
        query.setParameter("prontuario_cae", cae.getProntuario());
        query.setParameter("senha_cae", cae.getSenha());
        query.setParameter("img_cae", cae.getImg_cae());
        query.executeUpdate();
    }
}
