package Projeto_SEA.IFSP.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import Projeto_SEA.IFSP.Model.Horario;
import Projeto_SEA.IFSP.Repository.HorarioRepository;
import jakarta.validation.Valid;

@Controller
public class HorarioController {

    @Autowired
    private HorarioRepository horarioRepository;
    
    @GetMapping("/cadastrar/horario")
    public String cadastrarHorarios(Horario horario){
        return "admin/cadastrar-horario";
    }

    @PostMapping("/cadastrar/horario")
    public String cadastroHorario(@Valid @ModelAttribute Horario horario, BindingResult result, RedirectAttributes redirectAttributes){

        if (result.hasErrors()) {
            return "admin/cadastrar-horario";
        }

        horarioRepository.save(horario);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Horario salvo com sucesso!");

        return "redirect:/dashboard";

    }
}
