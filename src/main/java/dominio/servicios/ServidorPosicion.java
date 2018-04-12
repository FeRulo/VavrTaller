package dominio.servicios;

import dominio.entidades.Direccion;
import dominio.entidades.Instruccion;
import dominio.entidades.Posicion;

public class ServidorPosicion {

    @FunctionalInterface
    private interface InterfacePosicion{
        Posicion operar(int x, int y , Direccion d);
    }

    private static final InterfacePosicion
            giroDerechaDesdeNorte = (x,y,d)-> new Posicion(x, y, Direccion.E);

    private static final InterfacePosicion
            giroDerechaDesdeSur = (x,y,d)-> new Posicion(x, y, Direccion.O);

    private static final InterfacePosicion
            giroDerechaDesdeEste = (x,y,d)-> new Posicion(x, y, Direccion.S);

    private static final InterfacePosicion
            giroDerechaDesdeOeste = (x,y,d)-> new Posicion(x, y, Direccion.N);

    private static final InterfacePosicion
            giroIzquierdaDesdeNorte = (x,y,d)-> new Posicion(x, y, Direccion.O);

    private static final InterfacePosicion
            giroIzquierdaDesdeSur = (x,y,d)-> new Posicion(x, y, Direccion.E);

    private static final InterfacePosicion
            giroIzquierdaDesdeEste = (x,y,d)-> new Posicion(x, y, Direccion.N);

    private static final InterfacePosicion
            giroIzquierdaDesdeOeste = (x,y,d)-> new Posicion(x, y, Direccion.S);

    private static final InterfacePosicion
            adelanteMirandoNorte = (x,y,d)-> new Posicion(x, y+1, d);

    private static final InterfacePosicion
            adelanteMirandoSur = (x,y,d)-> new Posicion(x, y-1, d);

    private static final InterfacePosicion
            adelanteMirandoEste = (x,y,d)-> new Posicion(x+1, y, d);

    private static final InterfacePosicion
            adelanteMirandoOeste = (x,y,d)-> new Posicion(x-1, y, d);

    private static Posicion cambiarPosicion(int x, int y, Direccion d, InterfacePosicion operacion){
        return operacion.operar(x,y,d);
    }

    public static Posicion cambiarPosicion(Posicion p, Instruccion i){
        Posicion resultado = new Posicion();
        switch (i){
            case A:
                switch (p.d) {
                    case N: resultado = cambiarPosicion(p.x, p.y, p.d,adelanteMirandoNorte);
                    break;
                    case S: resultado = cambiarPosicion(p.x, p.y, p.d,adelanteMirandoSur);
                    break;
                    case E: resultado = cambiarPosicion(p.x, p.y, p.d,adelanteMirandoEste);
                    break;
                    case O: resultado = cambiarPosicion(p.x, p.y, p.d,adelanteMirandoOeste);
                    break;
                }
                break;
            case D:
                switch (p.d) {
                    case N: resultado = cambiarPosicion(p.x, p.y, p.d,giroDerechaDesdeNorte);
                    break;
                    case S: resultado = cambiarPosicion(p.x, p.y, p.d,giroDerechaDesdeSur);
                    break;
                    case E: resultado = cambiarPosicion(p.x, p.y, p.d,giroDerechaDesdeEste);
                    break;
                    case O: resultado = cambiarPosicion(p.x, p.y, p.d,giroDerechaDesdeOeste);
                    break;
                }
                break;
            case I:
                switch (p.d) {
                    case N: resultado = cambiarPosicion(p.x, p.y, p.d,giroIzquierdaDesdeNorte);
                    break;
                    case S: resultado = cambiarPosicion(p.x, p.y, p.d,giroIzquierdaDesdeSur);
                    break;
                    case E: resultado = cambiarPosicion(p.x, p.y, p.d,giroIzquierdaDesdeEste);
                    break;
                    case O: resultado = cambiarPosicion(p.x, p.y, p.d,giroIzquierdaDesdeOeste);
                    break;
                }
                break;
        }
        return resultado;
    }

    public static String posicionToString(Posicion p){
        return "("+p.x+","+p.y+") Dirección "+ ServidorDireccion.direccionToString(p.d);
    }
}
