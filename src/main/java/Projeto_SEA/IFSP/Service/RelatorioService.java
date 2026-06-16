package Projeto_SEA.IFSP.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import Projeto_SEA.IFSP.Enum.DiaSemana;
import Projeto_SEA.IFSP.Model.Horario;
import Projeto_SEA.IFSP.Model.Professor;
import Projeto_SEA.IFSP.Model.Turma;
import Projeto_SEA.IFSP.Repository.HorarioRepository;

@Service
public class RelatorioService {

    @Autowired
    private HorarioRepository horarioRepository;

    private static final DateTimeFormatter HORARIO_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private static final DateTimeFormatter DATA_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private String formatarHorario(Horario horario) {

        return horario.getHoraInicio().format(HORARIO_FORMATTER)
                + " - "
                + horario.getHoraFim().format(HORARIO_FORMATTER);
    }

    private int ordemDia(DiaSemana dia) {

        return switch (dia) {

            case SEGUNDA -> 1;
            case TERCA -> 2;
            case QUARTA -> 3;
            case QUINTA -> 4;
            case SEXTA -> 5;
            case SABADO -> 6;
        };
    }

    private void ordenarHorarios(List<Horario> horarios) {

        horarios.sort(
                Comparator
                        .comparing(
                                (Horario h) -> ordemDia(h.getDiaSemana()))
                        .thenComparing(
                                Horario::getHoraInicio));
    }

    private void adicionarCabecalho(
            Document document,
            String tituloRelatorio) {

        try {

            PdfPTable cabecalho = new PdfPTable(4);

            cabecalho.setWidthPercentage(100);
            cabecalho.setWidths(new float[] { 1f, 1f, 4f, 1.5f });

            // LOGO

            InputStream logoIfspStream = getClass().getResourceAsStream(
                    "/static/Imagens/LogoIFSP.jpg");

            PdfPCell celulaLogoIFSP;

            if (logoIfspStream != null) {

                Image logoIFSP = Image.getInstance(
                        logoIfspStream.readAllBytes());

                logoIFSP.scaleToFit(60, 60);

                celulaLogoIFSP = new PdfPCell(logoIFSP);

            } else {

                celulaLogoIFSP = new PdfPCell(
                        new Phrase("IFSP"));
            }

            celulaLogoIFSP.setBorder(
                    Rectangle.NO_BORDER);

            celulaLogoIFSP.setHorizontalAlignment(
                    Element.ALIGN_CENTER);

            InputStream logoSeaStream = getClass().getResourceAsStream(
                    "/static/Imagens/logo.png");

            PdfPCell celulaLogoSEA;

            if (logoSeaStream != null) {

                Image logoSEA = Image.getInstance(
                        logoSeaStream.readAllBytes());

                logoSEA.scaleToFit(90, 90);

                celulaLogoSEA = new PdfPCell(logoSEA);

            } else {

                celulaLogoSEA = new PdfPCell(
                        new Phrase("SEA"));
            }

            celulaLogoSEA.setBorder(
                    Rectangle.NO_BORDER);

            celulaLogoSEA.setHorizontalAlignment(
                    Element.ALIGN_CENTER);

            // TÍTULO

            Font fonteTitulo = FontFactory.getFont(
                    FontFactory.HELVETICA_BOLD,
                    14);

            Font fonteTexto = FontFactory.getFont(
                    FontFactory.HELVETICA,
                    11);

            Paragraph titulo = new Paragraph();

            titulo.add(new Phrase(
                    "INSTITUTO FEDERAL DE SÃO PAULO\n",
                    fonteTitulo));

            titulo.add(new Phrase(
                    "Sistema de Ensalamento Acadêmico (SEA)\n",
                    fonteTexto));

            titulo.add(new Phrase(
                    tituloRelatorio,
                    fonteTitulo));

            PdfPCell celulaTitulo = new PdfPCell(titulo);

            celulaTitulo.setBorder(Rectangle.NO_BORDER);
            celulaTitulo.setHorizontalAlignment(
                    Element.ALIGN_CENTER);

            // DATA

            String dataGeracao = LocalDateTime.now()
                    .format(DATA_FORMATTER);

            PdfPCell celulaData = new PdfPCell(
                    new Phrase(
                            "Emitido em:\n"
                                    + dataGeracao));

            celulaData.setBorder(Rectangle.NO_BORDER);
            celulaData.setHorizontalAlignment(
                    Element.ALIGN_RIGHT);

            cabecalho.addCell(celulaLogoIFSP);

            cabecalho.addCell(celulaLogoSEA);

            cabecalho.addCell(celulaTitulo);

            cabecalho.addCell(celulaData);

            document.add(cabecalho);

            PdfPTable linha = new PdfPTable(1);

            linha.setWidthPercentage(100);

            PdfPCell cellLinha = new PdfPCell();

            cellLinha.setFixedHeight(2);

            cellLinha.setBorder(
                    Rectangle.BOTTOM);

            linha.addCell(cellLinha);

            document.add(linha);

            document.add(new Paragraph(" "));

        } catch (Exception e) {

            throw new RuntimeException(
                    "Erro ao adicionar cabeçalho",
                    e);
        }
    }

    private void adicionarCabecalhoTabela(
            PdfPTable tabela,
            String texto) {

        Font fonteCabecalho = FontFactory.getFont(
                FontFactory.HELVETICA_BOLD,
                11);

        PdfPCell cell = new PdfPCell(
                new Phrase(texto, fonteCabecalho));

        cell.setHorizontalAlignment(
                Element.ALIGN_CENTER);

        cell.setVerticalAlignment(
                Element.ALIGN_MIDDLE);

        tabela.addCell(cell);
    }

    private void adicionarRodape(Document document) {

        try {

            document.add(new Paragraph(" "));

            Font fonteRodape = FontFactory.getFont(
                    FontFactory.HELVETICA_OBLIQUE,
                    9);

            Paragraph rodape = new Paragraph(
                    "Documento gerado automaticamente pelo Sistema de Ensalamento Acadêmico (SEA).",
                    fonteRodape);

            rodape.setAlignment(Element.ALIGN_CENTER);

            document.add(rodape);

        } catch (Exception e) {

            throw new RuntimeException(
                    "Erro ao adicionar rodapé",
                    e);
        }
    }

    public byte[] gerarPdfEnsalamento() {

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            Document document = new Document(PageSize.A4.rotate());

            PdfWriter.getInstance(document, baos);

            document.open();

            adicionarCabecalho(
                    document,
                    "RELATÓRIO GERAL DE ENSALAMENTO");

            // ==========================
            // TABELA
            // ==========================

            PdfPTable tabela = new PdfPTable(6);

            tabela.setWidthPercentage(100);

            tabela.setWidths(
                    new float[] { 2f, 2f, 3f, 3f, 2f, 2f });

            adicionarCabecalhoTabela(tabela, "Dia");
            adicionarCabecalhoTabela(tabela, "Horário");
            adicionarCabecalhoTabela(tabela, "Turma");
            adicionarCabecalhoTabela(tabela, "Disciplina");
            adicionarCabecalhoTabela(tabela, "Professor");
            adicionarCabecalhoTabela(tabela, "Sala");

            List<Horario> horarios = horarioRepository.findAll();

            ordenarHorarios(horarios);

            for (Horario h : horarios) {

                tabela.addCell(h.getDiaSemana().toString());

                tabela.addCell(
                        h.getHoraInicio()
                                + " - "
                                + h.getHoraFim());

                tabela.addCell(
                        h.getTurma().getNome());

                tabela.addCell(
                        h.getDisciplina().getNome());

                tabela.addCell(
                        h.getProfessor().getNome());

                tabela.addCell(
                        h.getSala().getNome());
            }

            document.add(tabela);

            document.add(new Paragraph(" "));

            adicionarRodape(document);

            document.close();

            return baos.toByteArray();

        } catch (Exception e) {

            throw new RuntimeException(
                    "Erro ao gerar PDF",
                    e);
        }
    }

    public byte[] gerarPdfTurma(Long turmaId) {

        List<Horario> horarios = horarioRepository.findByTurmaIdOrderByDiaSemanaAscHoraInicioAsc(turmaId);

        ordenarHorarios(horarios);

        if (horarios.isEmpty()) {
            throw new RuntimeException("Turma não encontrada ou sem horários.");
        }

        Turma turma = horarios.get(0).getTurma();

        try {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            Document document = new Document(PageSize.A4);

            PdfWriter.getInstance(document, baos);

            document.open();

            adicionarCabecalho(
                    document,
                    "RELATÓRIO DA TURMA");

            Font destaque = FontFactory.getFont(
                    FontFactory.HELVETICA_BOLD);

            Paragraph turmaInfo = new Paragraph();

            turmaInfo.add(
                    new Phrase(
                            "Turma: ",
                            destaque));

            turmaInfo.add(
                    new Phrase(
                            turma.getNome()));

            document.add(turmaInfo);

            Paragraph cursoInfo = new Paragraph();

            cursoInfo.add(
                    new Phrase(
                            "Curso: ",
                            destaque));

            cursoInfo.add(
                    new Phrase(
                            turma.getCurso()));

            document.add(cursoInfo);

            Paragraph periodoInfo = new Paragraph();

            periodoInfo.add(
                    new Phrase(
                            "Período: ",
                            destaque));

            periodoInfo.add(
                    new Phrase(
                            turma.getPeriodo().toString()));

            document.add(periodoInfo);

            document.add(new Paragraph(" "));

            PdfPTable tabela = new PdfPTable(5);

            tabela.setWidthPercentage(100);

            tabela.setWidths(new float[] {
                    3f,
                    3f,
                    2f,
                    2f,
                    2.5f
            });

            adicionarCabecalhoTabela(tabela, "Disciplina");
            adicionarCabecalhoTabela(tabela, "Professor");
            adicionarCabecalhoTabela(tabela, "Sala");
            adicionarCabecalhoTabela(tabela, "Dia");
            adicionarCabecalhoTabela(tabela, "Horário");

            for (Horario h : horarios) {

                tabela.addCell(h.getDisciplina().getNome());

                tabela.addCell(h.getProfessor().getNome());

                tabela.addCell(h.getSala().getNome());

                tabela.addCell(h.getDiaSemana().name());

                tabela.addCell(
                        formatarHorario(h));
            }

            document.add(tabela);

            document.add(new Paragraph(" "));

            adicionarRodape(document);

            document.close();

            return baos.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] gerarPdfProfessor(Long professorId) {

        List<Horario> horarios = horarioRepository.findByProfessorIdOrderByDiaSemanaAscHoraInicioAsc(professorId);

        ordenarHorarios(horarios);

        if (horarios.isEmpty()) {
            throw new RuntimeException("Professor sem horários.");
        }

        Professor professor = horarios.get(0).getProfessor();

        try {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            Document document = new Document(PageSize.A4);

            PdfWriter.getInstance(document, baos);

            document.open();

            adicionarCabecalho(
                    document,
                    "RELATÓRIO DO PROFESSOR");

            Font destaque = FontFactory.getFont(
                    FontFactory.HELVETICA_BOLD);

            Paragraph professorInfo = new Paragraph();

            professorInfo.add(
                    new Phrase(
                            "Professor: ",
                            destaque));

            professorInfo.add(
                    new Phrase(
                            professor.getNome()));

            document.add(professorInfo);

            Paragraph areaInfo = new Paragraph();

            areaInfo.add(
                    new Phrase(
                            "Área de atuação: ",
                            destaque));

            areaInfo.add(
                    new Phrase(
                            professor.getArea().toString()));

            document.add(areaInfo);

            document.add(
                    new Paragraph(
                            "Quantidade de aulas: "
                                    + horarios.size()));

            document.add(new Paragraph(" "));

            document.add(new Paragraph(" "));

            PdfPTable tabela = new PdfPTable(6);

            tabela.setWidthPercentage(100);

            tabela.setWidths(new float[] {
                    3f, // Disciplina
                    2f, // Turma
                    3f, // Curso
                    2f, // Sala
                    2f, // Dia
                    2.5f // Horário
            });

            adicionarCabecalhoTabela(tabela, "Disciplina");
            adicionarCabecalhoTabela(tabela, "Turma");
            adicionarCabecalhoTabela(tabela, "Curso");
            adicionarCabecalhoTabela(tabela, "Sala");
            adicionarCabecalhoTabela(tabela, "Dia");
            adicionarCabecalhoTabela(tabela, "Horário");

            for (Horario h : horarios) {

                tabela.addCell(h.getDisciplina().getNome());

                tabela.addCell(h.getTurma().getNome());

                tabela.addCell(h.getTurma().getCurso());

                tabela.addCell(h.getSala().getNome());

                tabela.addCell(h.getDiaSemana().name());

                tabela.addCell(
                        formatarHorario(h));
            }

            document.add(tabela);

            document.add(new Paragraph(" "));

            adicionarRodape(document);

            document.close();

            return baos.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
