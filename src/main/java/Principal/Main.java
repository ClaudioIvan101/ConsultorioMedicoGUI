package Principal;

import domain.CalendarioMedico;
import domain.RangoHorario;
import domain.Turno;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;
import service.CalendarioMedico.Impl.CalendarioServiceImpl;
import java.util.Arrays;
import service.CalendarioMedico.CalendarioMedicoService;
public class Main {
    public static void main(String[] args) {
    // Crear calendario
        CalendarioMedico calendario = new CalendarioMedico();

        // Definir días laborales (ej: lunes a viernes)
        calendario.getDiasLaborales().addAll(Arrays.asList(
                DayOfWeek.MONDAY,
                DayOfWeek.TUESDAY,
                DayOfWeek.WEDNESDAY,
                DayOfWeek.THURSDAY,
                DayOfWeek.FRIDAY
        ));       
     RangoHorario mañana = new RangoHorario(LocalTime.of(8, 0), LocalTime.of(12, 0));
        calendario.getFranjasHorarias().add(mañana);
     
     // Crear el service
        CalendarioMedicoService calendarioService = new CalendarioServiceImpl();
     ///// reservar turno "vaciones" /////
        LocalDate desde = LocalDate.now();
        LocalDate hasta = LocalDate.of(2025, 7, 20);
        calendarioService.bloquearTurnosPeriodo(calendario, desde, hasta);
        // Fecha deseada para ver los turnos
        LocalDate fechaConsulta = LocalDate.of(2025, 7, 10);
        // Obtener turnos disponibles
        List<Turno> turnosDia = calendario.getTurnosPorDia().get(fechaConsulta);
        // Mostrar resultados
        System.out.println("Turnos disponibles para el " + fechaConsulta + ":");
       if (turnosDia != null) {
        for (Turno turno : turnosDia) {
        System.out.println("Hora: " + turno.getHorario() + " - Estado: " + turno.getEstado());
       }
    }
    }
}
