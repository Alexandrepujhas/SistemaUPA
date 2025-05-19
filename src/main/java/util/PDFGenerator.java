package util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.util.List; // Importação correta de java.util.List

public class PDFGenerator {
    public static void gerarPDF(String titulo, String[] colunas, List<String[]> dados, String caminho) {
        Document doc = new Document();
        try {
            PdfWriter.getInstance(doc, new FileOutputStream(caminho));
            doc.open();

            // Título
            doc.add(new Paragraph(titulo, new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD)));
            doc.add(Chunk.NEWLINE);

            // Tabela
            PdfPTable table = new PdfPTable(colunas.length);

            // Cabeçalho
            for (String coluna : colunas) {
                table.addCell(new PdfPCell(new Phrase(coluna)));
            }

            // Dados
            for (String[] linha : dados) {
                for (String celula : linha) {
                    table.addCell(celula);
                }
            }

            doc.add(table);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            doc.close();
        }
    }
}
