package Projeto_SEA.IFSP.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import Projeto_SEA.IFSP.Model.Sala;
import Projeto_SEA.IFSP.Repository.SalaRepository;
import jakarta.validation.Valid;

@Controller
public class SalaController {

    @Autowired
    private SalaRepository salaRepository;
    
    @GetMapping("/cadastrar/sala")
    public String cadastrarSala (){
        return "admin/cadastrar-sala";
    }

    @PostMapping("/cadastrar/sala")
    public String cadastroSala(@Valid @ModelAttribute Sala sala, BindingResult result, RedirectAttributes redirectAttributes){

        if (result.hasErrors()) {
            return "admin/cadastrar-sala";
        }

        salaRepository.save(sala);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Sala salva com sucesso!");

        return "redirect:/dashboard";
    }

}
