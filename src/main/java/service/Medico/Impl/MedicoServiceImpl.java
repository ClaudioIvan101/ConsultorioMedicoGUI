package service.Medico.Impl;

import domain.CalendarioMensual;
import domain.Medico;
import domain.RangoHorario;
import domain.enums.Estado;
import service.Medico.MedicoService;
import service.Turno.TurnoService;
import domain.Turno;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static bd.MedicoBd.MedicoBd.MEDICOS;

public class MedicoServiceImpl implements MedicoService {
    private final Map<Medico, Map<YearMonth, CalendarioMensual>> calendarios = new HashMap<>();
    private List<Medico> medicos = MEDICOS;
    private TurnoService turnoService;
    private Map<YearMonth, CalendarioMensual> calendarioMensual;

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

        Map<DayOfWeek, List<LocalTime>> calendario = configurarCalendarioLaboral(sc, DIAS_SEMANA_ES);
        nuevoMedico.setCalendario(calendario);

        medicos.add(nuevoMedico);
        generarTurnosParaMes(nuevoMedico, YearMonth.now());
        turnoService.inicializarCalendarioMedico(nuevoMedico);
        System.out.println("✅ Médico creado e inicializado correctamente.");
    }

    @Override
    public Medico actualizarMedico() {
        listarMedicos();
        Scanner sc = new Scanner(System.in);

        while (true) {
            Medico medico = getMedicoById();

            if (medico != null) {
                System.out.println("Medico encontrado bajo el nombre: " + medico.getNombre());

                System.out.println("Ingrese su nombre y apellido actualizado:");
                String nuevoNombre = sc.nextLine();
                medico.setNombre(nuevoNombre);

                System.out.println("Ingrese la especialidad del médico:");
                String nuevaEspecialidad = sc.nextLine();
                medico.setEspecialidad(nuevaEspecialidad);

                System.out.println("Medico actualizado con éxito."
                        + "\n Nombre: " + medico.getNombre()
                        + "\n Especialidad: " + medico.getEspecialidad()
                        + "\n ID: " + medico.getId());
                return medico;
            }

            System.out.println("Medico no encontrado. \n" +
                    "1-Reintentar \n" +
                    "2-Registrar \n" +
                    "3-Salir \n");
            int opcion = sc.nextInt();
            switch (opcion) {
                case 1: continue;
                case 2: crearMedico();
                case 3: return null;
                default: System.out.println("Opción inválida");
            }
        }


    }

    @Override
    public void eliminarMedico() {
        Scanner sc = new Scanner(System.in);
        int opcion;
        listarMedicos();
        Medico medicoAEliminar = getMedicoById();
        do {
            System.out.println("¿Está seguro que desea eliminar al Medico: " + medicoAEliminar.getNombre()
                    +"\n 1. Eliminar"
                    +"\n 2. No eliminar");
            opcion = sc.nextInt();

            switch(opcion) {
                case 1:
                    medicos.remove(medicoAEliminar);
                    System.out.println("Medico eliminado con éxito");
                    return;
                case 2:
                    return;
                default:
                    System.out.println("Opcion no valida");
            }
        }while (true);
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

        System.out.println("🔎 Buscando médicos con especialidad: " + especialidad);

        for (Medico medico : medicos) {
            if (medico.getEspecialidad().equalsIgnoreCase(especialidad)) {
                resultado.add(medico);
                System.out.println("✅ Médico disponible:");
                System.out.println("   Nombre: " + medico.getNombre());
                System.out.println("   ID: " + medico.getId());
            }
        }
        return resultado;
    }


    @Override
    public void listarMedicos() {
        System.out.println("---LISTA DE MEDICOS---");
        for (Medico m : medicos) {
            System.out.println("Medico: " + m.getNombre());
            System.out.println("Especialidad: " + m.getEspecialidad());
            System.out.println("ID del medico: " + m.getId());
            System.out.println("\n");
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

    private Map<DayOfWeek, List<LocalTime>> configurarCalendarioLaboral(Scanner sc, Map<String, DayOfWeek> diasSemana) {
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

        return calendario;
    }


    public void setTurnoService(TurnoService turnoService) {
        this.turnoService = turnoService;
    }

    private Medico buscarMedicoPorId(UUID id) {
        for (Medico m : medicos) { // `medicos` debe ser una lista de tu sistema
            if (m.getId().equals(id)) {
                return m;
            }
        }
        return null;
    }

    @Override
    public Medico getMedicoById() {
        Scanner sc = new Scanner(System.in);
        boolean valido;
        Medico medico = null;

        do {
            System.out.println("Ingrese el ID del médico (formato UUID): ");
            String input = sc.nextLine();

            try {
                UUID id = UUID.fromString(input);
                medico = buscarMedicoPorId(id);
                if (medico != null) {
                    valido = true;
                } else {
                    System.out.println("No se encontró un médico con ese ID.");
                    valido = false;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: Ingrese un UUID válido.");
                valido = false;
            }
        } while (!valido);

        return medico;
    }

    @Override
    public void consultarDisponibilidad() {
        listarMedicos();
        Medico medico = getMedicoById();

        if (medico == null) {
            System.out.println("❌ Médico no encontrado.");
            return;
        }

        Map<YearMonth, CalendarioMensual> calendarioMensual = medico.getCalendarioMensual();

        if (calendarioMensual == null || calendarioMensual.isEmpty()) {
            System.out.println("📭 El médico no tiene turnos generados.");
            return;
        }

        System.out.println("📅 Disponibilidad del médico: " + medico.getNombre() + "\n");

        // Map para agrupar horarios por día
        Map<LocalDate, List<LocalTime>> disponibilidadPorDia = new TreeMap<>();

        for (CalendarioMensual calendario : calendarioMensual.values()) {
            for (Map.Entry<LocalDateTime, Turno> entry : calendario.getTurnos().entrySet()) {
                Turno turno = entry.getValue();
                if (turno != null && turno.getEstado() == Estado.LIBRE) {
                    LocalDateTime fechaHora = entry.getKey();
                    disponibilidadPorDia
                            .computeIfAbsent(fechaHora.toLocalDate(), k -> new ArrayList<>())
                            .add(fechaHora.toLocalTime());
                }
            }
        }

        if (disponibilidadPorDia.isEmpty()) {
            System.out.println("❌ No hay turnos disponibles.");
            return;
        }

        // Mostrar por día
        DateTimeFormatter diaFormatter = DateTimeFormatter.ofPattern("EEEE dd/MM/yyyy", new Locale("es", "ES"));
        for (Map.Entry<LocalDate, List<LocalTime>> entry : disponibilidadPorDia.entrySet()) {
            LocalDate fecha = entry.getKey();
            List<LocalTime> horarios = entry.getValue();
            horarios.sort(Comparator.naturalOrder());

            String nombreDia = fecha.format(diaFormatter);
            System.out.println("📆 " + capitalizar(nombreDia));
            System.out.print("   Horarios: ");

            for (int i = 0; i < horarios.size(); i++) {
                System.out.print(horarios.get(i));
                if (i < horarios.size() - 1) System.out.print(" - ");
            }
            System.out.println("\n");
        }
    }

    // Utilidad para capitalizar el primer carácter (lunes → Lunes)
    private String capitalizar(String texto) {
        return texto.substring(0, 1).toUpperCase() + texto.substring(1);
    }



    @Override
    public Medico solicitarMedico(Scanner sc) {
        int opcion;
        do {
            System.out.println("¿Desea tomar turno por:");
            System.out.println("1. Especialidad");
            System.out.println("2. Nombre del médico");
            opcion = Integer.parseInt(sc.nextLine());
        }while(opcion != 1 && opcion != 2);

        if (opcion == 1) {
            System.out.println("-------Especialidades Disponibles------");
            listarEspecialidades();

            System.out.print("Ingrese la especialidad deseada: ");
            String especialidad = sc.nextLine();

            List<Medico> disponibles = listarMedicosPorEspecialidad(especialidad);
            for (Medico m : disponibles) {
                System.out.println("- " + m.getNombre());
            }
        }else{
            listarMedicos();
        }
        Medico medico = getMedicoById();
        return medico;
    }
    @Override
    public Map<YearMonth, CalendarioMensual> obtenerFechasDisponibles(Medico medico) {
        Map<YearMonth, CalendarioMensual> disponibles = new HashMap<>();

        Map<YearMonth, CalendarioMensual> calendarioMensual = medico.getCalendarioMensual();
        if (calendarioMensual == null) return disponibles;

        for (Map.Entry<YearMonth, CalendarioMensual> entry : calendarioMensual.entrySet()) {
            YearMonth mes = entry.getKey();
            CalendarioMensual calendario = entry.getValue();

            boolean tieneDisponible = calendario.getTurnos().values().stream()
                    .anyMatch(turno -> turno != null && turno.getEstado() == Estado.LIBRE);

            if (tieneDisponible) {
                disponibles.put(mes, calendario);
            }
        }
        return disponibles;
    }


    @Override
    public void generarTurnosParaMes(Medico medico, YearMonth mes) {
        CalendarioMensual calendarioMensual = new CalendarioMensual();

        Map<DayOfWeek, List<LocalTime>> calendarioBase = medico.getCalendario();

        for (DayOfWeek dia : calendarioBase.keySet()) {
            List<LocalTime> horarios = calendarioBase.get(dia);

            // Empezamos desde el primer día del mes
            LocalDate fecha = mes.atDay(1);
            while (fecha.getMonth() == mes.getMonth()) {
                if (fecha.getDayOfWeek() == dia) {
                    for (LocalTime hora : horarios) {
                        LocalDateTime fechaHora = LocalDateTime.of(fecha, hora);

                        LocalTime horaFin = hora.plusMinutes(30);
                        RangoHorario rango = new RangoHorario(hora, horaFin);

                        Turno turno = new Turno(
                                UUID.randomUUID(),
                                Estado.LIBRE,
                                null,
                                medico,
                                rango
                        );

                        calendarioMensual.agregarTurno(fechaHora, turno);
                    }
                }
                fecha = fecha.plusDays(1);
            }
        }
        if (medico.getCalendarioMensual() == null) {
            medico.setCalendarioMensual(new HashMap<>());
        }

        medico.getCalendarioMensual().put(mes, calendarioMensual);
    }

}
