package service.Medico.Impl;

import domain.CalendarioMensual;
import domain.Medico;
import domain.Paciente;
import service.Medico.MedicoService;
import service.Turno.TurnoService;

import java.time.*;
import java.util.*;

public class MedicoServiceImpl implements MedicoService {
    private final Map<Medico, Map<YearMonth, CalendarioMensual>> calendarios = new HashMap<>();
    private List<Medico> medicos = new ArrayList<>();
    private TurnoService turnoService;


    public MedicoServiceImpl() {
    }

    private static final Map<String, DayOfWeek> DIAS_SEMANA_ES = Map.ofEntries(
            Map.entry("LUNES", DayOfWeek.MONDAY),
            Map.entry("MARTES", DayOfWeek.TUESDAY),
            Map.entry("MIERCOLES", DayOfWeek.WEDNESDAY),
            Map.entry("MIÉRCOLES", DayOfWeek.WEDNESDAY),
            Map.entry("JUEVES", DayOfWeek.THURSDAY),
            Map.entry("VIERNES", DayOfWeek.FRIDAY),
            Map.entry("SABADO", DayOfWeek.SATURDAY),
            Map.entry("SÁBADO", DayOfWeek.SATURDAY),
            Map.entry("DOMINGO", DayOfWeek.SUNDAY)
    );

    @Override
    public void crearMedico() {
        Scanner sc = new Scanner(System.in);
        Medico nuevoMedico = new Medico();

        nuevoMedico.setId(UUID.randomUUID());

        System.out.print("Ingrese el nombre y apellido del médico: ");
        String nombre = sc.nextLine();
        nuevoMedico.setNombre(nombre);

        System.out.print("Ingrese la especialidad del médico: ");
        String especialidad = sc.nextLine();
        nuevoMedico.setEspecialidad(especialidad);

        Map<DayOfWeek, List<LocalTime>> calendario = new HashMap<>();
        System.out.println("Configure los días y horarios laborales del médico:");

        while (true) {
            System.out.print("Ingrese el día (ej: lunes) o 'fin' para terminar: ");
            String diaInput = sc.nextLine().toUpperCase();
            if (diaInput.equals("FIN")) break;

            DayOfWeek dia = DIAS_SEMANA_ES.get(diaInput);
            if (dia == null) {
                System.out.println("❌ Día inválido. Intente nuevamente.");
                continue;
            }

            LocalTime inicio;
            try {
                System.out.print("Hora de inicio (HH:mm): ");
                inicio = LocalTime.parse(sc.nextLine());
            } catch (Exception e) {
                System.out.println("❌ Formato inválido. Use HH:mm (ej. 09:30)");
                continue;
            }

            LocalTime fin;
            try {
                System.out.print("Hora de fin (HH:mm): ");
                fin = LocalTime.parse(sc.nextLine());
            } catch (Exception e) {
                System.out.println("❌ Formato inválido. Use HH:mm (ej. 17:00)");
                continue;
            }

            if (!inicio.isBefore(fin)) {
                System.out.println("❌ La hora de inicio debe ser anterior a la de fin.");
                continue;
            }

            List<LocalTime> horarios = new ArrayList<>();
            for (LocalTime h = inicio; h.isBefore(fin); h = h.plusMinutes(30)) {
                horarios.add(h);
            }

            calendario.put(dia, horarios);
        }

        nuevoMedico.setCalendario(calendario);
        medicos.add(nuevoMedico);

        turnoService.inicializarCalendarioMedico(nuevoMedico);
        System.out.println("✅ Médico creado e inicializado correctamente.");
    }

    @Override
    public void actualizarMedico(String nombre) {
        // similar a crearMedico, con ajustes y confirmaciones
    }

    @Override
    public void eliminarMedico(String nombre) {
        Medico medico = buscarMedicoPorNombre(nombre);
        if (medico != null) {
            medicos.remove(medico);
            System.out.println("✅ Médico eliminado.");
        } else {
            System.out.println("❌ Médico no encontrado.");
        }
    }

    @Override
    public Medico buscarMedicoPorNombre(String nombre) {
        return medicos.stream()
                .filter(m -> m.getNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Medico> listarMedicosPorEspecialidad(String especialidad) {
        List<Medico> resultado = new ArrayList<>();
        for (Medico medico : medicos) {
            if (medico.getEspecialidad().equalsIgnoreCase(especialidad)) {
                resultado.add(medico);
            }
        }
        return resultado;
    }

    @Override
    public void listarMedicos() {
        for (Medico m : medicos) {
            System.out.println("Medico: " + m.getNombre());
            System.out.println("Especialidad: " + m.getEspecialidad());
        }
    }

    @Override
    public void listarEspecialidades() {
        HashSet<String> sinDuplicados = new HashSet<>();
        for (Medico medico : medicos) {
            sinDuplicados.add(medico.getEspecialidad());
        }
        for (String especialidad : sinDuplicados) {
            System.out.println(especialidad);
        }
    }

    public void setTurnoService(TurnoService turnoService) {
        this.turnoService = turnoService;
    }

}
