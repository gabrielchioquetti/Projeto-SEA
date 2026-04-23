package Projeto_SEA.IFSP.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import Projeto_SEA.IFSP.Model.Disciplina;
import Projeto_SEA.IFSP.Repository.DisciplinaRepository;
import jakarta.validation.Valid;

@Controller
public class DisciplinaController {

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @GetMapping("/cadastrar/disciplina")
    public String cadastrarDisciplina(Model model){
        model.addAttribute("disciplina", new Disciplina());
        return "admin/cadastrar-disciplina";
    }

    @PostMapping("/cadastrar/disciplina")
    public String cadastroDisciplina(@Valid @ModelAttribute Disciplina disciplina, BindingResult result, RedirectAttributes redirectAttributes){
        
        if (result.hasErrors()) {
            return "admin/cadastrar-disciplina";
        }
        
        disciplina.setNome(disciplina.getNome().trim());
        disciplinaRepository.save(disciplina);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Disciplina salva com sucesso!");
        return "redirect:/cadastrar/disciplina";
    }
}
