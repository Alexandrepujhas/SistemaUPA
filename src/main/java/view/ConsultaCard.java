package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import controller.ConsultaController;
import model.Consulta;
import model.Paciente; // Import Paciente
import model.Medico;   // Import Medico
import model.Especialidade;
import java.util.ArrayList;

public class ConsultaCard extends JPanel {

    private JComboBox<Paciente> comboPacientes; // Use Paciente objects
    private JComboBox<Medico> comboMedicos;     // Use Medico objects
    private JComboBox<Especialidade> comboEspecialidades;
    private JTextField tfData;
    private JTextField tfHora;
    private JButton btnAgendarConsulta;
    private ConsultaController consultaController;

    public ConsultaCard() {
        // Inicializa os controllers
        consultaController = new ConsultaController();

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0; // Faz os componentes se expandirem horizontalmente

        // Cabeçalho
        JLabel titulo = new JLabel("Agendamento de Consultas", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titulo, gbc);

        // Paciente
        JLabel lblPaciente = new JLabel("Paciente:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(lblPaciente, gbc);

        comboPacientes = new JComboBox<>();
        preencherComboBoxPacientes();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(comboPacientes, gbc);

        // Médico
        JLabel lblMedico = new JLabel("Médico:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        add(lblMedico, gbc);

        comboMedicos = new JComboBox<>();
        preencherComboBoxMedicos();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        add(comboMedicos, gbc);

        // Especialidade
        JLabel lblEspecialidade = new JLabel("Especialidade:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        add(lblEspecialidade, gbc);

        comboEspecialidades = new JComboBox<>();
        preencherComboBoxEspecialidades();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        add(comboEspecialidades, gbc);

        // Data
        JLabel lblData = new JLabel("Data (dd/mm/aaaa):");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        add(lblData, gbc);

        tfData = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        add(tfData, gbc);

        // Hora
        JLabel lblHora = new JLabel("Hora (hh:mm):");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        add(lblHora, gbc);

        tfHora = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        add(tfHora, gbc);

        // Botão Agendar
        btnAgendarConsulta = new JButton("Agendar Consulta");
        btnAgendarConsulta.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        add(btnAgendarConsulta, gbc);
        btnAgendarConsulta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agendarConsulta();
            }
        });
    }

    private void preencherComboBoxPacientes() {
        List<Paciente> pacientes = consultaController.listarPacientes();
        comboPacientes.removeAllItems();
        for (Paciente paciente : pacientes) {
            comboPacientes.addItem(paciente);
        }
    }

    private void preencherComboBoxMedicos() {
        // Precisa buscar a lista de médicos do banco de dados
        List<Medico> medicos = consultaController.listarMedicos(); // Corrigido para usar ConsultaController
        comboMedicos.removeAllItems();
        for (Medico medico : medicos) {
            comboMedicos.addItem(medico);
        }
    }

    private void preencherComboBoxEspecialidades() {
        // Precisa buscar a lista de especialidades do banco de dados
        List<Especialidade> especialidades = consultaController.listarEspecialidades(); // Corrigido para usar ConsultaController
        comboEspecialidades.removeAllItems();
        for (Especialidade especialidade : especialidades) {
            comboEspecialidades.addItem(especialidade);
        }
    }

    private void agendarConsulta() {
        Paciente paciente = (Paciente) comboPacientes.getSelectedItem();
        Medico medico = (Medico) comboMedicos.getSelectedItem();
        Especialidade especialidade = (Especialidade) comboEspecialidades.getSelectedItem();
        String dataText = tfData.getText();
        String horaText = tfHora.getText();

        // Validação dos dados
        if (paciente == null || medico == null || especialidade == null || dataText.isEmpty() || horaText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.");
            return;
        }

        LocalDate data;
        LocalTime hora;
        // Validação da data
        try {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            data = LocalDate.parse(dataText, dateFormatter);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Formato de data inválido. Use dd/mm/aaaa.");
            return;
        }

        // Validação da hora
        try {
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            hora = LocalTime.parse(horaText, timeFormatter);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Formato de hora inválido. Use hh:mm.");
            return;
        }

        // Cria a consulta e agenda
        Consulta consulta = new Consulta(paciente, medico, especialidade, data, hora, ""); // Passa a especialidade
        consultaController.agendarConsulta(consulta);
        JOptionPane.showMessageDialog(this, "Consulta agendada com sucesso!");

        // Limpa os campos após o agendamento
        tfData.setText("");
        tfHora.setText("");
        comboPacientes.setSelectedIndex(0);
        comboMedicos.setSelectedIndex(0);
        comboEspecialidades.setSelectedIndex(0);
    }
}

