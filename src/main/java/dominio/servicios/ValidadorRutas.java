package dominio.servicios;

import dominio.entidades.Posicion;
import io.vavr.collection.List;
import io.vavr.control.Either;
import static io.vavr.API.*;

public class ValidadorRutas {

    public static Either<String, List<String>> validarCantidadRutas(Integer capacidadDron, List<String> rutas){
        return (rutas.size() <= capacidadDron && rutas.size() > 0)?Right(rutas):Left("El Número " +
                "de Rutas Excede la capacidad del drón");
    }

    public static Either<String, Posicion> validarPosicionPorMaxCuadras(Integer limite, Posicion posicion){
        return ( Math.abs(posicion.x) > limite && Math.abs(posicion.y) > limite )?Right(posicion):Left("Posición " +
                "resultante " + ServidorPosicion.posicionToString(posicion)+" fuera de los límites");
    }
}
