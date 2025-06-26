import domain.Turno;
import service.Medico.Impl.MedicoServiceImpl;
import service.Medico.MedicoService;
import service.Menu.Impl.MenuServiceImpl;
import service.Menu.MenuService;
import service.Paciente.Impl.PacienteServiceImpl;
import service.Paciente.PacienteService;
import service.Turno.Impl.TurnoServiceImpl;
import service.Turno.TurnoService;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        PacienteService pacienteService = new PacienteServiceImpl();
        MedicoService medicoService = new MedicoServiceImpl(); // sin parámetro
        TurnoService turnoService = new TurnoServiceImpl(medicoService, pacienteService);
        ((MedicoServiceImpl) medicoService).setTurnoService(turnoService);

        MenuService menuService = new MenuServiceImpl(pacienteService, turnoService, medicoService);


        Scanner scanner = new Scanner(System.in);
        menuService.mostrarMenu(scanner);
        scanner.close();
    }
}
