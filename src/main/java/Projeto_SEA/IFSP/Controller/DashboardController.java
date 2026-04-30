package Projeto_SEA.IFSP.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import Projeto_SEA.IFSP.Repository.AlunoRepository;
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
    
    @GetMapping("/dashboard")
    public String painelControle(Model model) {

        long totalAlunos = alunoRepository.count();
        long totalProfessores = professorRepository.count();
        long totalSalas = salaRepository.count();

        model.addAttribute("totalAlunos", totalAlunos);
        model.addAttribute("totalProfessores", totalProfessores);
        model.addAttribute("totalSalas", totalSalas);
        
        return "admin/dashboard";
    }
}
