package dominio.servicios;

import dominio.entidades.Instruccion;
import dominio.entidades.Posicion;
import io.vavr.collection.List;
import io.vavr.control.Either;

import java.util.stream.Stream;

import static dominio.servicios.ServidorInstruccion.*;
import static io.vavr.API.*;

public class ServidorRutas {

    private static Stream<Instruccion> hacerListaRuta(String ruta){
        return ruta
                .codePoints()
                .mapToObj(c -> Instruccion.valueOf(String.valueOf((char) c)));
    }

    public static Either<String, List<String>> validarCantidadRutas(Integer capacidadDron, List<String> rutas){
        return Either.right(rutas)
                .filter(l->l.size()<= capacidadDron)
                .getOrElse(Left("El Número de Rutas Excede la capacidad del drón"))
                .filter(l->l.size() > 0)
                .getOrElse(Left("No hay rutas que ejecutar"))
                .mapLeft(s->""+s);
    }


    public static Either<String, Posicion> validarPosicionPorMaxCuadras(Integer limite, Posicion posicion){
        return ( Math.abs(posicion.x) <= limite && Math.abs(posicion.y) <= limite )?Right(posicion):Left("La Posición " +
                "resultante:[" + ServidorPosicion.posicionToString(posicion)+"] está fuera del límite de cuadras");
    }


    public static String revertirRuta(String ruta){
        return List.ofAll(hacerListaRuta(new StringBuilder("II"+ruta+"II")
                .reverse()
                .toString()))
                .map(i->instruccionToString(cambiarInstruccion(i)))
                .fold("",(s, s2) -> s+s2 );
    }

    private static Instruccion cambiarInstruccion(Instruccion i){
        Instruccion cambio = i;
        switch(i){
            case A: break;
            case D: cambio = Instruccion.I;
                break;
            case I: cambio = Instruccion.D;
                break;
        }
        return cambio;
    }
}
