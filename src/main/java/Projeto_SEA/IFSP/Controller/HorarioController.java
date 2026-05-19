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

    private void carregarListas(Model model) {

    model.addAttribute(
            "turmas",
            turmaRepository.findAll());

    model.addAttribute(
            "disciplinas",
            disciplinaRepository.findAll());

    model.addAttribute(
            "professores",
            professorRepository.findAll());

    model.addAttribute(
            "salas",
            salaRepository.findAll());
}

    @GetMapping("/cadastrar/horario")
    public String cadastrarHorarios(Model model) {

        Horario horario = new Horario();

        horario.setTurma(new Turma());

        horario.setDisciplina(new Disciplina());

        horario.setProfessor(new Professor());

        horario.setSala(new Sala());

        model.addAttribute("horario", horario);

        carregarListas(model);

        return "admin/cadastrar-horario";
    }

    @PostMapping("/cadastrar/horario")
    public String cadastroHorario(

            @Valid @ModelAttribute Horario horario,

            BindingResult result,

            RedirectAttributes redirectAttributes,

            Model model) {

        if (result.hasErrors()) {

            carregarListas(model);

            return "admin/cadastrar-horario";
        }

        // Busca entidades reais
        Turma turma = turmaRepository
                .findById(horario.getTurma().getId())
                .orElse(null);

        Disciplina disciplina = disciplinaRepository
                .findById(horario.getDisciplina().getIdDisciplina())
                .orElse(null);

        Professor professor = professorRepository
                .findById(horario.getProfessor().getId())
                .orElse(null);

        Sala sala = salaRepository
                .findById(horario.getSala().getId_sala())
                .orElse(null);

        // Validação
        if (turma == null ||
            disciplina == null ||
            professor == null ||
            sala == null) {

            result.reject(
                    "erro",
                    "Dados inválidos"
            );

            carregarListas(model);

            return "admin/cadastrar-horario";
        }

        // Injeta entidades reais
        horario.setTurma(turma);

        horario.setDisciplina(disciplina);

        horario.setProfessor(professor);

        horario.setSala(sala);

        // Validação horário
        if (horario.getHoraInicio()
                .isAfter(horario.getHoraFim())) {

            result.rejectValue(
                    "horaInicio",
                    "erro",
                    "Hora início deve ser antes da hora fim"
            );

            carregarListas(model);

            return "admin/cadastrar-horario";
        }

        // Conflito
        boolean conflito = horarioRepository.existeConflito(

                horario.getDiaSemana(),

                horario.getHoraInicio(),

                horario.getHoraFim(),

                sala.getId_sala(),

                professor.getId(),

                turma.getId()
        );

        if (conflito) {

            result.reject(
                    "erro",
                    "Já existe conflito de horário!"
            );

            carregarListas(model);

            return "admin/cadastrar-horario";
        }

        horarioRepository.save(horario);

        redirectAttributes.addFlashAttribute(
                "mensagemSucesso",
                "Horário cadastrado com sucesso!"
        );

        return "redirect:/dashboard";
    }
}
