package Principal;


import service.Paciente.PacienteService;
import domain.Paciente;
import java.util.List;
import java.util.Scanner;
import service.Medico.Impl.MedicoServiceImpl;
import service.Menu.Impl.MenuServiceImpl;
import service.Menu.MenuService;
import service.Paciente.Impl.PacienteServiceImpl;
import service.Medico.MedicoService;
public class Main {
    public static void main(String[] args) {
        // Crear una instancia del servicio de pacientes
        PacienteService pacienteService = new PacienteServiceImpl();
        // Listar pacientes inicialmente
        List<Paciente> pacientes = pacienteService.listarPacientes();
        // Registrar un nuevo paciente
        pacienteService.registrarPaciente("Claudio Gomez", "46463269");
        // Actualizar el nombre del paciente registrado
        pacienteService.actualizarPaciente("46463269", "Claudio Ivan Gomez");
        MenuService menu = new MenuServiceImpl();
        Scanner sc = new Scanner(System.in);
        menu.mostrarMenu(sc);
    }
}
