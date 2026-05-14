package Projeto_SEA.IFSP.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import Projeto_SEA.IFSP.Enum.TipoUsuario;
import Projeto_SEA.IFSP.Model.Aluno;
import Projeto_SEA.IFSP.Model.Usuario;

@Controller
@RequestMapping("/perfil")
public class PerfilController {

    @GetMapping
    public String perfil(Model model) {

        // MOCK TEMPORÁRIO

        Aluno aluno = new Aluno();

        aluno.setNome("João");
        aluno.setEmail("joao@ifsp.edu.br");
        aluno.setProntuario("SP123456");
        aluno.setTipoUsuario(TipoUsuario.ALUNO);

        model.addAttribute("usuario", aluno);

        return "perfil/perfil";
    }
}