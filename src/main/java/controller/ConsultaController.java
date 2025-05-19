package controller;

import model.Consulta;
import model.Paciente;
import model.Medico;
import model.Especialidade; // Importa a classe de Especialidade
import java.io.*;
import java.util.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ConsultaController {
    private static final String ARQUIVO_CONSULTAS = "consultas.txt";
    private static final String ARQUIVO_ESPECIALIDADES = "especialidades.txt";
    private static final String ARQUIVO_MEDICOS = "medicos.txt";
    private static final String ARQUIVO_PACIENTES = "pacientes.txt";

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    private static List<Paciente> listaPacientes = new ArrayList<>();
    private static List<Medico> listaMedicos = new ArrayList<>();
    private static List<Especialidade> listaEspecialidades = new ArrayList<>(); // Lista de Especialidades

    public ConsultaController() {
        // Carrega os dados dos arquivos ao inicializar o controlador
        carregarPacientes();
        carregarMedicos();
        carregarEspecialidades();
    }

    // Método para salvar consulta no arquivo
    public void salvarConsulta(Consulta consulta) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARQUIVO_CONSULTAS, true))) {
            pw.println(consulta.getPaciente().getCpf() + ";" +
                    consulta.getMedico().getCpf() + ";" +
                    consulta.getEspecialidade().getNome() + ";" + // Salva o nome da especialidade
                    consulta.getData().format(dateFormatter) + ";" +
                    consulta.getHora().format(timeFormatter) + ";" +
                    (consulta.getObservacoes() != null ? consulta.getObservacoes() : ""));
        } catch (IOException e) {
            System.err.println("Erro ao salvar consulta: " + e.getMessage());
        }
    }

    // Método para listar consultas do arquivo
    public List<Consulta> listarConsultas() {
        List<Consulta> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO_CONSULTAS))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length >= 5) {
                    try {
                        String cpfPaciente = dados[0];
                        String cpfMedico = dados[1];
                        String nomeEspecialidade = dados[2];
                        LocalDate data = LocalDate.parse(dados[3], dateFormatter);
                        LocalTime hora = LocalTime.parse(dados[4], timeFormatter);
                        String observacoes = dados.length == 6 ? dados[5] : "";

                        Paciente paciente = buscarPacientePorCpf(cpfPaciente);
                        Medico medico = buscarMedicoPorCpf(cpfMedico);
                        Especialidade especialidade = buscarEspecialidadePorNome(nomeEspecialidade);

                        if (paciente != null && medico != null && especialidade != null) {
                            Consulta consulta = new Consulta(paciente, medico, especialidade, data, hora, observacoes);
                            lista.add(consulta);
                        } else {
                            System.err.println("Paciente, Médico ou Especialidade não encontrados para a consulta: " + linha);
                        }
                    } catch (DateTimeParseException | NullPointerException e) {
                        System.err.println("Erro ao analisar linha da consulta: " + linha + " - Erro: " + e.getMessage());
                    }
                } else {
                    System.err.println("Linha inválida no arquivo de consultas: " + linha);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo de consultas: " + e.getMessage());
        }
        return lista;
    }

    // Método para listar consultas por CPF do paciente
    public List<Consulta> listarConsultasPorCpfPaciente(String cpf) {
        List<Consulta> lista = new ArrayList<>();
        for (Consulta consulta : listarConsultas()) {
            if (consulta.getPaciente().getCpf().equals(cpf)) {
                lista.add(consulta);
            }
        }
        return lista;
    }

    // Método para listar consultas por nome do paciente
    public List<Consulta> listarConsultasPorNomePaciente(String nome) {
        List<Consulta> lista = new ArrayList<>();
        for (Consulta consulta : listarConsultas()) {
            if (consulta.getPaciente().getNome().equalsIgnoreCase(nome)) {
                lista.add(consulta);
            }
        }
        return lista;
    }

    // Método para carregar especialidades do arquivo
    private void carregarEspecialidades() {
        File arquivo = new File(ARQUIVO_ESPECIALIDADES);
        if (!arquivo.exists()) {
            try (PrintWriter pw = new PrintWriter(new FileWriter(arquivo))) {
                // Escreva algumas especialidades padrão no arquivo
                pw.println("Cardiologia");
                pw.println("Dermatologia");
                pw.println("Ortopedia");
                pw.println("Pediatria");
                pw.println("Ginecologia");
            } catch (IOException e) {
                System.err.println("Erro ao criar arquivo de especialidades padrão: " + e.getMessage());
            }
        }

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                try {
                    Especialidade especialidade = Especialidade.valueOf(linha.trim().toUpperCase());
                    listaEspecialidades.add(especialidade);
                } catch (IllegalArgumentException e) {
                    System.err.println("Especialidade inválida: " + linha);
                }        }
        } catch (IOException e) {
            System.err.println("Erro ao ler especialidades do arquivo: " + e.getMessage());
        }
    }


    // Método para listar especialidades
    public List<Especialidade> listarEspecialidades() {
        return listaEspecialidades;
    }

    // Método para buscar especialidade por nome
    private Especialidade buscarEspecialidadePorNome(String nome) {
        for (Especialidade especialidade : listaEspecialidades) {
            if (especialidade.getNome().equalsIgnoreCase(nome)) {
                return especialidade;
            }
        }
        return null;
    }

    // Método para carregar médicos do arquivo
    private void carregarMedicos() {
        File arquivo = new File(ARQUIVO_MEDICOS);
        if (!arquivo.exists()) {
            try (PrintWriter pw = new PrintWriter(new FileWriter(arquivo))) {
                // Escreva alguns médicos padrão no arquivo
                pw.println("12345678901;João Silva");
                pw.println("98765432109;Maria Oliveira");
            } catch (IOException e) {
                System.err.println("Erro ao criar arquivo de médicos padrão: " + e.getMessage());
            }
        }

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length == 2) {
                    String cpf = dados[0];
                    String nome = dados[1];
                    Medico medico = new Medico(cpf, nome);
                    listaMedicos.add(medico);
                }        }
        } catch (IOException e) {
            System.err.println("Erro ao ler médicos do arquivo: " + e.getMessage());
        }
    }


    // Método para listar médicos
    public List<Medico> listarMedicos() {
        return listaMedicos;
    }

    // Método para buscar médico por CPF
    private Medico buscarMedicoPorCpf(String cpf) {
        for (Medico medico : listaMedicos) {
            if (medico.getCpf().equals(cpf)) {
                return medico;
            }
        }
        return null;
    }

    // Método para carregar pacientes do arquivo
    private void carregarPacientes() {
        File arquivo = new File(ARQUIVO_PACIENTES);
        if (!arquivo.exists()) {
            try (PrintWriter pw = new PrintWriter(new FileWriter(arquivo))) {
                // Escreva alguns pacientes padrão no arquivo
                pw.println("11122233344;Carlos Souza");
                pw.println("55566677788;Ana Pereira");
            } catch (IOException e) {
                System.err.println("Erro ao criar arquivo de pacientes padrão: " + e.getMessage());
            }
        }

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length == 2) {
                    String cpf = dados[0];
                    String nome = dados[1];
                    //validarCPF(cpf);  // Chamada à validação de CPF
                    Paciente paciente = new Paciente(nome, cpf);
                    listaPacientes.add(paciente);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler pacientes do arquivo: " + e.getMessage());
        }
    }


    // Método para listar pacientes
    public List<Paciente> listarPacientes() {
        return listaPacientes;
    }

    // Método para buscar paciente por CPF
    private Paciente buscarPacientePorCpf(String cpf) {
        for (Paciente paciente : listaPacientes) {
            if (paciente.getCpf().equals(cpf)) {
                return paciente;
            }
        }
        return null;
    }

    // Método para atualizar consulta
    public void atualizarConsulta(Consulta consulta) {
        List<Consulta> listaConsultas = listarConsultas();
        for (int i = 0; i < listaConsultas.size(); i++) {
            Consulta c = listaConsultas.get(i);
            // Usa um identificador único para consulta (CPF do paciente, data e hora)
            if (c.getPaciente().getCpf().equals(consulta.getPaciente().getCpf()) &&
                    c.getData().equals(consulta.getData()) &&
                    c.getHora().equals(consulta.getHora())) {
                listaConsultas.set(i, consulta);
                break;
            }
        }
        // Reescreve o arquivo com a lista de consultas atualizada
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARQUIVO_CONSULTAS))) {
            for (Consulta c : listaConsultas) {
                pw.println(c.getPaciente().getCpf() + ";" +
                        c.getMedico().getCpf() + ";" +
                        c.getEspecialidade().getNome() + ";" +
                        c.getData().format(dateFormatter) + ";" +
                        c.getHora().format(timeFormatter) + ";" +
                        (c.getObservacoes() != null ? c.getObservacoes() : ""));
            }
        } catch (IOException e) {
            System.err.println("Erro ao atualizar arquivo de consultas: " + e.getMessage());
        }
    }

    public void agendarConsulta(Consulta consulta) {
        salvarConsulta(consulta);
    }
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
        int[] digitos = new int[11];
        for (int i = 0; i < 11; i++) {
            digitos[i] = Character.getNumericValue(cpf.charAt(i));
        }
        // Cálculo do primeiro dígito verificador
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += digitos[i] * (10 - i);
        }
        int resto = soma % 11;
        int digitoVerificador1 = (resto < 2) ? 0 : 11 - resto;
        if (digitos[9] != digitoVerificador1) {
            return false;
        }
        // Cálculo do segundo dígito verificador
        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += digitos[i] * (11 - i);
        }
        resto = soma % 11;
        int digitoVerificador2 = (resto < 2) ? 0 : 11 - resto;
        if (digitos[10] != digitoVerificador2) {
            return false;
        }
        return true;
    }
}

