package service.Medico.Impl;

import domain.Medico;
import service.Medico.MedicoService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class MedicoServiceImpl implements MedicoService {
    private List<Medico> medicos = new ArrayList<>();

    @Override
    public void crearMedico() {
        Medico nuevoMedico = new Medico();
        Scanner sc = new Scanner(System.in);

        UUID iD = UUID.randomUUID();
        nuevoMedico.setId(iD);

        System.out.println("Ingrese el nombre y apellido del Medico: ");
        String nombre = sc.nextLine();
        nuevoMedico.setNombre(nombre);

        System.out.println("Ingrese la especialidad del Medico: ");
        String especialidad = sc.nextLine();
        nuevoMedico.setEspecialidad(especialidad);

        //CargadorCalendario.cargarCalendario(nuevoMedico);

        medicos.add(nuevoMedico);
        System.out.println("✅ Médico creado exitosamente.");
    }

    @Override
    public void actualizarMedico() {

    }

    @Override
    public void eliminarMedico() {

    }

    @Override
    public Medico buscarMedicoPorNombre(String nombre) {
        return null;
    }

    @Override
    public Medico buscarMedicoPorEspecialidad(String especialidad) {
        return null;
    }

    @Override
    public List<Medico> listarMedicos() {
        return medicos;
    }
}
