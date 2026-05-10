package Projeto_SEA.IFSP.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import Projeto_SEA.IFSP.Enum.AreaAtuacao;
import Projeto_SEA.IFSP.Model.Disciplina;
import Projeto_SEA.IFSP.Model.Professor;
import Projeto_SEA.IFSP.Repository.DisciplinaRepository;
import Projeto_SEA.IFSP.Repository.ProfessorRepository;
import Projeto_SEA.IFSP.Service.FileStorageService;
import jakarta.validation.Valid;

@Controller
public class ProfessorController {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @GetMapping("/cadastrar/professor")
    public String cadastrarProfessor(Model model){
        model.addAttribute("professor", new Professor());
        model.addAttribute("areas", AreaAtuacao.values());
        model.addAttribute("disciplinas", disciplinaRepository.findAll());
        return "admin/cadastrar-professor";
    }

    @GetMapping("/disciplinas/area")
        @ResponseBody
        public List<Disciplina> buscarDisciplinasPorArea(@RequestParam AreaAtuacao area){

            return disciplinaRepository.findByArea(area);
        }

    @PostMapping("/cadastrar/professor")
    public String cadastroProfessor(@Valid @ModelAttribute Professor professor, BindingResult result, @RequestParam("file") MultipartFile file, Model model, RedirectAttributes redirectAttributes){

        if (result.hasErrors()) {
            model.addAttribute("disciplinas", disciplinaRepository.findAll());
            model.addAttribute("areas", AreaAtuacao.values());
            return "admin/cadastrar-professor";
        }

        if (professor.getDisciplinas() == null || professor.getDisciplinas().isEmpty()) {
            result.rejectValue("disciplinas", null, "Selecione pelo menos uma disciplina");
            model.addAttribute("disciplinas", disciplinaRepository.findAll());
            return "admin/cadastrar-professor";
        }
        
        professor.setDisciplinas(
            disciplinaRepository.findAllById(
                professor.getDisciplinas()
                        .stream()
                        .map(d -> d.getIdDisciplina())
                        .toList()
            )
        );

        if (file == null || file.isEmpty()) {
            model.addAttribute("mensagemErro", "Imagem é obrigatória!");
            model.addAttribute("disciplinas", disciplinaRepository.findAll());
            return "admin/cadastrar-professor";
        }

        if (file.getContentType() == null || !file.getContentType().startsWith("image/")) {
            model.addAttribute("mensagemErro", "Apenas imagens são permitidas (jpg, png, etc)");
            model.addAttribute("disciplinas", disciplinaRepository.findAll());
            return "admin/cadastrar-professor";
        }

        try {
            String imagem = fileStorageService.store(file);
            professor.setImg_professor("/uploads/" + imagem);
        } catch (Exception e) {
            model.addAttribute("mensagemErro", "Erro ao salvar imagem");
            model.addAttribute("disciplinas", disciplinaRepository.findAll());
            return "admin/cadastrar-professor";
        }  

        if (professor.getSenha() != null && !professor.getSenha().isBlank()) {
            professor.setSenha(passwordEncoder.encode(professor.getSenha()));
        }

        professorRepository.save(professor);

        redirectAttributes.addFlashAttribute("mensagemSucesso", "Professor salvo com sucesso!");
        return "redirect:/cadastrar/professor";
    }

    @GetMapping("/listar/professores")
    public String listarProfessores(Model model){

        model.addAttribute("professores", professorRepository.findAll());
        
        return "admin/listar-professores";
    }

    @GetMapping("/detalhes/professor/{id}")
    public String detalhesProfessor(@PathVariable Long id, Model model){

        Professor professor = professorRepository.findById(id).orElse(null);

        model.addAttribute("professor", professor);
        
        return "admin/detalhes-professor";
    }
}
