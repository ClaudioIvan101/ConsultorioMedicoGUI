package domain;


import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Medico {
  private UUID id;
  private String nombre;
  private String especialidad;
  private Map<DayOfWeek, List<LocalTime>> calendario;// calendario base
  private Map<YearMonth, CalendarioMensual> calendarioMensual; // calendario generado

// Getters y setters


  public Medico(UUID id, String nombre, String especialidad, Map<DayOfWeek, List<LocalTime>> calendario, Map<YearMonth, CalendarioMensual> calendarioMensual) {
    this.id = id;
    this.nombre = nombre;
    this.especialidad = especialidad;
    this.calendario = calendario;
  }

  public Medico() {

  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getEspecialidad() {
    return especialidad;
  }

  public void setEspecialidad(String especialidad) {
    this.especialidad = especialidad;
  }

  public Map<DayOfWeek, List<LocalTime>> getCalendario() {
    return calendario;
  }

  public void setCalendario(Map<DayOfWeek, List<LocalTime>> calendario) {
    this.calendario = calendario;
  }

  public Map<YearMonth, CalendarioMensual> getCalendarioMensual() {
    return calendarioMensual;
  }

  public void setCalendarioMensual(Map<YearMonth, CalendarioMensual> calendarioMensual) {
    this.calendarioMensual = calendarioMensual;
  }

}
