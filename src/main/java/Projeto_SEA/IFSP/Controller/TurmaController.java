package Projeto_SEA.IFSP.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import Projeto_SEA.IFSP.Model.Turma;
import Projeto_SEA.IFSP.Repository.TurmaRepository;
import jakarta.validation.Valid;

@Controller
public class TurmaController {

    @Autowired
    private TurmaRepository turmaRepository;
    
    @GetMapping("/cadastrar/turma")
    public String cadastrarTurma(Turma turma) {
        return "admin/cadastrar-turma";
    }

    @PostMapping("/cadastrar/turma")
    public String cadastroTurma(@Valid @ModelAttribute Turma turma) {

        turmaRepository.save(turma);
        return "redirect:/admin/dasboard";
    }

}
