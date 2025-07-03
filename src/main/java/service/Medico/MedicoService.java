package service.Medico;

import domain.CalendarioMensual;
import domain.Medico;

import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public interface MedicoService {
    void crearMedico();
    Medico actualizarMedico();
    void eliminarMedico();
    Medico buscarMedicoPorNombre(String nombre);
    List<Medico> listarMedicosPorEspecialidad(String especialidad);
    void listarMedicos();
    void listarEspecialidades();
    void consultarDisponibilidad();
    Medico getMedicoById();
    Medico solicitarMedico(Scanner sc);
    Map<YearMonth, CalendarioMensual>  obtenerFechasDisponibles(Medico medico);
    void generarTurnosParaMes(Medico medico, YearMonth mes);
   }
