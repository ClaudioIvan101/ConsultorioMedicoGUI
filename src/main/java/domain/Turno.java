package domain;

import domain.enums.Estado;

import java.util.UUID;


public class Turno {
    private UUID id;
    private Estado estado;
    private Paciente paciente;
    private Medico medico;
    private RangoHorario horario;

    public Turno(UUID id, Estado estado, Paciente paciente, Medico medico, RangoHorario horario) {
        this.id = id;
        this.estado = estado;
        this.paciente = paciente;
        this.medico = medico;
        this.horario = horario;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public RangoHorario getHorario() {
        return horario;
    }

    public void setHorario(RangoHorario horario) {
        this.horario = horario;
    }
}
