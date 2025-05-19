package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList; // Import ArrayList
import controller.ConsultaController;
import model.Consulta;

import javax.swing.table.DefaultTableModel;

public class HistoricoMedicoCard extends JPanel {

    private JComboBox<String> cbFiltro;
    private JTextField tfFiltroValor;
    private JButton btnBuscar;
    private JTable tabelaHistorico;
    private JTextArea taObservacoes;
    private JButton btnSalvarObservacoes;
    private ConsultaController consultaController;
    private DefaultTableModel modeloTabela;

    public HistoricoMedicoCard() {
        // Inicializa o controller
        consultaController = new ConsultaController();

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Cabeçalho
        JLabel titulo = new JLabel("Histórico Médico do Paciente", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titulo, gbc);

        // Filtro
        JLabel lblFiltro = new JLabel("Filtrar por:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(lblFiltro, gbc);

        String[] opcoesFiltro = {"Nome", "CPF"};
        cbFiltro = new JComboBox<>(opcoesFiltro);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        add(cbFiltro, gbc);

        tfFiltroValor = new JTextField();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(tfFiltroValor, gbc);

        btnBuscar = new JButton("Buscar");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        add(btnBuscar, gbc);
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarHistorico();
            }
        });

        // Tabela
        modeloTabela = new DefaultTableModel();
        tabelaHistorico = new JTable(modeloTabela);
        modeloTabela.addColumn("Data");
        modeloTabela.addColumn("Hora");
        modeloTabela.addColumn("Médico");
        modeloTabela.addColumn("Especialidade");
        JScrollPane scrollPane = new JScrollPane(tabelaHistorico);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        add(scrollPane, gbc);

        // Observações
        JLabel lblObservacoes = new JLabel("Observações/Anotações Médicas:");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weighty = 0;
        add(lblObservacoes, gbc);

        taObservacoes = new JTextArea(5, 20);
        taObservacoes.setWrapStyleWord(true);
        taObservacoes.setLineWrap(true);
        JScrollPane scrollObservacoes = new JScrollPane(taObservacoes);
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        add(scrollObservacoes, gbc);

        btnSalvarObservacoes = new JButton("Salvar Observações");
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        add(btnSalvarObservacoes, gbc);
        btnSalvarObservacoes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarObservacoes();
            }
        });
    }

    private void buscarHistorico() {
        String filtro = (String) cbFiltro.getSelectedItem();
        String filtroValor = tfFiltroValor.getText();

        if (filtroValor.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha o valor para filtrar a busca.");
            return;
        }

        List<Consulta> historico = new ArrayList<>();
        if (filtro.equals("Nome")) {
            historico = consultaController.listarConsultasPorNomePaciente(filtroValor);
        } else if (filtro.equals("CPF")) {
            historico = consultaController.listarConsultasPorCpfPaciente(filtroValor);
        }

        if (historico.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhuma consulta encontrada para o filtro informado.");
            modeloTabela.setRowCount(0); // Limpa a tabela
            taObservacoes.setText("");    // Limpa a área de texto
            return;
        }

        // Preenche a tabela com o histórico
        modeloTabela.setRowCount(0); // Limpa a tabela antes de preencher
        for (Consulta consulta : historico) {
            Object[] linha = {
                    consulta.getData(),
                    consulta.getHora(),
                    consulta.getMedico().getNome(),
                    consulta.getEspecialidade()
            };
            modeloTabela.addRow(linha);
        }

        // Exibe as observações da primeira consulta na área de texto
        if (historico.size() > 0) {
            taObservacoes.setText(historico.get(0).getObservacoes());
        } else {
            taObservacoes.setText("");
        }
    }

    private void salvarObservacoes() {
        int linhaSelecionada = tabelaHistorico.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione uma consulta na tabela para salvar as observações.");
            return;
        }

        String filtro = (String) cbFiltro.getSelectedItem();
        String filtroValor = tfFiltroValor.getText();

        List<Consulta> historico = new ArrayList<>();
        if (filtro.equals("Nome")) {
            historico = consultaController.listarConsultasPorNomePaciente(filtroValor);
        } else if (filtro.equals("CPF")) {
            historico = consultaController.listarConsultasPorCpfPaciente(filtroValor);
        }

        String observacoes = taObservacoes.getText();
        // Atualiza as observações da consulta selecionada
        if (historico != null && linhaSelecionada < historico.size()) {
            Consulta consulta = historico.get(linhaSelecionada);
            consulta.setObservacoes(observacoes);
            consultaController.atualizarConsulta(consulta); // Persiste a atualização
            JOptionPane.showMessageDialog(this, "Observações salvas com sucesso!");
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao salvar observações. Consulta não encontrada.");
        }
    }
}

