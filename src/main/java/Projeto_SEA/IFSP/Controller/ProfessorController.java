package Projeto_SEA.IFSP.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import Projeto_SEA.IFSP.Enum.AreaAtuacao;
import Projeto_SEA.IFSP.Model.Disciplina;
import Projeto_SEA.IFSP.Model.Professor;
import Projeto_SEA.IFSP.Repository.DisciplinaRepository;
import Projeto_SEA.IFSP.Repository.HorarioRepository;
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

    @Autowired
    private HorarioRepository horarioRepository;

    @GetMapping("/cadastrar/professor")
    public String cadastrarProfessor(Model model) {
        model.addAttribute("professor", new Professor());
        model.addAttribute("areas", AreaAtuacao.values());
        model.addAttribute("disciplinas", disciplinaRepository.findAll());
        return "admin/cadastrar-professor";
    }

    @GetMapping("/disciplinas/area")
    @ResponseBody
    public List<Disciplina> buscarDisciplinasPorArea(@RequestParam AreaAtuacao area) {

        return disciplinaRepository.findByArea(area);
    }

    @PostMapping("/cadastrar/professor")
    public String cadastroProfessor(@Valid @ModelAttribute Professor professor, BindingResult result,
            @RequestParam("file") MultipartFile file, Model model, RedirectAttributes redirectAttributes) {

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
                                .toList()));

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
            professor.setImagem("/uploads/" + imagem);
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
    public String listarProfessores(Model model) {

        model.addAttribute("professores", professorRepository.findAll());

        return "admin/listar-professores";
    }

    @GetMapping("/detalhes/professor/{id}")
    public String detalhesProfessor(@PathVariable Long id, Model model) {

        Professor professor = professorRepository.findById(id).orElse(null);

        model.addAttribute("professor", professor);

        return "admin/detalhes-professor";
    }

    @GetMapping("/deletar/professor/{id}")
    public String deletarProfessor(@PathVariable Long id, RedirectAttributes redirectAttributes) {

        Professor professor = professorRepository.findById(id).orElse(null);

        if (professor != null) {
            horarioRepository.deleteByProfessor(professor);
            fileStorageService.delete(professor.getImagem());
            professorRepository.delete(professor);
        }

        redirectAttributes.addFlashAttribute("mensagemSucesso", "Professor deletado com sucesso!");

        return "redirect:/listar/professores";
    }

    @GetMapping("/editar/professor/{id}")
    public String editarProfessor(@PathVariable Long id, Model model) {

        Professor professor = professorRepository.findById(id).orElse(null);

        model.addAttribute("professor", professor);
        model.addAttribute("areas", AreaAtuacao.values());
        model.addAttribute("disciplinas", disciplinaRepository.findAll());

        return "admin/editar-professor";
    }

    @PostMapping("/editar/professor/{id}")
    public String atualizarProfessor(@PathVariable Long id, @Valid @ModelAttribute Professor professor, BindingResult result, @RequestParam("file") MultipartFile file, Model model, RedirectAttributes redirectAttributes) {

        Professor professorExistente = professorRepository.findById(id).orElse(null);

        if (professorExistente == null) {
            return "redirect:/listar/professores";
        }

        if (result.hasErrors()) {
            model.addAttribute("areas", AreaAtuacao.values());
            model.addAttribute("disciplinas", disciplinaRepository.findAll());
            return "admin/editar-professor";
        }

        if (professor.getDisciplinas() == null || professor.getDisciplinas().isEmpty()) {
            result.rejectValue("disciplinas", null, "Selecione pelo menos uma disciplina");
            model.addAttribute("areas", AreaAtuacao.values());
            model.addAttribute("disciplinas", disciplinaRepository.findAll());
            return "admin/editar-professor";
        }

        professorExistente.setNome(professor.getNome());
        professorExistente.setEmail(professor.getEmail());
        professorExistente.setProntuario(professor.getProntuario());
        professorExistente.setArea(professor.getArea());
        professorExistente.setDisciplinas(disciplinaRepository.findAllById(professor.getDisciplinas() .stream() .map(d -> d.getIdDisciplina()) .toList()));

        if (professor.getSenha() != null && !professor.getSenha().isBlank()) {
            professorExistente.setSenha(passwordEncoder.encode(professor.getSenha()));
        }

        if (file != null && !file.isEmpty()) {

            try {
                if (professorExistente.getImagem() != null) {
                    String imagemAntiga = professorExistente.getImagem() .replace("/uploads/", "");
                    fileStorageService.delete(imagemAntiga);
                }

                String imagem = fileStorageService.store(file);

                professorExistente.setImagem("/uploads/" + imagem);

            } catch (Exception e) {
                model.addAttribute("mensagemErro", "Erro ao salvar imagem");
                model.addAttribute("areas", AreaAtuacao.values());
                model.addAttribute("disciplinas", disciplinaRepository.findAll());
                return "admin/editar-professor";
            }
        }

        professorRepository.save(professorExistente);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Professor atualizado com sucesso!");

        return "redirect:/listar/professores";
    }
}
