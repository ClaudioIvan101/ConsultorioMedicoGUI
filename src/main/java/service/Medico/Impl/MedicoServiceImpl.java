package service.Medico.Impl;

import domain.Medico;
import service.Medico.MedicoService;

import java.util.*;

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

        //cargarCalendario(nuevoMedico);

        medicos.add(nuevoMedico);
        System.out.println("✅ Médico creado exitosamente.");
        sc.close();
    }

    @Override
    public void actualizarMedico(String nombre) {
        Medico medico = buscarMedicoPorNombre(nombre);
        if (medico != null) {
            //crearMedico();
        }
    }

    @Override
    public void eliminarMedico(String nombre) {
        Medico medico = buscarMedicoPorNombre(nombre);
        if (medico != null) {
            medicos.remove(medico);
        }
    }

    @Override
    public Medico buscarMedicoPorNombre(String nombre) {
        try {
            for (Medico medico : medicos) {
                if (medico.getNombre().equalsIgnoreCase(nombre)) {
                    return medico;
                }
            }
            throw new NoSuchElementException("No se encontró un médico con el nombre: " + nombre);
        } catch (Exception e) {
            System.err.println("Error al buscar médico: " + e.getMessage());
            return null;
        }
    }


    @Override
    public List<Medico> listarMedicosPorEspecialidad(String especialidad) {
        try {
            List<Medico> resultado = new ArrayList<>();
            for (Medico medico : medicos) {
                if (medico.getEspecialidad().equalsIgnoreCase(especialidad)) {
                    resultado.add(medico);
                }
            }
            if (resultado.isEmpty()) {
                throw new NoSuchElementException("No se encontró una especialidad: " + especialidad);
            }
            return resultado;
        } catch (Exception e) {
            System.err.println("Error al buscar médicos por especialidad: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<Medico> listarMedicos() {
        return medicos;
    }
}
