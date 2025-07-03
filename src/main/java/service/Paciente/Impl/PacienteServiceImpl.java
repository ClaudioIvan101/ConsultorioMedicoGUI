package service.Paciente.Impl;

import domain.Paciente;

import java.util.*;

import service.Paciente.PacienteService;

import static bd.PacienteBd.PacienteBd.PACIENTES;

public class PacienteServiceImpl implements PacienteService {

    // Lista que almacena los pacientes
    private List<Paciente> pacientes = PACIENTES;


    @Override
    public Paciente registrarPaciente() {
        Scanner sc = new Scanner(System.in);
        Paciente nuevoPaciente = new Paciente();

        int dniPaciente = leerDniValido(sc);

        Paciente pacienteRegistrado = buscarPacientePorDni(dniPaciente);
        if (pacienteRegistrado == null) {
            nuevoPaciente.setDni(dniPaciente);

            System.out.println("Ingrese el nombre del paciente: ");
            String nombrePaciente = sc.nextLine(); // ahora sí funciona bien
            nuevoPaciente.setNombre(nombrePaciente);

            pacientes.add(nuevoPaciente);
            System.out.println("Paciente registrado con éxito. Nombre: " + nuevoPaciente.getNombre() + " DNI: " + nuevoPaciente.getDni());

            return nuevoPaciente;
        }else {
            System.out.println("Paciente con DNI: " + dniPaciente + " se encuentra registrado bajo el nombre: " + pacienteRegistrado.getNombre());
            return pacienteRegistrado;
        }
    }

    @Override
    public void listarPacientes() {
        System.out.println("---LISTA DE PACIENTES---");
        for (Paciente p : pacientes) {
            System.out.println("Paciente: " + p.getNombre());
            System.out.println("DNI: " + p.getDni());
            System.out.println("\n");
        }
    }

    @Override
    public Paciente buscarPacientePorDni(int dni) {
        for(Paciente buscado : pacientes) {
            if(buscado.getDni() == dni) {
                return buscado;
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
    public void eliminarPaciente() {
        Scanner sc = new Scanner(System.in);
        int opcion = 0;

        int dniPaciente = leerDniValido(sc);
        Paciente pacienteEliminar = buscarPacientePorDni(dniPaciente);

        do {
            System.out.println("¿Está seguro que desea eliminar al Paciente: " + pacienteEliminar.getNombre()
                    +"\n 1. Eliminar"
                    +"\n 2. No eliminar");
            opcion = sc.nextInt();

            switch(opcion) {
                case 1:
                    pacientes.remove(pacienteEliminar);
                    System.out.println("Paciente eliminado con éxito");
                    return;
                case 2:
                    return;
                default:
                    System.out.println("Opcion no valida");
            }
        }while (true);
    }

    @Override
    public Paciente actualizarPaciente() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            int dniPaciente = leerDniValido(sc);
            Paciente paciente = buscarPacientePorDni(dniPaciente);

            if (paciente != null) {
                System.out.println("Paciente encontrado bajo el nombre: " + paciente.getNombre());

                System.out.println("Ingrese su nombre y apellido actualizado:");
                String nuevoNombre = sc.nextLine();
                paciente.setNombre(nuevoNombre);

                System.out.println("Paciente actualizado con éxito."
                        + "\n Nombre: " + paciente.getNombre()
                        + "\n DNI: " + paciente.getDni());
                return paciente;
            }

            System.out.println("Paciente no encontrado. 1-Reintentar 2-Registrar 3-Salir");
            int opcion = sc.nextInt();
            switch (opcion) {
                case 1: continue;
                case 2: return registrarPaciente();
                case 3: return null;
                default: System.out.println("Opción inválida");
            }
        }
    }

    private int leerDniValido(Scanner sc) {
        boolean valido;
        int dni = 0;
        do {
            System.out.println("Ingrese el DNI del paciente:");
            try {
                dni = sc.nextInt();
                sc.nextLine(); // limpiar '\n'
                valido = true;
            } catch (InputMismatchException e) {
                sc.nextLine(); // limpiar buffer
                System.out.println("Error: Ingrese un número válido.");
                valido = false;
            }
        } while (!valido);
        return dni;
    }

}