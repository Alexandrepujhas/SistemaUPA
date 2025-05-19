package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Consulta {
    private Paciente paciente;
    private Medico medico;
    private Especialidade especialidade; // Changed to Especialidade
    private LocalDate data;
    private LocalTime hora;
    private String observacoes;

    public Consulta(Paciente paciente, Medico medico, Especialidade especialidade, LocalDate data, LocalTime hora, String observacoes) { // Changed to Especialidade
        this.paciente = paciente;
        this.medico = medico;
        this.especialidade = especialidade;
        this.data = data;
        this.hora = hora;
        this.observacoes = observacoes;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getCpfPaciente() {
        return paciente.getCpf();
    }

    public String getNomeMedico() {
        return medico.getNome();
    }


    @Override
    public String toString() {
        return data + " " + hora + " - " + especialidade + " - " + medico.getNome(); //changed especialidade
    }
}
