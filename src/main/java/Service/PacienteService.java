package Service;

import domain.Paciente;
import java.util.List;
/**
 * La interfaz PacienteService define un contrato para la gestión de pacientes.
 * Proporciona métodos para registrar, listar, buscar, eliminar y actualizar la información de los pacientes.
 * Cualquier clase que implemente esta interfaz debe proporcionar la lógica específica para cada uno de estos métodos.
 */
public interface PacienteService {
    boolean registrarPaciente(String nombre, String dni);
    List<Paciente> listarPacientes();
    Paciente buscarPacientePorDni(String dni);
    boolean eliminarPaciente(String dni);
    boolean actualizarPaciente(String dni, String nuevoNombre);
}
