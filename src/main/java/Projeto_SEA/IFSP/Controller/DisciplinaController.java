package Projeto_SEA.IFSP.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import Projeto_SEA.IFSP.Enum.AreaAtuacao;
import Projeto_SEA.IFSP.Model.Disciplina;
import Projeto_SEA.IFSP.Repository.DisciplinaRepository;
import jakarta.validation.Valid;

@Controller
public class DisciplinaController {

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @GetMapping("/cadastrar/disciplina")
    public String cadastrarDisciplina(Model model) {
        model.addAttribute("disciplina", new Disciplina());
        model.addAttribute("areas", AreaAtuacao.values());
        return "admin/cadastrar-disciplina";
    }

    @PostMapping("/cadastrar/disciplina")
    public String cadastroDisciplina(@Valid @ModelAttribute Disciplina disciplina, BindingResult result,
            RedirectAttributes redirectAttributes, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("areas", AreaAtuacao.values());
            return "admin/cadastrar-disciplina";
        }

        disciplina.setNome(disciplina.getNome().trim());
        disciplinaRepository.save(disciplina);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Disciplina salva com sucesso!");
        return "redirect:/cadastrar/disciplina";
    }

    @GetMapping("/listar/disciplinas")
    public String listarDisciplinas(@RequestParam(defaultValue = "") String busca, @RequestParam(defaultValue = "0") int page, Model model) {

        Pageable pageable = PageRequest.of(page, 5);
        Page<Disciplina> disciplinas = disciplinaRepository.findByNomeContainingIgnoreCase(busca, pageable);

        model.addAttribute("disciplinas", disciplinas);
        model.addAttribute("busca", busca);

        return "admin/listar-disciplina";
    }

    @GetMapping("/deletar/disciplina/{id}")
    public String deletarDisciplina(@PathVariable Long id, RedirectAttributes redirectAttributes) {

        Disciplina disciplina = disciplinaRepository.findById(id).orElse(null);

        if (disciplina != null) {
            disciplina.getProfessores().clear();
            disciplina.getTurmas().clear();
            disciplinaRepository.save(disciplina);
            disciplinaRepository.delete(disciplina);
        }

        redirectAttributes.addFlashAttribute("mensagemSucesso", "Disciplina deletada com sucesso!");

        return "redirect:/listar/disciplinas";
    }

    @GetMapping("/editar/disciplina/{id}")
    public String editarDisciplina(@PathVariable Long id, Model model) {

        Disciplina disciplina = disciplinaRepository.findById(id).orElse(null);

        if (disciplina == null) {
            return "redirect:/listar/disciplinas";
        }

        model.addAttribute("disciplina", disciplina);
        model.addAttribute("areas", AreaAtuacao.values());

        return "admin/editar-disciplina";
    }

    @PostMapping("/editar/disciplina/{id}")
    public String atualizarDisciplina(@PathVariable Long id, @Valid @ModelAttribute Disciplina disciplina, BindingResult result, RedirectAttributes redirectAttributes, Model model) {

        Disciplina disciplinaExistente = disciplinaRepository.findById(id).orElse(null);

        if (disciplinaExistente == null) {
            return "redirect:/listar/disciplinas";
        }

        if (result.hasErrors()) {

            model.addAttribute("areas", AreaAtuacao.values());
            return "admin/editar-disciplina";
        }

        disciplinaExistente.setNome(disciplina.getNome().trim());
        disciplinaExistente.setCarga(disciplina.getCarga());
        disciplinaExistente.setArea(disciplina.getArea());
        disciplinaRepository.save(disciplinaExistente);

        redirectAttributes.addFlashAttribute("mensagemSucesso", "Disciplina atualizada com sucesso!");

        return "redirect:/listar/disciplinas";
    }
}
