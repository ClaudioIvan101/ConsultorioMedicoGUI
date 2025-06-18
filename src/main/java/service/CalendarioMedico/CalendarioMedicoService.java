package service.CalendarioMedico;


import domain.Turno;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface CalendarioMedicoService {
    public void cargarCalendarioProximos30Dias();
    public List<Turno> obtenerTurnosDisponibles(LocalDate fecha);
    public boolean asignarTurno(LocalDate fecha, LocalTime hora, String nombrePaciente);
    public void bloquearTurnosPeriodo(LocalDate desde, LocalDate hasta);
}
