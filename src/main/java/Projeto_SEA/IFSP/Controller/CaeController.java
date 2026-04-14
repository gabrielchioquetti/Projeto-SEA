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

import Projeto_SEA.IFSP.Model.Cae;
import Projeto_SEA.IFSP.Repository.CaeRepository;
import Projeto_SEA.IFSP.Service.FileStorageService;
import jakarta.validation.Valid;

@Controller
public class CaeController {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CaeRepository caeRepository;

    @GetMapping("/cadastrar/cae")
    public String cadastrarCae(Cae cae){
        return "admin/cadastrar-cae";
    }

    @PostMapping("/cadastrar/cae")
    public String cadastroCae(@Valid @ModelAttribute Cae cae, BindingResult result, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes){
        
        if (result.hasErrors()) {
            return "admin/cadastrar-cae";
        }

        if (file == null || file.isEmpty()) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Imagem é obrigatória!");
            return "redirect:/cadastrar/cae";
        }

        if (!file.getContentType().startsWith("image/")) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Arquivo inválido! Apenas imagens são permitidas.");
            return "redirect:/cadastrar/cae";
        }

        try {
            String imagem = fileStorageService.store(file);
            cae.setImg_cae("/uploads/" + imagem);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Erro ao salvar imagem: " + e.getMessage());
            return "redirect:/cadastrar/cae";
        }
        
        cae.setSenha(passwordEncoder.encode(cae.getSenha()));
        caeRepository.save(cae);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "CAE salvo com sucesso!");
        return "redirect:/cadastrar/cae";
    }
}
