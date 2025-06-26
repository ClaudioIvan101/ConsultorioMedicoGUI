package service.Paciente;

import domain.Paciente;
import java.util.List;

public interface PacienteService {
    Paciente registrarPaciente();
    void listarPacientes();
    Paciente buscarPacientePorDni(int dni);
    Paciente buscarPacienteByNombre(String nombre);
    int getDNIPacienteByNombre(String nombre);
    void eliminarPaciente(int dni);
    Paciente actualizarPaciente();
}