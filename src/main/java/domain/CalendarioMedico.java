package domain;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

public class CalendarioMedico {
    private Set<DayOfWeek> diasLaborales = new HashSet<>();
    private List<RangoHorario> franjasHorarias = new ArrayList<>();
    private Map<LocalDate, List<Turno>> turnosPorDia = new HashMap<>();

    public CalendarioMedico() {
    }

    public CalendarioMedico(Set<DayOfWeek> diasLaborales, List<RangoHorario> franjasHorarias, Map<LocalDate, List<Turno>> turnosPorDia) {
        this.diasLaborales = diasLaborales;
        this.franjasHorarias = franjasHorarias;
        this.turnosPorDia = turnosPorDia;
    }

    public Set<DayOfWeek> getDiasLaborales() {
        return diasLaborales;
    }

    public void setDiasLaborales(Set<DayOfWeek> diasLaborales) {
        this.diasLaborales = diasLaborales;
    }

    public List<RangoHorario> getFranjasHorarias() {
        return franjasHorarias;
    }

    public void setFranjasHorarias(List<RangoHorario> franjasHorarias) {
        this.franjasHorarias = franjasHorarias;
    }

    public Map<LocalDate, List<Turno>> getTurnosPorDia() {
        return turnosPorDia;
    }

    public void setTurnosPorDia(Map<LocalDate, List<Turno>> turnosPorDia) {
        this.turnosPorDia = turnosPorDia;
    }
}
