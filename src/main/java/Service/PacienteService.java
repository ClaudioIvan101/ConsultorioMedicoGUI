package Service;

import Domain.Paciente;
import java.util.List;

public interface PacienteService {
     boolean registrarPaciente(String nombre, String dni);
    List<Paciente> listarPacientes();
    Paciente buscarPacientePorDni(String dni);
    boolean eliminarPaciente(String dni);
    boolean actualizarPaciente(String dni, String nuevoNombre);
}
