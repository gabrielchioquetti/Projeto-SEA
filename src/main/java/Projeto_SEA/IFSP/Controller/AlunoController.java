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

import Projeto_SEA.IFSP.Model.Aluno;
import Projeto_SEA.IFSP.Repository.AlunoRepository;
import Projeto_SEA.IFSP.Service.FileStorageService;
import jakarta.validation.Valid;

@Controller
public class AlunoController {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    
    @GetMapping("/cadastrar/aluno")
    public String cadastrarAluno(Aluno aluno){
        return "admin/cadastrar-aluno";
    }

    @PostMapping("/cadastrar/aluno")
    public String cadastroAluno(@Valid @ModelAttribute Aluno aluno, BindingResult result, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes){

        if (result.hasErrors()) {
            return "admin/cadastrar-aluno";
        }

        if (file == null || file.isEmpty()) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Imagem é obrigatória!");
            return "redirect:/cadastrar/aluno";
        }

        if (!file.getContentType().startsWith("image/")) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Arquivo inválido! Apenas imagens são permitidas.");
            return "redirect:/cadastrar/aluno";
        }

        try {
            String imagem = fileStorageService.store(file);
            aluno.setImg_aluno("/uploads/" + imagem);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Erro ao salvar imagem: " + e.getMessage());
            return "redirect:/cadastrar/aluno";
        }
        
        aluno.setSenha(passwordEncoder.encode(aluno.getSenha()));
        alunoRepository.save(aluno);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Aluno salvo com sucesso!");
        return "redirect:/cadastrar/aluno";
    }
}
