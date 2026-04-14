package Projeto_SEA.IFSP.Repository;

import org.springframework.stereotype.Repository;

import Projeto_SEA.IFSP.Model.Professor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
public class ProfessorRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(Professor professor){
        Query query = em.createNativeQuery("INSERT INTO professores (nome_professor,email_professor,prontuario_professor,senha_professor,area_atuacao,img_professor) VALUES(:nome_professor,:email_professor,:prontuario_professor, :senha_professor, :area_atuacao, :img_professor)");
        query.setParameter("nome_professor", professor.getNome());
        query.setParameter("email_professor", professor.getEmail());
        query.setParameter("prontuario_professor", professor.getProntuario());
        query.setParameter("senha_professor", professor.getSenha());
        query.setParameter("area_atuacao", professor.getArea());
        query.setParameter("img_professor", professor.getImg_professor());
        query.executeUpdate();
    }
    
}
