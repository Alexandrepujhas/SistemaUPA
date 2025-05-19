package view;

import controller.PacienteController; // Importação do Controller
import util.PDFGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList; // Importação de ArrayList
import java.util.stream.Collectors;

import model.Paciente; // Importe a classe Paciente

public class RelatorioCard extends JPanel {
    private JButton btnPacientesPDF, btnConsultasPDF, btnExportarExcel;
    private PacienteController pacienteController; // Adicione uma instância do controlador

    public RelatorioCard() {
        // Inicialize o controlador
        pacienteController = new PacienteController();

        setLayout(new BorderLayout());

        // Cabeçalho
        JLabel titulo = new JLabel("Relatórios", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        add(titulo, BorderLayout.NORTH);

        // Painel de botões
        JPanel botoesPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        botoesPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));

        btnPacientesPDF = criarBotaoRelatorio("📄 Gerar Relatório de Pacientes (PDF)");
        btnConsultasPDF = criarBotaoRelatorio("📅 Gerar Relatório de Consultas (PDF)");
        btnExportarExcel = criarBotaoRelatorio("📊 Exportar para Excel");

        botoesPanel.add(btnPacientesPDF);
        botoesPanel.add(btnConsultasPDF);
        botoesPanel.add(btnExportarExcel);

        add(botoesPanel, BorderLayout.CENTER);
    }

    private JButton criarBotaoRelatorio(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btn.setPreferredSize(new Dimension(300, 60));

        btn.addActionListener(e -> {
            if (btn == btnPacientesPDF) {
                gerarRelatorioPacientesPDF();
            } else if (btn == btnConsultasPDF) {
                new DialogoPeriodoRelatorio().setVisible(true);
            }
        });

        return btn;
    }

    private void gerarRelatorioPacientesPDF() {
        String[] colunas = {"Nome", "CPF", "Telefone"};
        List<String[]> dados = pacienteController.listarPacientes().stream() // Use o controlador
                .map(p -> new String[]{p.getNome(), p.getCpf(), p.getTelefone()})
                .collect(Collectors.toList());

        PDFGenerator.gerarPDF(
                "Relatório de Pacientes",
                colunas,
                dados,
                "relatorio_pacientes.pdf"
        );

        JOptionPane.showMessageDialog(this, "PDF gerado com sucesso!");
    }

    // Classe interna para seleção de período
    private class DialogoPeriodoRelatorio extends JDialog {
        private JTextField tfDataInicio, tfDataFim;
        private JButton btnGerar; // Mova a declaração para o escopo da classe

        public DialogoPeriodoRelatorio() {
            setTitle("Selecionar Período");
            setSize(300, 200);
            setModal(true);

            JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
            panel.add(new JLabel("Data Início (dd/mm/aaaa):"));
            tfDataInicio = new JTextField();
            panel.add(tfDataInicio);

            panel.add(new JLabel("Data Fim (dd/mm/aaaa):"));
            tfDataFim = new JTextField();
            panel.add(tfDataFim);

            btnGerar = new JButton("Gerar Relatório"); // Inicialize aqui
            btnGerar.addActionListener(this::gerarRelatorio);
            panel.add(btnGerar);

            add(panel);
        }

        private void gerarRelatorio(ActionEvent e) {
            // Implementar lógica de geração por período
            String dataInicio = tfDataInicio.getText();
            String dataFim = tfDataFim.getText();

            if (dataInicio.isEmpty() || dataFim.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, preencha as datas de início e fim.");
                return;
            }

            // Valide o formato da data (opcional)
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            try {
                formatter.parse(dataInicio);
                formatter.parse(dataFim);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Formato de data inválido. Use dd/mm/aaaa.");
                return;
            }

            // Chame o método para gerar o relatório (ainda não implementado)
            gerarRelatorioConsultasPDF(dataInicio, dataFim);
            dispose(); // Feche o diálogo após gerar o relatório
        }

        private void gerarRelatorioConsultasPDF(String dataInicio, String dataFim) {
            // TODO: Implemente a lógica para buscar as consultas no período especificado
            // Use o pacienteController para obter os dados das consultas do banco de dados
            // Exemplo (adapte para a sua lógica real):
            /*
            List<Consulta> consultas = pacienteController.listarConsultasPorPeriodo(dataInicio, dataFim);
            List<String[]> dados = consultas.stream()
                    .map(c -> new String[]{
                            c.getPaciente().getNome(),
                            c.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                            c.getMedico().getNome(),
                            c.getProcedimento()
                    })
                    .collect(Collectors.toList());

            String[] colunas = {"Paciente", "Data", "Médico", "Procedimento"};
            PDFGenerator.gerarPDF(
                    "Relatório de Consultas",
                    colunas,
                    dados,
                    "relatorio_consultas.pdf"
            );
            JOptionPane.showMessageDialog(this, "Relatório de Consultas gerado com sucesso!");
            */

            // Por enquanto, apenas exiba uma mensagem
            JOptionPane.showMessageDialog(this, "Relatório de Consultas gerado para o período de " + dataInicio + " a " + dataFim + " (Implementação pendente)!");
        }
    }
}
