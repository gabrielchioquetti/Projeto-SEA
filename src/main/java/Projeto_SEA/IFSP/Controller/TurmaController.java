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

import Projeto_SEA.IFSP.Enum.Periodo;
import Projeto_SEA.IFSP.Model.Sala;
import Projeto_SEA.IFSP.Model.Turma;
import Projeto_SEA.IFSP.Repository.DisciplinaRepository;
import Projeto_SEA.IFSP.Repository.SalaRepository;
import Projeto_SEA.IFSP.Repository.TurmaRepository;
import jakarta.validation.Valid;

@Controller
public class TurmaController {

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private SalaRepository salaRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    private void carregarDadosFormulario(Model model) {
        model.addAttribute("turma", new Turma());
        model.addAttribute("salas", salaRepository.findAll());
        model.addAttribute("disciplinas", disciplinaRepository.findAll());
        model.addAttribute("periodos", Periodo.values());
    }

    @GetMapping("/cadastrar/turma")
    public String cadastrarTurma(Model model) {
        model.addAttribute("turma", new Turma());
        model.addAttribute("salas", salaRepository.findAll());
        model.addAttribute("disciplinas", disciplinaRepository.findAll());
        model.addAttribute("periodos", Periodo.values());
        return "admin/cadastrar-turma";
    }

    @PostMapping("/cadastrar/turma")
    public String cadastroTurma(@Valid @ModelAttribute Turma turma, BindingResult result,
            RedirectAttributes redirectAttributes, Model model) {

        if (result.hasErrors()) {
            carregarDadosFormulario(model);
            return "admin/cadastrar-turma";
        }

        if (turma.getSala() == null || turma.getSala().getId_sala() == null) {
            result.rejectValue("sala", null, "Selecione uma sala");
            carregarDadosFormulario(model);
            return "admin/cadastrar-turma";
        }

        Sala sala = salaRepository.findById(turma.getSala().getId_sala())
                .orElse(null);

        if (sala == null) {
            result.rejectValue("sala", null, "Sala inválida");
            carregarDadosFormulario(model);
            return "admin/cadastrar-turma";
        }

        turma.setSala(sala);

        if (turma.getDisciplinas() == null || turma.getDisciplinas().isEmpty()) {
            result.rejectValue("disciplinas", null, "Selecione pelo menos uma disciplina");
            carregarDadosFormulario(model);
            return "admin/cadastrar-turma";
        }

        turma.setDisciplinas(
                disciplinaRepository
                        .findAllById(turma.getDisciplinas().stream().map(d -> d.getIdDisciplina()).toList()));

        if (turma.getPeriodo() == null) {
            result.rejectValue("periodo", null, "Selecione um período");
            carregarDadosFormulario(model);
            return "admin/cadastrar-turma";
        }

        turmaRepository.save(turma);

        redirectAttributes.addFlashAttribute("mensagemSucesso", "Turma salva com sucesso!");
        return "redirect:/dashboard";
    }

    @GetMapping("/listar/turmas")
    public String listarSalas(Model model) {

        model.addAttribute("turmas", turmaRepository.findAll());

        return "admin/listar-turmas";
    }

    @GetMapping("/deletar/turma/{id}")
    public String deletarTurma(@PathVariable Long id, RedirectAttributes redirectAttributes) {

        Turma turma = turmaRepository.findById(id).orElse(null);

        if (turma != null) {
            turmaRepository.delete(turma);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Turma deletada com sucesso!");
        }

        return "redirect:/listar/turmas";
    }

    @GetMapping("/editar/turma/{id}")
    public String editarTurma(@PathVariable Long id, Model model) {

        Turma turma = turmaRepository.findById(id).orElse(null);

        if (turma == null) {
            return "redirect:/listar/turmas";
        }

        model.addAttribute("turma", turma);
        model.addAttribute("periodos", Periodo.values());
        model.addAttribute("salas", salaRepository.findAll());
        model.addAttribute("disciplinas", disciplinaRepository.findAll());

        return "admin/editar-turma";
    }

    @PostMapping("/editar/turma/{id}")
    public String atualizarTurma(@PathVariable Long id, @Valid @ModelAttribute Turma turma, BindingResult result, Model model, RedirectAttributes redirectAttributes) {

        Turma turmaExistente = turmaRepository.findById(id).orElse(null);

        if (turmaExistente == null) {
            return "redirect:/listar/turmas";
        }

        if (result.hasErrors()) {

            model.addAttribute("periodos", Periodo.values());
            model.addAttribute("salas", salaRepository.findAll());
            model.addAttribute("disciplinas", disciplinaRepository.findAll());

            return "admin/editar-turma";
        }

        turmaExistente.setNome(turma.getNome());
        turmaExistente.setCurso(turma.getCurso());
        turmaExistente.setPeriodo(turma.getPeriodo());
        turmaExistente.setSala(turma.getSala());
        turmaExistente.setDisciplinas(disciplinaRepository.findAllById(turma.getDisciplinas() .stream() .map(d -> d.getIdDisciplina()) .toList()));

        turmaRepository.save(turmaExistente);
        redirectAttributes.addFlashAttribute( "mensagemSucesso", "Turma atualizada com sucesso!");

        return "redirect:/listar/turmas";
    }
}