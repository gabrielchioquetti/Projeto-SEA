package Projeto_SEA.IFSP.Controller;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Projeto_SEA.IFSP.Model.Horario;
import Projeto_SEA.IFSP.Model.Professor;
import Projeto_SEA.IFSP.Model.Sala;
import Projeto_SEA.IFSP.Model.Turma;
import Projeto_SEA.IFSP.Repository.HorarioRepository;
import Projeto_SEA.IFSP.Repository.ProfessorRepository;
import Projeto_SEA.IFSP.Repository.SalaRepository;

@Controller
@RequestMapping("/home")
public class HomeController {

        @Autowired
        private ProfessorRepository professorRepository;

        @Autowired
        private HorarioRepository horarioRepository;

        @Autowired
        private SalaRepository salaRepository;

        @GetMapping("/")
        public String home(Model model) {

        Professor professor = professorRepository.findById(1L).orElse(null);

        List<Horario> horarios = horarioRepository.findByProfessor(professor);

        // ordena por hora
        horarios.sort(Comparator.comparing(Horario::getHoraInicio));

        Horario proxima = horarios.isEmpty() ? null : horarios.get(0);

        model.addAttribute("horarios", horarios);
        model.addAttribute("proxima", proxima);
        model.addAttribute("totalAulas", horarios.size());

        return "home";
        }

        @GetMapping("/horarios")
        public String horarios(Model model) {

        Professor professor = professorRepository.findById(1L).orElse(null);

        List<Horario> horarios = horarioRepository.findByProfessor(professor);

        model.addAttribute("horarios", horarios);

        return "horarios";
        }

        @GetMapping("/salas")
        public String salas(Model model) {

        List<Sala> salas = salaRepository.findAll();

        model.addAttribute("salas", salas);

        return "salas";
        }

        @GetMapping("/turmas")
        public String turmas(Model model) {

                Professor professor = professorRepository
                                .findById(1L)
                                .orElse(null);

                if (professor == null) {
                        return "redirect:/";
                }

                List<Horario> horarios = horarioRepository.findByProfessor(professor);

                List<Turma> turmas = horarios.stream()
                                .map(Horario::getTurma)
                                .distinct()
                                .toList();

                model.addAttribute("turmas", turmas);

                return "turmas";
        }

        @GetMapping("/busca")
        public String buscar(
                        @RequestParam("termo") String termo,
                        Model model) {

                Professor professor = professorRepository
                                .findById(1L)
                                .orElse(null);

                if (professor == null) {

                        return "redirect:/";
                }

                List<Horario> horarios = horarioRepository
                                .findByProfessor(professor);

                System.out.println(
                                "Horários encontrados: "
                                                + horarios.size());

                final String termoBusca = termo.toLowerCase();

                List<Horario> resultados = horarios.stream()
                                .filter(h ->

                                h.getDisciplina()
                                                .getNome()
                                                .toLowerCase()
                                                .contains(termoBusca)

                                                ||

                                                h.getTurma()
                                                                .getNome()
                                                                .toLowerCase()
                                                                .contains(termoBusca)

                                                ||

                                                h.getSala()
                                                                .getNome()
                                                                .toLowerCase()
                                                                .contains(termoBusca))
                                .toList();

                System.out.println(
                                "Resultados: "
                                                + resultados.size());

                model.addAttribute(
                                "resultados",
                                resultados);

                model.addAttribute(
                                "termo",
                                termo);

                return "busca";
        }

        @GetMapping("/teste")
        public String teste() {

                Professor professor = professorRepository
                                .findById(1L)
                                .orElse(null);

                List<Horario> horarios = horarioRepository
                                .findByProfessor(professor);

                System.out.println(
                                "TOTAL: " + horarios.size());

                for (Horario h : horarios) {

                        System.out.println(
                                        h.getDisciplina().getNome());

                        System.out.println(
                                        h.getTurma().getNome());

                        System.out.println(
                                        h.getSala().getNome());
                }

                return "home";
        }

}
