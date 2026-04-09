package Projeto_SEA.IFSP.Repository;

import org.springframework.stereotype.Repository;

import Projeto_SEA.IFSP.Model.Aluno;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
public class AlunoRepository {
    
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(Aluno aluno){
        Query query = em.createNativeQuery("INSERT INTO alunos (nome_aluno,email_aluno,prontuario_aluno,senha_aluno,img_aluno) VALUES(:nome_aluno,:email_aluno,:prontuario_aluno, :senha_aluno, :img_aluno)");
        query.setParameter("nome_aluno", aluno.getNome());
        query.setParameter("email_aluno", aluno.getEmail());
        query.setParameter("prontuario_aluno", aluno.getProntuario());
        query.setParameter("senha_aluno", aluno.getSenha());
        query.setParameter("img_aluno", aluno.getImg_aluno());
        query.executeUpdate();
    }
}
