package service.Menu.Impl;

import service.Medico.MedicoService;
import service.Menu.MenuService;
import service.Paciente.PacienteService;
import service.Turno.TurnoService;

import java.util.Scanner;

public class MenuServiceImpl implements MenuService {
    private PacienteService pacienteService;
    private TurnoService turnoService;
    private MedicoService medicoService;

    public MenuServiceImpl(PacienteService pacienteService, TurnoService turnoService, MedicoService medicoService) {
        this.pacienteService = pacienteService;
        this.turnoService = turnoService;
        this.medicoService = medicoService;
    }

    @Override
    public void mostrarMenu(Scanner scanner) {
        int opcion;

        do {
            System.out.println("\n========== CONSULTORIO MÉDICO ==========");
            System.out.println("Seleccione una opción:");

            System.out.println("\n--- GESTIÓN DE TURNOS ---");
            System.out.println("1. Solicitar turno");
            System.out.println("2. Consultar disponibilidad de un médico");
            System.out.println("3. Bloquear agenda por período (Reservado)");

            System.out.println("\n--- CONSULTAS ---");
            System.out.println("4. Ver médicos registrados");
            System.out.println("5. Ver pacientes registrados");

            System.out.println("\n--- GESTIÓN DE PACIENTES ---");
            System.out.println("6. Registrar nuevo paciente");
            System.out.println("7. Editar datos de paciente");
            System.out.println("8. Eliminar paciente");

            System.out.println("\n--- GESTIÓN DE MÉDICOS ---");
            System.out.println("9. Registrar nuevo médico");
            System.out.println("10. Editar datos de médico");
            System.out.println("11. Eliminar médico");

            System.out.println("\n12. Salir");

            System.out.print("\nOpción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    turnoService.crearTurno();
                    break;
                case 2:
                    System.out.println("Ingrese el nombre del medico: ");
                    String nombreMedico = scanner.next();
                    break;
                case 3:
                    turnoService.registrarTurnosOcupadosPorPeriodo();
                    break;
                case 4:
                    medicoService.listarMedicos();
                    break;
                case 5:
                    pacienteService.listarPacientes();
                    break;
                case 6:
                    pacienteService.registrarPaciente();
                    break;
                case 7:
                    pacienteService.actualizarPaciente();
                    break;
                case 8:
                    System.out.println("Ingrese el DNI del paciente a eliminar: ");
                    int dni = scanner.nextInt();
                    pacienteService.eliminarPaciente(dni);
                    break;
                case 9:
                    medicoService.crearMedico();
                    break;
                case 10:
                    System.out.println("Ingrese el Nombre del medico a editar: ");
                    String nombre = scanner.nextLine();
                    medicoService.actualizarMedico(nombre);
                    break;
                case 11:
                    System.out.println("Ingrese el Nombre del medico a eliminar: ");
                    String nombreMedic = scanner.nextLine();
                    medicoService.eliminarMedico(nombreMedic);
                    break;
                case 12:
                    break;
            }

        } while (opcion != 12);
    }
}
