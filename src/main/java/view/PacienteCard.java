package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import controller.PacienteController;
import model.Paciente;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class PacienteCard extends JPanel {

    private JTextField tfNome, tfCpf, tfDataNascimento, tfTelefone, tfEndereco, tfEmail;
    private JButton btnSalvarPaciente;
    private PacienteController pacienteController;

    public PacienteCard() {
        // Inicializa o controller
        pacienteController = new PacienteController();

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Cabeçalho
        JLabel titulo = new JLabel("Cadastro de Pacientes", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titulo, gbc);

        // Nome
        JLabel lblNome = new JLabel("Nome:", SwingConstants.LEFT);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        add(lblNome, gbc);

        tfNome = new JTextField();
        tfNome.setPreferredSize(new Dimension(tfNome.getPreferredSize().width, 30)); // Altura fixa
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(tfNome, gbc);

        // CPF
        JLabel lblCpf = new JLabel("CPF:", SwingConstants.LEFT);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        add(lblCpf, gbc);

        tfCpf = new JTextField();
        tfCpf.setPreferredSize(new Dimension(tfCpf.getPreferredSize().width, 30)); // Altura fixa
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(tfCpf, gbc);

        // Data de Nascimento
        JLabel lblDataNascimento = new JLabel("Data Nascimento (dd/mm/aaaa):", SwingConstants.LEFT);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        add(lblDataNascimento, gbc);

        tfDataNascimento = new JTextField();
        tfDataNascimento.setPreferredSize(new Dimension(tfDataNascimento.getPreferredSize().width, 30)); // Altura fixa
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(tfDataNascimento, gbc);

        // Telefone
        JLabel lblTelefone = new JLabel("Telefone:", SwingConstants.LEFT);
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        add(lblTelefone, gbc);

        tfTelefone = new JTextField();
        tfTelefone.setPreferredSize(new Dimension(tfTelefone.getPreferredSize().width, 30)); // Altura fixa
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(tfTelefone, gbc);

        // Endereço
        JLabel lblEndereco = new JLabel("Endereço:", SwingConstants.LEFT);
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        add(lblEndereco, gbc);

        tfEndereco = new JTextField();
        tfEndereco.setPreferredSize(new Dimension(tfEndereco.getPreferredSize().width, 30)); // Altura fixa
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(tfEndereco, gbc);

        // Email
        JLabel lblEmail = new JLabel("Email:", SwingConstants.LEFT);
        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        add(lblEmail, gbc);

        tfEmail = new JTextField();
        tfEmail.setPreferredSize(new Dimension(tfEmail.getPreferredSize().width, 30)); // Altura fixa
        gbc.gridx = 0;
        gbc.gridy = 12;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(tfEmail, gbc);

        // Botão Salvar
        btnSalvarPaciente = new JButton("Salvar Paciente");
        btnSalvarPaciente.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 13;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        add(btnSalvarPaciente, gbc);
        btnSalvarPaciente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarPaciente();
            }
        });
    }

    private void salvarPaciente() {
        String nome = tfNome.getText();
        String cpf = tfCpf.getText();
        String dataNascimento = tfDataNascimento.getText();
        String telefone = tfTelefone.getText();
        String endereco = tfEndereco.getText();
        String email = tfEmail.getText();

        // Validação dos dados
        if (nome.isEmpty() || cpf.isEmpty() || dataNascimento.isEmpty() || telefone.isEmpty() || endereco.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.");
            return;
        }

        // Validação do CPF
        if (!validarCPF(cpf)) {
            JOptionPane.showMessageDialog(this, "CPF inválido.");
            return;
        }

        // Validação da data de nascimento
        try {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            dateFormatter.parse(dataNascimento);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Formato de data inválido. Use dd/mm/aaaa.");
            return;
        }

        // Validação do email (simples)
        if (!email.contains("@")) {
            JOptionPane.showMessageDialog(this, "Email inválido.");
            return;
        }

        Paciente paciente = new Paciente(nome, cpf, dataNascimento, telefone, endereco, email);
        pacienteController.cadastrarPaciente(paciente);
        JOptionPane.showMessageDialog(this, "Paciente cadastrado com sucesso!");

        // Limpa os campos após o cadastro
        tfNome.setText("");
        tfCpf.setText("");
        tfDataNascimento.setText("");
        tfTelefone.setText("");
        tfEndereco.setText("");
        tfEmail.setText("");
    }

    // Método para validar CPF
    private boolean validarCPF(String cpf) {
        // Remove caracteres não numéricos
        cpf = cpf.replaceAll("[^0-9]", "");
        if (cpf.length() != 11) {
            return false;
        }
        // Considera CPFs com todos os dígitos iguais inválidos
        if (cpf.equals("00000000000") || cpf.equals("11111111111") || cpf.equals("22222222222") ||
                cpf.equals("33333333333") || cpf.equals("44444444444") || cpf.equals("55555555555") ||
                cpf.equals("66666666666") || cpf.equals("77777777777") || cpf.equals("88888888888") ||
                cpf.equals("99999999999")) {
            return false;
        }
        char digito10, digito11;
        int sm, i, r, num, peso;
        // "Peso 9"
        sm = 0;
        peso = 10;
        for (i = 0; i < 9; i++) {
            // converte o i-ésimo caractere do CPF em um número
            num = (int) (cpf.charAt(i) - 48);
            sm = sm + (num * peso);
            peso = peso - 1;
        }
        r = 11 - (sm % 11);
        if ((r == 10) || (r == 11))
            digito10 = '0';
        else digito10 = (char) (r + 48); // converte no respectivo caractere
        // "Peso 10"
        sm = 0;
        peso = 11;
        for (i = 0; i < 10; i++) {
            num = (int) (cpf.charAt(i) - 48);
            sm = sm + (num * peso);
            peso = peso - 1;
        }
        r = 11 - (sm % 11);
        if ((r == 10) || (r == 11))
            digito11 = '0';
        else digito11 = (char) (r + 48);
        // Verifica se os dígitos calculados conferem com os dígitos reais.
        if ((digito10 == cpf.charAt(9)) && (digito11 == cpf.charAt(10)))
            return (true);
        else return (false);
    }
}

