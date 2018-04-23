package dominio.servicios;

import dominio.entidades.Direccion;
import dominio.entidades.Instruccion;

public class ServidorInstruccion {
    public static String instruccionToString(Instruccion i){
        String resultado="";
        switch (i){
            case A: resultado="A";
            break;
            case I: resultado="I";
            break;
            case D: resultado="D";
            break;
        }
        return resultado;
    }
}
