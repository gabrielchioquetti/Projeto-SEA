package Projeto_SEA.IFSP.Controller;

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
import Projeto_SEA.IFSP.Repository.HorarioRepository;
import Projeto_SEA.IFSP.Repository.ProfessorRepository;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private HorarioRepository horarioRepository;

    @GetMapping("/")
    public String homePage() {
        return "home.html";
    }

    @GetMapping("/horarios")
    public String horarios() {
        return "horarios";
    }

    @GetMapping("/salas")
    public String salas() {
        return "salas";
    }

    @GetMapping("/turmas")
    public String turmas() {
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

    Professor professor =
            professorRepository
            .findById(1L)
            .orElse(null);

    List<Horario> horarios =
            horarioRepository
            .findByProfessor(professor);

    System.out.println(
            "TOTAL: " + horarios.size()
    );

    for (Horario h : horarios) {

        System.out.println(
                h.getDisciplina().getNome()
        );

        System.out.println(
                h.getTurma().getNome()
        );

        System.out.println(
                h.getSala().getNome()
        );
    }

    return "home";
}

}
