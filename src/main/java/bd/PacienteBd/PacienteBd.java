// src/bd/PacienteBd.java
package bd.PacienteBd;

import domain.Paciente;
import java.util.ArrayList;
import java.util.List;

public class PacienteBd {
    public static final List<Paciente> PACIENTES = new ArrayList<>();

    static {
        // Carga inicial de pacientes
        Paciente p1 = new Paciente();
        p1.setDni(33555123);
        p1.setNombre("María López");

        Paciente p2 = new Paciente();
        p2.setDni(22444123);
        p2.setNombre("Carlos Ruiz");

        PACIENTES.add(p1);
        PACIENTES.add(p2);
    }
}
