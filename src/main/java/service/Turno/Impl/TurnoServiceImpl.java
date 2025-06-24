package service.Turno.Impl;
import domain.CalendarioMedico;
import domain.Medico;
import domain.Paciente;
import domain.RangoHorario;
import domain.Turno;
import domain.enums.Estado;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import service.Medico.MedicoService;
import service.Paciente.PacienteService;
import service.Turno.TurnoService;

public class TurnoServiceImpl implements TurnoService{
    private CalendarioMedico calendarioMedico;
    private MedicoService medicoService;
    private PacienteService pacienteService;
    @Override
    public List<Turno> generarTurnosPara(LocalDate fecha, Medico medico,List<Paciente> pacientes) {
    if (!calendarioMedico.getDiasLaborales().contains(fecha.getDayOfWeek())) {
            System.out.println("El médico no trabaja este día");
        }
    List<Turno> turnosDelDia = calendarioMedico.getTurnosPorDia()
                .computeIfAbsent(fecha, k -> new ArrayList<>());
    List<Turno> nuevosTurnos = new ArrayList<>();
        int pacienteIndex = 0;
        
        for(RangoHorario franja: calendarioMedico.getFranjasHorarias()) {
            LocalTime horaActual = franja.getInicio();
            while(!horaActual.plusMinutes(30).isAfter(franja.getFin()) &&
                    pacienteIndex < pacientes.size()) {
                 Turno turno = new Turno(
                    fecha,
                    horaActual,
                    Estado.OCUPADO,
                    pacientes.get(pacienteIndex),
                    medico
                );
                 turnosDelDia.add(turno);
                 nuevosTurnos.add(turno);
                horaActual = horaActual.plusMinutes(30);
                pacienteIndex++;
            }
        }
        return nuevosTurnos;
    }
}

