package Projeto_SEA.IFSP.Controller;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import Projeto_SEA.IFSP.Enum.DiaSemana;
import Projeto_SEA.IFSP.Model.Horario;
import Projeto_SEA.IFSP.Repository.AlunoRepository;
import Projeto_SEA.IFSP.Repository.HorarioRepository;
import Projeto_SEA.IFSP.Repository.ProfessorRepository;
import Projeto_SEA.IFSP.Repository.SalaRepository;

@Controller
public class DashboardController {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private SalaRepository salaRepository;

    @Autowired
    private HorarioRepository horarioRepository;

    @GetMapping("/dashboard")
    public String painelControle(Model model) {

        long totalAlunos = alunoRepository.count();
        long totalProfessores = professorRepository.count();
        long totalSalas = salaRepository.count();

        model.addAttribute("totalAlunos", totalAlunos);
        model.addAttribute("totalProfessores", totalProfessores);
        model.addAttribute("totalSalas", totalSalas);

        DiaSemana hoje = converterDia(LocalDate.now().getDayOfWeek().name());

        List<Horario> aulasHoje =
                horarioRepository.findByDiaSemanaOrderByHoraInicio(hoje);

        model.addAttribute("aulasHoje", aulasHoje);
        model.addAttribute("totalAulasHoje", aulasHoje.size());

        List<Horario> proximasAulas =
                horarioRepository.findAll()
                        .stream()
                        .sorted(Comparator
                                .comparing(Horario::getDiaSemana)
                                .thenComparing(Horario::getHoraInicio))
                        .limit(5)
                        .toList();

        model.addAttribute("proximasAulas", proximasAulas);

        return "admin/dashboard";
    }

    private DiaSemana converterDia(String dia) {
        return switch (dia) {
            case "MONDAY" -> DiaSemana.SEGUNDA;
            case "TUESDAY" -> DiaSemana.TERCA;
            case "WEDNESDAY" -> DiaSemana.QUARTA;
            case "THURSDAY" -> DiaSemana.QUINTA;
            case "FRIDAY" -> DiaSemana.SEXTA;
            default -> DiaSemana.SEGUNDA;
        };
    }
}