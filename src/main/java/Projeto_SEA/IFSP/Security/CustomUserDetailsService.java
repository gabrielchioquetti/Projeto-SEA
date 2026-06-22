package Projeto_SEA.IFSP.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import Projeto_SEA.IFSP.Model.Usuario;
import Projeto_SEA.IFSP.Repository.AlunoRepository;
import Projeto_SEA.IFSP.Repository.CaeRepository;
import Projeto_SEA.IFSP.Repository.ProfessorRepository;

@Service
public class CustomUserDetailsService
        implements UserDetailsService {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private CaeRepository caeRepository;

    

    @Override
    public UserDetails loadUserByUsername(
            String username)
            throws UsernameNotFoundException {

                System.out.println(
            "LOGIN RECEBIDO: " + username);

        Usuario usuario = alunoRepository
                .findByProntuario(username)
                .orElse(null);

        if (usuario == null) {
            usuario = professorRepository
                    .findByProntuario(username)
                    .orElse(null);
        }

        if (usuario == null) {
            usuario = caeRepository
                    .findByProntuario(username)
                    .orElse(null);
        }

        if (usuario == null) {
            throw new UsernameNotFoundException(
                    "Usuário não encontrado");
        }

        return User.builder()
                .username(usuario.getProntuario())
                .password(usuario.getSenha())
                .roles(usuario.getTipoUsuario().name())
                .build();
    }
}
