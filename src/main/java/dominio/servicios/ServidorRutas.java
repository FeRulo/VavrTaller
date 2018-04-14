package dominio.servicios;

import dominio.entidades.Posicion;
import io.vavr.collection.List;
import io.vavr.control.Either;
import static io.vavr.API.*;

public class ServidorRutas {

    public static Either<String, List<String>> validarCantidadRutas(Integer capacidadDron, List<String> rutas){
        return Either.right(rutas)
                .filter(l->l.size()<= capacidadDron)
                .getOrElse(Left("El Número de Rutas Excede la capacidad del drón"))
                .filter(l->l.size() > 0)
                .getOrElse(Left("No hay rutas que ejecutar"))
                .mapLeft(s->""+s);
    }


    public static Either<String, Posicion> validarPosicionPorMaxCuadras(Integer limite, Posicion posicion){
        return ( Math.abs(posicion.x) <= limite && Math.abs(posicion.y) <= limite )?Right(posicion):Left("Posición " +
                "resultante:[" + ServidorPosicion.posicionToString(posicion)+"] afuera del límite de cuadras");
    }

    public static String obtenerRutaReciproca(){

    }
}
