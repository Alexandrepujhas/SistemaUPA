package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TelaPrincipal extends JFrame {
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private JSplitPane splitPane;
    private JPanel menuPanel; // Declare como atributo da classe

    public TelaPrincipal() {
        setTitle("Sistema Médico Moderno");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel principal com CardLayout
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.add(new PacienteCard(), "PACIENTES");
        cardPanel.add(new ConsultaCard(), "CONSULTAS");
        cardPanel.add(new RelatorioCard(), "RELATORIOS");

        // Menu Lateral
        menuPanel = new JPanel(); // Inicialize aqui
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(new Color(150, 150, 150));
        menuPanel.setPreferredSize(new Dimension(200, 400));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        JButton btnPacientes = criarBotaoMenu("👥 Pacientes", "PACIENTES");
        JButton btnConsultas = criarBotaoMenu("📅 Consultas", "CONSULTAS");
        JButton btnRelatorios = criarBotaoMenu("📊 Relatórios", "RELATORIOS");

        menuPanel.add(btnPacientes);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Reduz o espaço
        menuPanel.add(btnConsultas);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Reduz o espaço
        menuPanel.add(btnRelatorios);

        // Layout principal
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, menuPanel, cardPanel);
        splitPane.setDividerLocation(200);
        splitPane.setEnabled(true);

        add(splitPane);
    }

    private JButton criarBotaoMenu(String texto, String cardName) {
        JButton button = new JButton(texto);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setMaximumSize(new Dimension(180, 30)); // Reduz a altura
        button.setPreferredSize(new Dimension(180, 30)); // Adiciona preferred size
        button.setVerticalTextPosition(SwingConstants.CENTER); // Centraliza o texto verticalmente
        button.addActionListener((ActionEvent e) -> cardLayout.show(cardPanel, cardName));
        return button;
    }
}
