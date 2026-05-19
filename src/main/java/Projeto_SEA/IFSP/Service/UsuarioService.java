package Projeto_SEA.IFSP.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import Projeto_SEA.IFSP.Model.Usuario;
import Projeto_SEA.IFSP.Repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void salvar(Usuario usuario) {

        usuario.setSenha(
                passwordEncoder.encode(usuario.getSenha())
        );

        usuarioRepository.save(usuario);
    }

    public Usuario buscarPorEmail(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buscarPorEmail'");
    }
}
