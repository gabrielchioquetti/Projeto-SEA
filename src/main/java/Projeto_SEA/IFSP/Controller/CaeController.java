package Projeto_SEA.IFSP.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
            cae.setImagem("/uploads/" + imagem);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Erro ao salvar imagem: " + e.getMessage());
            return "redirect:/cadastrar/cae";
        }
        
        cae.setSenha(passwordEncoder.encode(cae.getSenha()));
        caeRepository.save(cae);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "CAE salvo com sucesso!");
        return "redirect:/cadastrar/cae";
    }

    @GetMapping("/listar/caes")
    public String listarCaes(@RequestParam(defaultValue = "") String busca, @RequestParam(defaultValue = "0") int page, Model model) {

        Pageable pageable = PageRequest.of(page, 5);
        Page<Cae> caes = caeRepository.findByNomeContainingIgnoreCase(busca, pageable);

        model.addAttribute("caes", caes);
        model.addAttribute("busca", busca);

        return "admin/listar-caes";
    }

    @GetMapping("/deletar/cae/{id}")
    public String deletarCae(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Cae cae = caeRepository.findById(id).orElse(null);

        if (cae != null) {
            if (cae.getImagem() != null) {

                String imagem = cae.getImagem().replace("/uploads/", "");
                fileStorageService.delete(imagem);
            }
            caeRepository.delete(cae);
        }
        redirectAttributes.addFlashAttribute("mensagemSucesso", "CAE deletado com sucesso!");

        return "redirect:/listar/caes";
    }

    @GetMapping("/editar/cae/{id}")
    public String editarCae(@PathVariable Long id, Model model) {

        Cae cae = caeRepository.findById(id).orElse(null);

        if (cae == null) {
            return "redirect:/listar/caes";
        }

        model.addAttribute("cae", cae);

        return "admin/editar-cae";
    }

    @PostMapping("/editar/cae/{id}")
    public String atualizarCae(@PathVariable Long id, @Valid @ModelAttribute Cae cae, BindingResult result, @RequestParam("file") MultipartFile file, Model model, RedirectAttributes redirectAttributes) {

        Cae caeExistente = caeRepository.findById(id).orElse(null);

        if (caeExistente == null) {
            return "redirect:/listar/caes";
        }

        if (result.hasErrors()) {
            return "admin/editar-cae";
        }

        caeExistente.setNome(cae.getNome());
        caeExistente.setEmail(cae.getEmail());
        caeExistente.setProntuario(cae.getProntuario());

        if (cae.getSenha() != null && !cae.getSenha().isBlank()) {
            caeExistente.setSenha(passwordEncoder.encode(cae.getSenha()));
        }

        if (file != null && !file.isEmpty()) {
            try {
                if (caeExistente.getImagem() != null) {
                    String imagemAntiga = caeExistente.getImagem().replace("/uploads/", "");
                    fileStorageService.delete(imagemAntiga);
                }

                String imagem = fileStorageService.store(file);
                caeExistente.setImagem("/uploads/" + imagem);

            } catch (Exception e) {
                model.addAttribute("mensagemErro", "Erro ao salvar imagem");
                return "admin/editar-cae";
            }
        }

        caeRepository.save(caeExistente);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "CAE atualizado com sucesso!");

        return "redirect:/listar/caes";
    }
}
