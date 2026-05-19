package Projeto_SEA.IFSP.Controller;

import java.util.ArrayList;
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

        /*
         * TESTE TEMPORÁRIO SEM AUTENTICAÇÃO
         *
         * Troque entre aluno/professor
         * alterando o repository abaixo.
         */

         Usuario usuario = alunoRepository
                .findById(2L)
                .orElse(null);

        // Usuario usuario = professorRepository
        //         .findById(1L)
        //         .orElse(null);

        if (usuario == null) {

            return "redirect:/";
        }

        List<Horario> horarios = buscarHorarios(usuario);

        Map<String, GradeHorarioDTO> grade =
                montarGrade(horarios);

        model.addAttribute("usuario", usuario);

        model.addAttribute("grade", grade.values());

        return "perfil/perfil";
    }

    private List<Horario> buscarHorarios(Usuario usuario) {

        List<Horario> horarios = new ArrayList<>();

        if (usuario instanceof Aluno aluno) {

            if (aluno.getTurma() != null) {

                horarios = horarioRepository
                        .findByTurma(aluno.getTurma());
            }

        } else if (usuario instanceof Professor professor) {

            horarios = horarioRepository
                    .findByProfessor(professor);
        }

        return horarios;
    }

    private Map<String, GradeHorarioDTO> montarGrade(
            List<Horario> horarios) {

        Map<String, GradeHorarioDTO> gradeMap =
                new LinkedHashMap<>();

        for (Horario horario : horarios) {

            String faixaHorario =
                    horario.getHoraInicio()
                    + " - "
                    + horario.getHoraFim();

            GradeHorarioDTO dto =
                    gradeMap.getOrDefault(
                            faixaHorario,
                            new GradeHorarioDTO()
                    );

            dto.setHorario(faixaHorario);

            String disciplina =
                    horario.getDisciplina().getNome();

            switch (horario.getDiaSemana()) {

                case SEGUNDA -> dto.setSeg(disciplina);

                case TERCA -> dto.setTer(disciplina);

                case QUARTA -> dto.setQua(disciplina);

                case QUINTA -> dto.setQui(disciplina);

                case SEXTA -> dto.setSex(disciplina);
            }

            gradeMap.put(faixaHorario, dto);
        }

        return gradeMap;
    }
}