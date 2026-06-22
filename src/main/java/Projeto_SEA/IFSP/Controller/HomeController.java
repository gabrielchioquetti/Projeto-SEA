package Projeto_SEA.IFSP.Controller;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Projeto_SEA.IFSP.Enum.DiaSemana;
import Projeto_SEA.IFSP.Model.Aluno;
import Projeto_SEA.IFSP.Model.Horario;
import Projeto_SEA.IFSP.Model.Professor;
import Projeto_SEA.IFSP.Model.Sala;
import Projeto_SEA.IFSP.Model.Turma;
import Projeto_SEA.IFSP.Model.Usuario;
import Projeto_SEA.IFSP.Repository.AlunoRepository;
import Projeto_SEA.IFSP.Repository.CaeRepository;
import Projeto_SEA.IFSP.Repository.HorarioRepository;
import Projeto_SEA.IFSP.Repository.ProfessorRepository;
import Projeto_SEA.IFSP.Repository.SalaRepository;

@Controller
@RequestMapping("/home")
public class HomeController {

        @Autowired
        private AlunoRepository alunoRepository;

        @Autowired
        private CaeRepository caeRepository;

        @Autowired
        private ProfessorRepository professorRepository;

        @Autowired
        private HorarioRepository horarioRepository;

        @Autowired
        private SalaRepository salaRepository;

        private Usuario obterUsuarioLogado(
                        Authentication auth) {

                String prontuario = auth.getName();

                Usuario usuario = professorRepository
                                .findByProntuario(prontuario)
                                .orElse(null);

                if (usuario == null) {
                        usuario = alunoRepository
                                        .findByProntuario(prontuario)
                                        .orElse(null);
                }

                if (usuario == null) {
                        usuario = caeRepository
                                        .findByProntuario(prontuario)
                                        .orElse(null);
                }

                return usuario;
        }

        private DiaSemana obterDiaAtual() {

                return switch (java.time.LocalDate.now().getDayOfWeek()) {

                        case MONDAY -> DiaSemana.SEGUNDA;
                        case TUESDAY -> DiaSemana.TERCA;
                        case WEDNESDAY -> DiaSemana.QUARTA;
                        case THURSDAY -> DiaSemana.QUINTA;
                        case FRIDAY -> DiaSemana.SEXTA;

                        default -> null;
                };
        }

        @GetMapping("/")
        public String home(
                        Authentication auth,
                        Model model) {

                Usuario usuario = obterUsuarioLogado(auth);

                if (usuario == null) {
                        return "redirect:/login";
                }

                DiaSemana hoje = obterDiaAtual();

                List<Horario> horariosHoje;

                if (usuario instanceof Professor professor) {

                        horariosHoje = horarioRepository
                                        .findByProfessorAndDiaSemana(
                                                        professor,
                                                        hoje);

                } else if (usuario instanceof Aluno aluno) {

                        if (aluno.getTurma() == null) {

                                horariosHoje = new ArrayList<>();

                        } else {

                                horariosHoje = new ArrayList<>(
                                                horarioRepository
                                                                .findByTurma(aluno.getTurma())
                                                                .stream()
                                                                .filter(h -> h.getDiaSemana()
                                                                                .equals(hoje))
                                                                .toList());
                        }

                } else {

                        return "redirect:/dashboard";
                }

                horariosHoje.sort(
                                Comparator.comparing(
                                                Horario::getHoraInicio));

                Horario proxima = horariosHoje.stream()
                                .filter(h -> h.getHoraInicio()
                                                .isAfter(
                                                                LocalTime.now()))
                                .findFirst()
                                .orElse(null);

                model.addAttribute(
                                "usuario",
                                usuario);

                model.addAttribute(
                                "horarios",
                                horariosHoje);

                model.addAttribute(
                                "proxima",
                                proxima);

                model.addAttribute(
                                "totalAulas",
                                horariosHoje.size());

                return "home";
        }

        @GetMapping("/horarios")
        public String horarios(
                        Authentication auth,
                        Model model) {

                Usuario usuario = obterUsuarioLogado(auth);

                List<Horario> horarios;

                if (usuario instanceof Professor professor) {

                        horarios = horarioRepository
                                        .findByProfessor(
                                                        professor);

                } else if (usuario instanceof Aluno aluno
                                && aluno.getTurma() != null) {

                        horarios = horarioRepository
                                        .findByTurma(
                                                        aluno.getTurma());

                } else {

                        horarios = List.of();
                }

                model.addAttribute(
                                "horarios",
                                horarios);

                return "horarios";
        }

        @GetMapping("/salas")
        public String salas(Model model) {

                List<Sala> salas = salaRepository.findAll();

                model.addAttribute("salas", salas);

                return "salas";
        }

        @GetMapping("/turmas")
        public String turmas(
                        Authentication auth,
                        Model model) {

                Usuario usuario = obterUsuarioLogado(auth);

                if (!(usuario instanceof Professor professor)) {

                        return "redirect:/home/";
                }

                List<Horario> horarios = horarioRepository
                                .findByProfessor(professor);

                List<Turma> turmas = horarios.stream()
                                .map(Horario::getTurma)
                                .distinct()
                                .toList();

                model.addAttribute(
                                "turmas",
                                turmas);

                return "turmas";
        }

        @GetMapping("/busca")
        public String buscar(
                        @RequestParam("termo") String termo,
                        Authentication auth,
                        Model model) {

                Usuario usuario = obterUsuarioLogado(auth);

                if (!(usuario instanceof Professor professor)) {

                        return "redirect:/perfil";
                }

                List<Horario> horarios = horarioRepository
                                .findByProfessor(professor);

                String termoBusca = termo.toLowerCase();

                List<Horario> resultados = horarios.stream()
                                .filter(h ->

                                h.getDisciplina()
                                                .getNome()
                                                .toLowerCase()
                                                .contains(
                                                                termoBusca)

                                                ||

                                                h.getTurma()
                                                                .getNome()
                                                                .toLowerCase()
                                                                .contains(
                                                                                termoBusca)

                                                ||

                                                h.getSala()
                                                                .getNome()
                                                                .toLowerCase()
                                                                .contains(
                                                                                termoBusca))
                                .toList();

                model.addAttribute(
                                "resultados",
                                resultados);

                model.addAttribute(
                                "termo",
                                termo);

                return "busca";
        }

}
