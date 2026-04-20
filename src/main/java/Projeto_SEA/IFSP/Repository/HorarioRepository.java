package Projeto_SEA.IFSP.Repository;

import org.springframework.stereotype.Repository;

import Projeto_SEA.IFSP.Model.Horario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
public class HorarioRepository {
    
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save (Horario horario){
        Query query = em.createNativeQuery( "INSERT INTO horarios (periodo, p_aula, sg_aula, t_aula, qa_aula, qi_aula, sx_aula) VALUES (:periodo, :p_aula, :sg_aula, :t_aula, :qa_aula, :qi_aula, :sx_aula)");
        query.setParameter("periodo", horario.getPeriodo());
        query.setParameter("p_aula", horario.getP_aula());
        query.setParameter("sg_aula", horario.getSg_aula());
        query.setParameter("t_aula", horario.getT_aula());
        query.setParameter("qa_aula", horario.getQa_aula());
        query.setParameter("qi_aula", horario.getQi_aula());
        query.setParameter("sx_aula", horario.getSx_aula());

        query.executeUpdate();
    }
}
