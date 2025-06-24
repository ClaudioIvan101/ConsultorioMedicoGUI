package service.CalendarioMedico;


import domain.CalendarioMedico;
import domain.Turno;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface CalendarioMedicoService {
    //public void cargarCalendarioProximos30Dias();
    public List<Turno> obtenerTurnosDisponibles(CalendarioMedico calendario,LocalDate fecha);
   // public boolean asignarTurno(LocalDate fecha, LocalTime hora, String nombrePaciente);
    public void bloquearTurnosPeriodo(CalendarioMedico calendario,LocalDate desde, LocalDate hasta);
    boolean estaDisponible(CalendarioMedico calendario, LocalDateTime fechaHora);
}
