package service.Turno;

import domain.Medico;
import domain.Paciente;
import domain.Turno;
import java.time.LocalDate;
import java.util.List;

public interface TurnoService {
     List<Turno> generarTurnosPara(LocalDate fecha,Medico medico,List<Paciente> pacientes);
}
