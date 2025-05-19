package controller;

import model.Paciente;
import java.io.*;
import java.util.*;

public class PacienteController {
    private static final String ARQUIVO = "pacientes.txt";

    public static void salvarPaciente(Paciente p) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO, true))) {
            bw.write(p.getNome() + ";" + p.getCpf() + ";" + p.getDataNascimento() + ";" +
                    p.getTelefone() + ";" + p.getEndereco() + ";" + p.getEmail());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Paciente> listarPacientes() {
        List<Paciente> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length == 6) {
                    Paciente p = new Paciente(dados[0], dados[1], dados[2], dados[3], dados[4], dados[5]);
                    lista.add(p);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void cadastrarPaciente(Paciente paciente) {
        // Valida o CPF antes de cadastrar o paciente
        if (!validarCPF(paciente.getCpf())) {
            System.out.println("CPF inválido: " + paciente.getCpf());
            return; // Encerra o método se o CPF for inválido
        }
        // Aqui você adicionaria a lógica para salvar o paciente no banco de dados
        // Por enquanto, estou apenas reutilizando o método salvarPaciente
        salvarPaciente(paciente);
        System.out.println("Paciente cadastrado: " + paciente);
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

