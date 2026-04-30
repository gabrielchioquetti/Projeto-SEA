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
import Projeto_SEA.IFSP.Model.Horario;
import Projeto_SEA.IFSP.Model.Professor;
import Projeto_SEA.IFSP.Model.Sala;
import Projeto_SEA.IFSP.Model.Turma;
import Projeto_SEA.IFSP.Repository.DisciplinaRepository;
import Projeto_SEA.IFSP.Repository.HorarioRepository;
import Projeto_SEA.IFSP.Repository.ProfessorRepository;
import Projeto_SEA.IFSP.Repository.SalaRepository;
import Projeto_SEA.IFSP.Repository.TurmaRepository;
import jakarta.validation.Valid;

@Controller
public class HorarioController {

    @Autowired
    private HorarioRepository horarioRepository;

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private SalaRepository salaRepository;

    @GetMapping("/cadastrar/horario")
    public String cadastrarHorarios(Model model){

        Horario horario = new Horario();
        horario.setTurma(new Turma());
        horario.setDisciplina(new Disciplina());
        horario.setProfessor(new Professor());
        horario.setSala(new Sala());

        model.addAttribute("horario", horario);
        model.addAttribute("turmas", turmaRepository.findAll());
        model.addAttribute("disciplinas", disciplinaRepository.findAll());
        model.addAttribute("professores", professorRepository.findAll());
        model.addAttribute("salas", salaRepository.findAll());

        return "admin/cadastrar-horario";
    }

    @PostMapping("/cadastrar/horario")
    public String cadastroHorario(@Valid @ModelAttribute Horario horario, BindingResult result, RedirectAttributes redirectAttributes, Model model){

        if (result.hasErrors()) {
            model.addAttribute("turmas", turmaRepository.findAll());
            model.addAttribute("disciplinas", disciplinaRepository.findAll());
            model.addAttribute("professores", professorRepository.findAll());
            model.addAttribute("salas", salaRepository.findAll());

            return "admin/cadastrar-horario";
        }

        if (horario.getHoraInicio().isAfter(horario.getHoraFim())) {
            result.rejectValue("horaInicio", "erro", "Hora início deve ser antes do fim");

            model.addAttribute("turmas", turmaRepository.findAll());
            model.addAttribute("disciplinas", disciplinaRepository.findAll());
            model.addAttribute("professores", professorRepository.findAll());
            model.addAttribute("salas", salaRepository.findAll());

            return "admin/cadastrar-horario";
        }

        boolean conflito = horarioRepository.existeConflito(
            horario.getDiaSemana(),
            horario.getHoraInicio(),
            horario.getHoraFim(),
            horario.getSala().getId_sala(),
            horario.getProfessor().getId_professor(),
            horario.getTurma().getId()
        );

        if (conflito) {
            result.reject("erro", "Já existe conflito de horário!");

            model.addAttribute("turmas", turmaRepository.findAll());
            model.addAttribute("disciplinas", disciplinaRepository.findAll());
            model.addAttribute("professores", professorRepository.findAll());
            model.addAttribute("salas", salaRepository.findAll());

            return "admin/cadastrar-horario";
        }

        horarioRepository.save(horario);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Horário salvo com sucesso!");

        return "redirect:/dashboard";
    }
}
