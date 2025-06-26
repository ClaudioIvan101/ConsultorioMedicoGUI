package domain;

public class Paciente {
    private String nombre;
    private int dni;

    public Paciente(String nombre, int dni) {
        this.nombre = nombre;
        this.dni = dni;
    }

    public Paciente() {

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }
}
