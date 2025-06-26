package domain;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class CalendarioMensual {
    private Map<LocalDateTime, Turno> turnos;

    public CalendarioMensual() {
        this.turnos = new HashMap<>();
    }

    public void agregarTurno(LocalDateTime fechaHora, Turno turno) {
        turnos.put(fechaHora, turno);
    }

    public Map<LocalDateTime, Turno> getTurnos() {
        return turnos;
    }

    public Turno getTurno(LocalDateTime fechaHora) {
        return turnos.get(fechaHora);
    }
}
