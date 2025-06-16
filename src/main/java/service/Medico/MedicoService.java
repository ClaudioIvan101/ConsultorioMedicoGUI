package service.Medico;

import domain.Medico;

import java.util.List;

public interface MedicoService {
    void crearMedico();
    void actualizarMedico();
    void eliminarMedico();
    Medico buscarMedicoPorNombre(String nombre);
    Medico buscarMedicoPorEspecialidad(String especialidad);
    List<Medico> listarMedicos();
}
