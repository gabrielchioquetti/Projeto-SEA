package Projeto_SEA.IFSP.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import Projeto_SEA.IFSP.Enum.TipoSala;
import Projeto_SEA.IFSP.Model.Sala;
import Projeto_SEA.IFSP.Repository.HorarioRepository;
import Projeto_SEA.IFSP.Repository.SalaRepository;
import jakarta.validation.Valid;

@Controller
public class SalaController {

    @Autowired
    private SalaRepository salaRepository;

    @Autowired
    private HorarioRepository horarioRepository;

    @GetMapping("/cadastrar/sala")
    public String cadastrarSala(Sala sala, Model model) {
        model.addAttribute("tipos", TipoSala.values());
        return "admin/cadastrar-sala";
    }

    @PostMapping("/cadastrar/sala")
    public String cadastroSala(@Valid @ModelAttribute Sala sala, BindingResult result, RedirectAttributes redirectAttributes, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("tipos", TipoSala.values());
            return "admin/cadastrar-sala";
        }

        salaRepository.save(sala);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Sala salva com sucesso!");

        return "redirect:/dashboard";
    }

    @GetMapping("/listar/salas")
    public String listarSalas(Model model) {

        model.addAttribute("salas", salaRepository.findAll());

        return "admin/listar-salas";
    }

    @GetMapping("/deletar/sala/{id}")
    public String deletarSala(@PathVariable Long id, RedirectAttributes redirectAttributes) {

        Sala sala = salaRepository.findById(id).orElse(null);

        if (sala != null) {
            horarioRepository.deleteBySala(sala);
            salaRepository.delete(sala);
        }

        redirectAttributes.addFlashAttribute("mensagemSucesso", "Sala deletada com sucesso!");

        return "redirect:/listar/salas";
    }

    @GetMapping("/editar/sala/{id}")
    public String editarSala(@PathVariable Long id, Model model) {

        Sala sala = salaRepository.findById(id).orElse(null);

        model.addAttribute("sala", sala);
        model.addAttribute("tipos", TipoSala.values());

        return "admin/editar-sala";
    }

    @PostMapping("/editar/sala/{id}")
    public String atualizarSala(@PathVariable Long id, @Valid @ModelAttribute Sala sala, BindingResult result, RedirectAttributes redirectAttributes, Model model) {

        Sala salaExistente = salaRepository.findById(id).orElse(null);

        if (salaExistente == null) {
            return "redirect:/listar/salas";
        }

        if (result.hasErrors()) {
            model.addAttribute("tipos", TipoSala.values());
            return "admin/editar-sala";
        }

        salaExistente.setNome(sala.getNome());
        salaExistente.setCapacidade(sala.getCapacidade());
        salaExistente.setTipo(sala.getTipo());

        salaRepository.save(salaExistente);

        redirectAttributes.addFlashAttribute("mensagemSucesso", "Sala atualizada com sucesso!");

        return "redirect:/listar/salas";
    }
}
