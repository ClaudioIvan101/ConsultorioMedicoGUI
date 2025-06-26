package domain;

import domain.enums.Estado;

import java.time.LocalTime;

public class Turno {
    private Estado estado;
    private Paciente paciente;
    private Medico medico;
    private LocalTime horario;

    public Turno(Estado estado, Paciente paciente, Medico medico, LocalTime horario) {
        this.estado = estado;
        this.paciente = paciente;
        this.medico = medico;
        this.horario = horario;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
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

    public LocalTime getHorario() {
        return horario;
    }

    public void setHorario(LocalTime horario) {
        this.horario = horario;
    }
}
