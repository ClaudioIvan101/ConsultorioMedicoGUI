package service.Paciente.Impl;

import domain.Paciente;

import java.util.*;

import service.Paciente.PacienteService;

import static utils.Numbers.EsEnteroUtils.esEntero;

public class PacienteServiceImpl implements PacienteService {

    // Lista que almacena los pacientes
    private List<Paciente> pacientes = new ArrayList<>(Arrays.asList(
            new Paciente("Juan Pérez", 12345678),
            new Paciente("María Gómez", 87654321)
    ));


    @Override
    public Paciente registrarPaciente() {
        Scanner sc = new Scanner(System.in);
        Paciente nuevoPaciente = new Paciente();

        boolean valido;
        int dniPaciente = 0;
        do {
            System.out.println("Ingrese el DNI del paciente");
            try {
                dniPaciente = sc.nextInt();
                sc.nextLine(); // consume el '\n'
                valido = true;
            } catch (InputMismatchException e) {
                sc.nextLine(); // limpia el buffer
                valido = false;
                System.out.println("Error, ingrese nuevamente el DNI del paciente");
            }
        } while (!valido);

        Paciente pacienteRegistrado = buscarPacientePorDni(dniPaciente);
        if (pacienteRegistrado != null) {
            System.out.println("Paciente registrado bajo el nombre: " + pacienteRegistrado.getNombre());
            return pacienteRegistrado;
        }

        nuevoPaciente.setDni(dniPaciente);

        System.out.println("Ingrese el nombre del paciente: ");
        String nombrePaciente = sc.nextLine(); // ahora sí funciona bien
        nuevoPaciente.setNombre(nombrePaciente);

        pacientes.add(nuevoPaciente);
        System.out.println("Paciente registrado con éxito. Nombre: " + nuevoPaciente.getNombre() + " DNI: " + nuevoPaciente.getDni());

        return nuevoPaciente;
    }



    @Override
    public void listarPacientes() {
        for (Paciente p : pacientes) {
            System.out.println("Paciente: " + p.getNombre());
            System.out.println("DNI: " + p.getDni());
        }
    }


    @Override
    public Paciente buscarPacientePorDni(int dni) {
        for(Paciente buscado : pacientes) {
            if(buscado.getDni() == dni) {
                System.out.println(buscado);
            }
        }
        return null;
    }

    @Override
    public Paciente buscarPacienteByNombre(String nombre) {
        for(Paciente buscado : pacientes) {
            if(buscado.getNombre().equalsIgnoreCase(nombre)) {
                return buscado;
            }else {
                System.out.println("No existe un paciente con el nombre: " + nombre);
            }
        }
        return null;
    }

    @Override
    public int getDNIPacienteByNombre(String nombre) {
        try{
            Paciente paciente = buscarPacienteByNombre(nombre);
            int dniPaciente = paciente.getDni();
            return dniPaciente;
        }catch(NullPointerException e) {
            System.out.println("No existe un paciente con el nombre: " + nombre);
        }
        return 0;
    }


    @Override
    public void eliminarPaciente(int dni) {
        for(Paciente buscado : pacientes) {
            if(buscado.getDni() == dni) {
                pacientes.remove(buscado);
            }
        }
    }

    /**
     * Actualiza el nombre de un paciente buscado por DNI.
     */
    @Override
    public Paciente actualizarPaciente() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Ingrese el DNI del paciente a actualizar: ");
        int dni = sc.nextInt();
        Paciente pacienteActualizado = buscarPacientePorDni(dni);

        if(pacienteActualizado == null) {
            System.out.println("Paciente no encontrado, el DNI ingresado es "+ dni + "\n" +
                    "1- Reingresar DNI \n 2-Registrar Paciente \n 3- Salir");
            int opcion = sc.nextInt();
            do {
                switch(opcion) {
                    case 1:
                        System.out.println("Ingrese el DNI del paciente a actualizar: ");
                        int dniPaciente = sc.nextInt();
                        Paciente busqueda = buscarPacientePorDni(dniPaciente);
                        sc.close();
                    case 2:
                        System.out.println("Ingrese el nombre del paciente: ");
                        String nombrePaciente = sc.next();
                        return registrarPaciente();
                    case 3:
                        return null;
                }
            }while (opcion > 0 || opcion != 3);
        }
        return pacienteActualizado;
    }
}