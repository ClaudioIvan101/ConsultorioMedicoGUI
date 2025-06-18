package service.Medico;

import domain.Medico;

import java.util.List;

public interface MedicoService {
    void crearMedico();
    void actualizarMedico(String nombre);
    void eliminarMedico(String nombre);
    Medico buscarMedicoPorNombre(String nombre);
    List<Medico> listarMedicosPorEspecialidad(String especialidad);
    List<Medico> listarMedicos();
}
