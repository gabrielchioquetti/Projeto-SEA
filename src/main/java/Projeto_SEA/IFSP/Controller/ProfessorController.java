package Projeto_SEA.IFSP.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import Projeto_SEA.IFSP.Model.Professor;
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

    @GetMapping("/cadastrar/professor")
    public String cadastrarProfessor(Professor professor){
        return "admin/cadastrar-professor";
    }

    @PostMapping("/cadastrar/professor")
    public String cadastroProfessor(@Valid @ModelAttribute Professor professor, BindingResult result, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes){

        if (result.hasErrors()) {
            return "admin/cadastrar-professor";
        }

        if (file == null || file.isEmpty()) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Imagem é obrigatória!");
            return "redirect:/cadastrar/professor";
        }

        if (!file.getContentType().startsWith("image/")) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Arquivo inválido! Apenas imagens são permitidas.");
            return "redirect:/cadastrar/professor";
        }

        try {
            String imagem = fileStorageService.store(file);
            professor.setImg_professor("/uploads/" + imagem);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Erro ao salvar imagem: " + e.getMessage());
            return "redirect:/cadastrar/professor";
        }

        professor.setSenha(passwordEncoder.encode(professor.getSenha()));
        professorRepository.save(professor);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Professor salvo com sucesso!");

        return "redirect:/cadastrar/professor";
    }
}
