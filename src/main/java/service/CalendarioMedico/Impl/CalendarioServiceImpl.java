package service.CalendarioMedico.Impl;

import domain.Turno;
import service.CalendarioMedico.CalendarioMedicoService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class CalendarioServiceImpl implements CalendarioMedicoService {

    @Override
    public void cargarCalendarioProximos30Dias() {

    }

    @Override
    public List<Turno> obtenerTurnosDisponibles(LocalDate fecha) {
        return List.of();
    }

    @Override
    public boolean asignarTurno(LocalDate fecha, LocalTime hora, String nombrePaciente) {
        return false;
    }

    @Override
    public void bloquearTurnosPeriodo(LocalDate desde, LocalDate hasta) {

    }
}
