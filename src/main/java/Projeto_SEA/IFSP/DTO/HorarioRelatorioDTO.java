package Projeto_SEA.IFSP.DTO;

public record HorarioRelatorioDTO(
        String disciplina,
        String professor,
        String turma,
        String sala,
        String diaSemana,
        String horaInicio,
        String horaFim
) {}