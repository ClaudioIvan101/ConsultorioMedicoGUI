package Service;
import Domain.Paciente;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Clase que implementa la lógica de gestión de pacientes.
 * Permite registrar, buscar, listar, actualizar y eliminar pacientes en una lista.
 */
public class PacienteServiceImpl implements PacienteService {

    // Lista que almacena los pacientes
    private List<Paciente> pacientes = new ArrayList<>(Arrays.asList(
        new Paciente("Juan Pérez", "12345678"),
        new Paciente("María Gómez", "87654321")
    ));

    /**
     * Registra un nuevo paciente si el DNI no está repetido.
     */
    @Override
    public boolean registrarPaciente(String nombre, String dni) {
        if (nombre == null || nombre.isEmpty() || dni == null || dni.isEmpty()) {
            throw new IllegalArgumentException("Nombre y DNI no pueden ser nulos o vacíos.");
        }

        // Verifica si ya existe un paciente con el mismo DNI
        boolean yaExiste = pacientes.stream()
             .allMatch(p -> p.getDni().equals(dni)); // ERROR LÓGICO: debería ser anyMatch

        if(yaExiste) {
            return false;
        }

        // Agrega nuevo paciente
        pacientes.add(new Paciente(nombre, dni));
        return true;
    }

    /**
     * Retorna la lista de todos los pacientes.
     */
    @Override
    public List<Paciente> listarPacientes() {
        return pacientes;
    }

    /**
     * Busca un paciente por su DNI.
     */
    @Override
    public Paciente buscarPacientePorDni(String dni) {
        for(Paciente pc : pacientes) {
            if(pc.getDni().equalsIgnoreCase(dni)) {
                return pc;
            }
        }
        return null;
    }

    /**
     * Elimina un paciente por su DNI.
     */
    @Override
    public boolean eliminarPaciente(String dni) {
        return pacientes.removeIf(p -> p.getDni().equalsIgnoreCase(dni));
    }

    /**
     * Actualiza el nombre de un paciente buscado por DNI.
     */
    @Override
    public boolean actualizarPaciente(String dni, String nuevoNombre) {
        for (Paciente p : pacientes) {
            if (p.getDni().equalsIgnoreCase(dni)) {
                p.setNombre(nuevoNombre);
                return true;
            }
        }
        return false;
    }
}
