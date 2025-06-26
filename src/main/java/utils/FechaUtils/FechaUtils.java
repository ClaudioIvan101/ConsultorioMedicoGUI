package utils.FechaUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class FechaUtils {
    public static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("HH:mm");
    public static final DateTimeFormatter FORMATO_FECHA_HORA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static LocalDate parseFecha(String fecha) {
        return LocalDate.parse(fecha, FORMATO_FECHA);
    }

    public static LocalTime parseHora(String hora) {
        return LocalTime.parse(hora, FORMATO_HORA);
    }

    public static String formatoFecha(LocalDate fecha) {
        return fecha.format(FORMATO_FECHA);
    }

    public static String formatoHora(LocalTime hora) {
        return hora.format(FORMATO_HORA);
    }

    public static String formatoFechaHora(LocalDateTime fechaHora) {
        return fechaHora.format(FORMATO_FECHA_HORA);
    }
}
