package service.CalendarioMedico.Impl;

import domain.CalendarioMedico;
import domain.RangoHorario;
import domain.Turno;
import domain.enums.Estado;
import java.time.Duration;
import service.CalendarioMedico.CalendarioMedicoService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import service.Turno.TurnoService;

public class CalendarioServiceImpl implements CalendarioMedicoService {
    private List<Turno> turno = new ArrayList<>();
    private TurnoService turnoService;
    private static final Duration DURACION_TURNO = Duration.ofMinutes(30);
    @Override
    public List<Turno> obtenerTurnosDisponibles(CalendarioMedico calendario,LocalDate fecha) {
     if (!calendario.getDiasLaborales().contains(fecha.getDayOfWeek())) {
            return Collections.emptyList(); // No trabaja ese día
        }
     
      List<Turno> turnosDelDia = calendario.getTurnosPorDia().get(fecha);
        if (turnosDelDia != null) {
            return turnosDelDia.stream()
                    .filter(t -> t.getEstado() == Estado.LIBRE)
                    .collect(Collectors.toList());
        }
        List<Turno> nuevosTurnos = generarTurnosParaFecha(fecha, calendario.getFranjasHorarias());
        calendario.getTurnosPorDia().put(fecha, nuevosTurnos);

        return nuevosTurnos;
    }

    @Override
    public void bloquearTurnosPeriodo(CalendarioMedico calendario, LocalDate desde, LocalDate hasta) {
    LocalDate actual = desde;
        while (!actual.isAfter(hasta)) {
            if (calendario.getDiasLaborales().contains(actual.getDayOfWeek())) {
                List<Turno> turnos = calendario.getTurnosPorDia().get(actual);
                if (turnos == null) {
                    turnos = generarTurnosParaFecha(actual, calendario.getFranjasHorarias());
                    calendario.getTurnosPorDia().put(actual, turnos);
                }

                for (Turno turno : turnos) {
                    turno.setEstado(Estado.OCUPADO);
                    turno.setPaciente(null);
                }
            }
            actual = actual.plusDays(1);
        }   
    }

    @Override
    public boolean estaDisponible(CalendarioMedico calendario, LocalDateTime fechaHora) {
     List<Turno> turnos = calendario.getTurnosPorDia().get(fechaHora.toLocalDate());
        if (turnos == null) return false;

        return turnos.stream().anyMatch(t ->
                t.getFecha().equals(fechaHora.toLocalDate()) &&
                t.getHorario().equals(fechaHora.toLocalTime()) &&
                t.getEstado() == Estado.LIBRE
        );
    }
    
    // Método privado auxiliar
    private List<Turno> generarTurnosParaFecha(LocalDate fecha, List<RangoHorario> franjasHorarias) {
        List<Turno> turnos = new ArrayList<>();
        for (RangoHorario franja : franjasHorarias) {
            LocalTime hora = franja.getInicio();
            while (!hora.isAfter(franja.getFin().minus(DURACION_TURNO))) {
                Turno turno = new Turno();
                turno.setFecha(fecha);
                turno.setHorario(hora);
                turno.setEstado(Estado.LIBRE);
                turno.setPaciente(null);
                turnos.add(turno);
                hora = hora.plus(DURACION_TURNO);
            }
        }
        return turnos;
    }
}
