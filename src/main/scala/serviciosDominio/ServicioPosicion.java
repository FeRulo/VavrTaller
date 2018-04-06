package serviciosDominio;

import sustantivos.Direccion;
import static sustantivos.Direccion.*;

import sustantivos.Instruccion;
import sustantivos.Posicion;

public class ServicioPosicion {

    @FunctionalInterface
    private interface InterfacePosicion{
        Posicion operar(int x, int y , Direccion d);
    }

    private static final InterfacePosicion
            giroDerechaDesdeNorte = (x,y,d)-> new Posicion(x, y, E);
    private static final InterfacePosicion
            giroDerechaDesdeSur = (x,y,d)-> new Posicion(x, y, O);
    private static final InterfacePosicion
            giroDerechaDesdeEste = (x,y,d)-> new Posicion(x, y, S);
    private static final InterfacePosicion
            giroDerechaDesdeOeste = (x,y,d)-> new Posicion(x, y, N);
    private static final InterfacePosicion
            giroIzquierdaDesdeNorte = (x,y,d)-> new Posicion(x, y, O);
    private static final InterfacePosicion
            giroIzquierdaDesdeSur = (x,y,d)-> new Posicion(x, y, E);
    private static final InterfacePosicion
            giroIzquierdaDesdeEste = (x,y,d)-> new Posicion(x, y, N);
    private static final InterfacePosicion
            giroIzquierdaDesdeOeste = (x,y,d)-> new Posicion(x, y, S);
    private static final InterfacePosicion
            adelanteMirandoNorte = (x,y,d)-> new Posicion(x, y+1, d);
    private static final InterfacePosicion
            adelanteMirandoSur = (x,y,d)-> new Posicion(x, y-1, d);
    private static final InterfacePosicion
            adelanteMirandoEste = (x,y,d)-> new Posicion(x+1, y, d);
    private static final InterfacePosicion
            adelanteMirandoOeste = (x,y,d)-> new Posicion(x-1, y, d);

    public static Posicion cambiarPosicion(Posicion p, Instruccion i){
        System.out.println("posicion actual:" + p.toString());
        Posicion resultado = new Posicion();
        switch (i){
            case A:
                switch (p.d) {
                    case N: resultado = ServicioPosicion.adelanteMirandoNorte.operar(p.x, p.y, p.d);
                    break;
                    case S: resultado = ServicioPosicion.adelanteMirandoSur.operar(p.x, p.y, p.d);
                    break;
                    case E: resultado = ServicioPosicion.adelanteMirandoEste.operar(p.x, p.y, p.d);
                    break;
                    case O: resultado = ServicioPosicion.adelanteMirandoOeste.operar(p.x, p.y, p.d);
                    break;
                }
                break;
            case D:
                switch (p.d) {
                    case N: resultado = ServicioPosicion.giroDerechaDesdeNorte.operar(p.x, p.y, p.d);
                    break;
                    case S: resultado = ServicioPosicion.giroDerechaDesdeSur.operar(p.x, p.y, p.d);
                    break;
                    case E: resultado = ServicioPosicion.giroDerechaDesdeEste.operar(p.x, p.y, p.d);
                    break;
                    case O: resultado = ServicioPosicion.giroDerechaDesdeOeste.operar(p.x, p.y, p.d);
                    break;
                }
                break;
            case I:
                switch (p.d) {
                    case N: resultado = ServicioPosicion.giroIzquierdaDesdeNorte.operar(p.x, p.y, p.d);
                    break;
                    case S: resultado = ServicioPosicion.giroIzquierdaDesdeSur.operar(p.x, p.y, p.d);
                    break;
                    case E: resultado = ServicioPosicion.giroIzquierdaDesdeEste.operar(p.x, p.y, p.d);
                    break;
                    case O: resultado = ServicioPosicion.giroIzquierdaDesdeOeste.operar(p.x, p.y, p.d);
                    break;
                }
                break;
        }
        return resultado;
    }
}
