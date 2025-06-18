package Principal;

import Domain.Paciente;
import Service.PacienteService;
import Service.PacienteServiceImpl;
import java.util.List;

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
        // Mostrar la lista de pacientes después de registrar y actualizar
        System.out.println("Lista de pacientes:");
        for (Paciente p : pacientes) {
            System.out.println("Paciente: " + p.getNombre());
            System.out.println("DNI: " + p.getDni());
        }
        // Buscar un paciente por su DNI
        String DNI = "46463269"; 
        Paciente pacienteEncontrado = pacienteService.buscarPacientePorDni(DNI);
        if (pacienteEncontrado != null) {
            // Si se encuentra el paciente, mostrar su nombre
            System.out.println("Paciente encontrado: " + pacienteEncontrado.getNombre());
        } else {
            // Si no se encuentra, mostrar un mensaje
            System.out.println("Paciente con DNI " + DNI + " no encontrado.");
        }
        // Intentar eliminar un paciente por su DNI
        String dniAEliminar = "12345678"; 
        boolean eliminado = pacienteService.eliminarPaciente(dniAEliminar);
        if (eliminado) {
            // Si la eliminación fue exitosa, mostrar un mensaje
            System.out.println("Paciente con DNI " + dniAEliminar + " eliminado.");
        } else {
            // Si no se pudo eliminar, mostrar un mensaje
            System.out.println("No se pudo eliminar el paciente con DNI " + dniAEliminar + ".");
        }
        // Listar nuevamente los pacientes para verificar que se eliminó correctamente
        System.out.println("Lista de pacientes:");
        for (Paciente p : pacientes) {
            System.out.println("Paciente: " + p.getNombre());
            System.out.println("DNI: " + p.getDni());
        }
    }
}
