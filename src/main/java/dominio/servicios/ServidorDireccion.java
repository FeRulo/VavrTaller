package dominio.servicios;

import dominio.entidades.Direccion;

public class ServidorDireccion {
    public static String direccionToString(Direccion d){
        String resultado="";
        switch (d){
            case N: resultado="Norte";
            break;
            case S: resultado="Sur";
            break;
            case E: resultado="Este";
            break;
            case O: resultado="Oeste";
            break;
        }
        return resultado;
    }

}
