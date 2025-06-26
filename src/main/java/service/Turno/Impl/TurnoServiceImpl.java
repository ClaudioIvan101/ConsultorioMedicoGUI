package service.Turno.Impl;

import domain.CalendarioMensual;
import domain.Medico;
import domain.Paciente;
import domain.Turno;
import domain.enums.Estado;
import domain.RangoHorario;
import service.Medico.MedicoService;
import service.Paciente.PacienteService;
import service.Turno.TurnoService;
import utils.FechaUtils.FechaUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.*;


public class TurnoServiceImpl implements TurnoService {

    private final Map<Medico, Map<YearMonth, CalendarioMensual>> calendarios = new HashMap<>();
    private final MedicoService medicoService;
    private final PacienteService pacienteService;

    public TurnoServiceImpl(MedicoService medicoService, PacienteService pacienteService) {
        this.medicoService = medicoService;
        this.pacienteService = pacienteService;
    }

    @Override
    public void crearTurno() {
        Scanner sc = new Scanner(System.in);
        Paciente paciente = pacienteService.registrarPaciente();

        Medico medico = solicitarMedico(sc);

        Map<YearMonth, CalendarioMensual> calendarioMedico = obtenerOCrearCalendarioMedico(medico);

        LocalDate fechaElegida = solicitarFecha(sc);
        if (fechaElegida == null) return;

        CalendarioMensual calMensual = calendarioMedico.get(YearMonth.from(fechaElegida));
        if (calMensual == null) {
            System.out.println("No hay turnos generados para esa fecha.");
            return;
        }

        List<LocalDateTime> disponibles = listarHorariosDisponibles(calMensual, fechaElegida);
        if (disponibles.isEmpty()) {
            System.out.println("No hay horarios disponibles para esa fecha.");
            return;
        }

        mostrarHorariosDisponibles(disponibles);

        LocalTime horaElegida = solicitarHora(sc);
        if (horaElegida == null) return;

        LocalDateTime fechaHora = LocalDateTime.of(fechaElegida, horaElegida);
        Turno turno = calMensual.getTurno(fechaHora);

        if (turno != null && turno.getEstado() == Estado.LIBRE) {
            turno.setEstado(Estado.OCUPADO);
            turno.setPaciente(paciente);
            System.out.println("Turno reservado correctamente para el " + FechaUtils.formatoFecha(LocalDate.from(fechaHora)));
        } else {
            System.out.println("Turno ya ocupado o no válido.");
        }
    }

    @Override
    public void editarTurno() {
        Scanner sc = new Scanner(System.in);

        Medico medico = solicitarMedico(sc);
        if (medico == null) return;

        Map<YearMonth, CalendarioMensual> calendario = calendarios.get(medico);
        if (calendario == null) {
            System.out.println("No hay calendario cargado para este médico.");
            return;
        }

        LocalDate fecha = solicitarFecha(sc);
        if (fecha == null) return;

        LocalTime hora = solicitarHora(sc);
        if (hora == null) return;

        Turno turno = obtenerTurno(calendario, fecha, hora);
        if (turno == null) return;

        if (turno.getEstado() == Estado.LIBRE) {
            System.out.println("El turno está libre, no se puede editar.");
            return;
        }

        //System.out.println("Desea modificar los datos del paciente ");
        // si desea modificar los datos:
        System.out.print("Ingrese el nuevo nombre del paciente: ");
        String nuevoNombre = sc.nextLine();
        Paciente paciente = pacienteService.buscarPacienteByNombre(nuevoNombre);
        if (paciente == null) {
            System.out.println("Paciente no registrado. Registrándolo...");
            pacienteService.registrarPaciente();
            paciente = pacienteService.buscarPacienteByNombre(nuevoNombre);
        }
        turno.setPaciente(paciente);
        System.out.println("✅ Turno actualizado correctamente.");
    }

    @Override
    public void eliminarTurno() {
        Scanner sc = new Scanner(System.in);

        Medico medico = solicitarMedico(sc);
        if (medico == null) return;

        Map<YearMonth, CalendarioMensual> calendario = calendarios.get(medico);
        if (calendario == null) {
            System.out.println("No hay calendario cargado para este médico.");
            return;
        }

        LocalDate fecha = solicitarFecha(sc);
        if (fecha == null) return;

        LocalTime hora = solicitarHora(sc);
        if (hora == null) return;

        Turno turno = obtenerTurno(calendario, fecha, hora);
        if (turno == null) return;

        if (turno.getEstado() == Estado.OCUPADO) {
            turno.setEstado(Estado.LIBRE);
            turno.setPaciente(null);
            System.out.println("Turno eliminado correctamente.");
        } else {
            System.out.println("El turno ya está libre o no existe.");
        }
    }

    @Override
    public void listarTurnosOcupados() {
        for (Map.Entry<Medico, Map<YearMonth, CalendarioMensual>> entry : calendarios.entrySet()) {
            Medico medico = entry.getKey();
            System.out.println("\nTurnos ocupados de: " + medico.getNombre());
            for (CalendarioMensual mensual : entry.getValue().values()) {
                mensual.getTurnos().entrySet().stream()
                        .filter(e -> e.getValue().getEstado() == Estado.OCUPADO)
                        .forEach(e -> System.out.println(
                                FechaUtils.formatoFechaHora(e.getKey()) + " - " + e.getValue().getPaciente().getNombre()
                        ));
            }
        }
    }

    @Override
    public void listarTurnosOcupadosByMedico(String nombre) {
        Medico medico = medicoService.buscarMedicoPorNombre(nombre);
        if (medico == null || !calendarios.containsKey(medico)) {
            System.out.println("Médico no encontrado o sin turnos.");
            return;
        }
        System.out.println("Turnos ocupados de: " + nombre);
        calendarios.get(medico).values().forEach(mensual -> mensual.getTurnos().entrySet().stream()
                .filter(e -> e.getValue().getEstado() == Estado.OCUPADO)
                .forEach(e -> System.out.println(
                        FechaUtils.formatoFechaHora(e.getKey()) + " - " + e.getValue().getPaciente().getNombre()
                )));
    }

    @Override
    public void registrarTurnosOcupadosPorPeriodo() {
        Scanner sc = new Scanner(System.in);
        Medico medico = solicitarMedico(sc);
        if (medico == null) return;

        Map<YearMonth, CalendarioMensual> calendario = calendarios.get(medico);
        if (calendario == null) {
            System.out.println("Médico no encontrado o sin calendario.");
            return;
        }

        LocalDate desde = null;
        do {
            System.out.print("Desde qué fecha desea bloquear (dd/MM/yyyy): ");
            String input = sc.nextLine();
            try {
                desde = FechaUtils.parseFecha(input);
            } catch (Exception e) {
                System.out.println("Fecha inválida, intente nuevamente.");
            }
        } while (desde == null);

        LocalDate hasta = null;
        do {
            System.out.print("Hasta qué fecha desea bloquear (dd/MM/yyyy): ");
            String input = sc.nextLine();
            try {
                hasta = FechaUtils.parseFecha(input);
                if (hasta.isBefore(desde)) {
                    System.out.println("La fecha 'hasta' no puede ser anterior a 'desde'. Intente de nuevo.");
                    hasta = null;
                }
            } catch (Exception e) {
                System.out.println("Fecha inválida, intente nuevamente.");
            }
        } while (hasta == null);

        for (LocalDate fecha = desde; !fecha.isAfter(hasta); fecha = fecha.plusDays(1)) {
            CalendarioMensual mensual = calendario.get(YearMonth.from(fecha));
            if (mensual != null) {
                for (Map.Entry<LocalDateTime, Turno> entry : mensual.getTurnos().entrySet()) {
                    if (entry.getKey().toLocalDate().equals(fecha)) {
                        entry.getValue().setEstado(Estado.OCUPADO);
                        entry.getValue().setPaciente(new Paciente("Reservado", 123));
                    }
                }
            }
        }
        System.out.println("✅ Turnos bloqueados como 'Reservado'.");
    }



    @Override
    public void inicializarCalendarioMedico(Medico medico) {
        Map<YearMonth, CalendarioMensual> calendarioMedico = new HashMap<>();
        LocalDate hoy = LocalDate.now();
        LocalDate fin = hoy.plusDays(30);

        for (LocalDate fecha = hoy; !fecha.isAfter(fin); fecha = fecha.plusDays(1)) {
            List<LocalTime> horarios = medico.getCalendario().get(fecha.getDayOfWeek());
            if (horarios == null) continue;

            for (LocalTime hora : horarios) {
                LocalDateTime fechaHora = LocalDateTime.of(fecha, hora);
                Turno turno = new Turno(
                        UUID.randomUUID(),
                        Estado.LIBRE,
                        null,
                        medico,
                        new RangoHorario(hora, hora.plusMinutes(30))
                );

                YearMonth mes = YearMonth.from(fecha);
                calendarioMedico.computeIfAbsent(mes, k -> new CalendarioMensual())
                        .agregarTurno(fechaHora, turno);
            }
        }

        calendarios.put(medico, calendarioMedico);
    }

    /* ======= metodos privados ======= */

    private Medico solicitarMedico(Scanner sc) {
        int opcion;
        do {
            System.out.println("¿Desea tomar turno por:");
            System.out.println("1. Especialidad");
            System.out.println("2. Nombre del médico");
            opcion = Integer.parseInt(sc.nextLine());
        }while(opcion != 1 && opcion != 2);


        if (opcion == 1) {
            System.out.println("-------Especialidades Disponibles------");
            medicoService.listarEspecialidades();

            System.out.print("Ingrese la especialidad deseada: ");
            String especialidad = sc.nextLine();

            System.out.println("Médicos disponibles:");
            List<Medico> disponibles = medicoService.listarMedicosPorEspecialidad(especialidad);
            for (Medico m : disponibles) {
                System.out.println("- " + m.getNombre());
            }
        }

        System.out.print("Ingrese el nombre del médico: ");
        String nombreMedico = sc.nextLine();
        return medicoService.buscarMedicoPorNombre(nombreMedico);
    }

    private Map<YearMonth, CalendarioMensual> obtenerOCrearCalendarioMedico(Medico medico) {
        Map<YearMonth, CalendarioMensual> calendario = calendarios.get(medico);
        if (calendario == null) {
            System.out.println("Este médico no tiene calendario generado. Generando ahora...");
            inicializarCalendarioMedico(medico);
            calendario = calendarios.get(medico);
        }
        return calendario;
    }

    private LocalDate solicitarFecha(Scanner sc) {
        System.out.print("Ingrese la fecha deseada (dd/MM/yyyy): ");
        String fechaInput = sc.nextLine();
        LocalDate fecha = FechaUtils.parseFecha(fechaInput);
        if (fecha == null) {
            System.out.println("Fecha inválida.");
        }
        return fecha;
    }

    private LocalTime solicitarHora(Scanner sc) {
        System.out.print("Ingrese el horario deseado (HH:mm): ");
        String horaInput = sc.nextLine();
        LocalTime hora = FechaUtils.parseHora(horaInput);
        if (hora == null) {
            System.out.println("Hora inválida.");
        }
        return hora;
    }

    private List<LocalDateTime> listarHorariosDisponibles(CalendarioMensual calMensual, LocalDate fecha) {
        List<LocalDateTime> disponibles = new ArrayList<>();
        for (Map.Entry<LocalDateTime, Turno> entry : calMensual.getTurnos().entrySet()) {
            if (entry.getKey().toLocalDate().equals(fecha) && entry.getValue().getEstado() == Estado.LIBRE) {
                disponibles.add(entry.getKey());
            }
        }
        return disponibles;
    }

    private void mostrarHorariosDisponibles(List<LocalDateTime> horarios) {
        System.out.println("Horarios disponibles:");
        for (LocalDateTime horario : horarios) {
            System.out.println(FechaUtils.formatoHora(horario.toLocalTime()));
        }
    }

    private Turno obtenerTurno(Map<YearMonth, CalendarioMensual> calendario, LocalDate fecha, LocalTime hora) {
        CalendarioMensual mensual = calendario.get(YearMonth.from(fecha));
        if (mensual == null) {
            System.out.println("No hay turnos para esa fecha.");
            return null;
        }
        LocalDateTime fechaHora = LocalDateTime.of(fecha, hora);
        Turno turno = mensual.getTurno(fechaHora);
        if (turno == null) {
            System.out.println("Turno no encontrado.");
            return null;
        }
        return turno;
    }
}
