package service.Turno;

import domain.Medico;

public interface TurnoService {
    void crearTurno();
    void inicializarCalendarioMedico(Medico medico);
    void editarTurno();
    void eliminarTurno();
    void registrarTurnosOcupadosPorPeriodo();
}
