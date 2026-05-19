package Projeto_SEA.IFSP.Controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import Projeto_SEA.IFSP.DTO.GradeHorarioDTO;
import Projeto_SEA.IFSP.Model.Aluno;
import Projeto_SEA.IFSP.Model.Horario;
import Projeto_SEA.IFSP.Model.Professor;
import Projeto_SEA.IFSP.Model.Usuario;
import Projeto_SEA.IFSP.Repository.AlunoRepository;
import Projeto_SEA.IFSP.Repository.HorarioRepository;
import Projeto_SEA.IFSP.Repository.ProfessorRepository;

@Controller
@RequestMapping("/perfil")
public class PerfilController {

    @Autowired
    private HorarioRepository horarioRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @GetMapping
    public String perfil(Model model) {

        // TESTE TEMPORÁRIO
        Usuario usuario = alunoRepository
                .findById(2L)
                .orElse(null);

        /*
        Usuario usuario = professorRepository
                .findById(1L)
                .orElse(null);
        */

        if (usuario == null) {

            return "redirect:/";
        }

        List<Horario> horarios =
                buscarHorarios(usuario);

        Map<String, GradeHorarioDTO> grade =
                montarGrade(horarios, usuario);

        model.addAttribute("usuario", usuario);

        model.addAttribute("grade", grade.values());

        return "perfil/perfil";
    }

    private List<Horario> buscarHorarios(
            Usuario usuario) {

        if (usuario instanceof Aluno aluno &&
                aluno.getTurma() != null) {

            return horarioRepository
                    .findByTurma(aluno.getTurma());
        }

        if (usuario instanceof Professor professor) {

            return horarioRepository
                    .findByProfessor(professor);
        }

        return List.of();
    }

    private Map<String, GradeHorarioDTO> montarGrade(
            List<Horario> horarios,
            Usuario usuario) {

        Map<String, GradeHorarioDTO> gradeMap =
                new LinkedHashMap<>();

        for (Horario horario : horarios) {

            String faixaHorario =
                    horario.getHoraInicio()
                    + " - "
                    + horario.getHoraFim();

            GradeHorarioDTO dto =
                    gradeMap.computeIfAbsent(
                            faixaHorario,
                            h -> {

                                GradeHorarioDTO novo =
                                        new GradeHorarioDTO();

                                novo.setHorario(
                                        faixaHorario
                                );

                                return novo;
                            }
                    );

            String conteudo =
                    montarConteudoHorario(
                            horario,
                            usuario
                    );

            String cor =
                    gerarCorDisciplina(
                            horario.getDisciplina()
                                   .getNome()
                    );

            preencherDiaSemana(
                    dto,
                    horario,
                    conteudo,
                    cor
            );
        }

        return gradeMap;
    }

    private String montarConteudoHorario(
            Horario horario,
            Usuario usuario) {

        StringBuilder texto =
                new StringBuilder();

        texto.append(
                horario.getDisciplina().getNome()
        );

        if (horario.getSala() != null) {

            texto.append("<br>Sala: ")
                 .append(
                         horario.getSala().getNome()
                 );
        }

        if (usuario instanceof Aluno &&
                horario.getProfessor() != null) {

            texto.append("<br>Prof. ")
                 .append(
                         horario.getProfessor().getNome()
                 );
        }

        return texto.toString();
    }

    private void preencherDiaSemana(
            GradeHorarioDTO dto,
            Horario horario,
            String conteudo,
            String cor) {

        switch (horario.getDiaSemana()) {

            case SEGUNDA -> {

                dto.setSeg(conteudo);

                dto.setCorSeg(cor);
            }

            case TERCA -> {

                dto.setTer(conteudo);

                dto.setCorTer(cor);
            }

            case QUARTA -> {

                dto.setQua(conteudo);

                dto.setCorQua(cor);
            }

            case QUINTA -> {

                dto.setQui(conteudo);

                dto.setCorQui(cor);
            }

            case SEXTA -> {

                dto.setSex(conteudo);

                dto.setCorSex(cor);
            }
        }
    }

    private String gerarCorDisciplina(
            String nome) {

        String[] cores = {

                "#2563eb",
                "#dc2626",
                "#16a34a",
                "#9333ea",
                "#ea580c",
                "#0891b2",
                "#ca8a04",
                "#be123c",
                "#0f766e",
                "#4338ca"
        };

        int index =
                Math.abs(nome.hashCode())
                % cores.length;

        return cores[index];
    }
}