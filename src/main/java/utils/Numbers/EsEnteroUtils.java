package utils.Numbers;

public class EsEnteroUtils {
    public static boolean esEntero(String cadena){
        boolean resultado;
        try {
            Integer.parseInt(cadena);
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;
        }
        return resultado;
    }

}
