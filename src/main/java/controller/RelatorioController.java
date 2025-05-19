package controller;

import model.Consulta;
import model.Paciente;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.time.LocalDate;
import java.util.stream.Collectors;

public class RelatorioController {

    private static ConsultaController consultaController = new ConsultaController(); // Create an instance

    // Relatório de Pacientes
    public static String gerarRelatorioPacientes() {
        StringBuilder sb = new StringBuilder();
        sb.append("RELATÓRIO DE PACIENTES\n\n");
        // Assuming PacienteController has a static method.  If not, you'll need an instance.
        List<Paciente> pacientes = PacienteController.listarPacientes();
        for (Paciente p : pacientes) {
            sb.append(p.getNome()).append(" | CPF: ").append(p.getCpf()).append("\n");
        }
        return sb.toString();
    }

    // Relatório de Consultas por Período
    public static List<Consulta> filtrarConsultasPorPeriodo(LocalDate inicio, LocalDate fim) {
        return consultaController.listarConsultas().stream() // Use the instance created.
                .filter(c -> {
                    LocalDate dataConsulta = c.getData(); //  c.getData() returns LocalDate
                    return !dataConsulta.isBefore(inicio) && !dataConsulta.isAfter(fim);
                })
                .collect(Collectors.toList());
    }
}
