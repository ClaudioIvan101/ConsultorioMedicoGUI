package bd.MedicoBd;

import domain.Medico;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.*;

import service.Medico.Impl.MedicoServiceImpl;
import service.Medico.MedicoService;

public class MedicoBd {

    public static final List<Medico> MEDICOS = new ArrayList<>();
    static MedicoService medicoService = new MedicoServiceImpl();

    public static Map<DayOfWeek, List<LocalTime>> generarCalendarioLunesAViernes() {
        Map<DayOfWeek, List<LocalTime>> calendario = new HashMap<>();

        for (DayOfWeek dia : List.of(
                DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
                DayOfWeek.THURSDAY, DayOfWeek.FRIDAY)) {

            List<LocalTime> horarios = new ArrayList<>();
            LocalTime inicio = LocalTime.of(9, 0);
            LocalTime fin = LocalTime.of(12, 0);

            while (inicio.isBefore(fin)) {
                horarios.add(inicio);
                inicio = inicio.plusMinutes(30);
            }

            calendario.put(dia, horarios);
        }

        return calendario;
    }

    static {
        Medico m1 = new Medico();
        m1.setId(UUID.randomUUID());
        m1.setNombre("Dra. Ana García");
        m1.setEspecialidad("Cardiología");
        m1.setCalendario(generarCalendarioLunesAViernes());

        Medico m2 = new Medico();
        m2.setId(UUID.randomUUID());
        m2.setNombre("Dr. Juan Pérez");
        m2.setEspecialidad("Pediatría");
        m2.setCalendario(generarCalendarioLunesAViernes());

        // Generar turnos para cada médico
        medicoService.generarTurnosParaMes(m1, YearMonth.now());
        medicoService.generarTurnosParaMes(m2, YearMonth.now());

        MEDICOS.add(m1);
        MEDICOS.add(m2);
    }

}
