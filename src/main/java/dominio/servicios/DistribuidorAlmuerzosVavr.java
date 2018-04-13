package dominio.servicios;

import dominio.entidades.Direccion;
import dominio.entidades.Dron;
import dominio.entidades.Instruccion;
import dominio.entidades.Posicion;
import io.vavr.Tuple;
import io.vavr.collection.List;
import io.vavr.control.Either;
import static dominio.servicios.ValidadorRutas.*;
import static io.vavr.API.*;

import java.util.stream.Stream;

import static dominio.servicios.ServidorPosicion.posicionToString;

public class DistribuidorAlmuerzosVavr extends DistribuidorAlmuerzos{

    public static String reportarEntregasDron2(Dron dron, List<String> entregas){
        final String[] reporte = {"== Reporte de entregas ==\n"};
        generarListaPosicionesFinales(dron, entregas).forEach(posicion-> {
            reporte[0] += posicionToString(posicion) + "\n";
        });
        return reporte[0];
    };

    public static Either<String, List<Either<String,Posicion>>>  generarListaPosicionesFinales2(Dron dron, List<String> rutas){
        return validarCantidadRutas(3, rutas)
                .map(ls-> ls
                        .map(ruta -> validarPosicionPorMaxCuadras(10,enviarDron(dron, "DDDD").p)
                                .flatMap(p-> validarPosicionPorMaxCuadras(10,enviarDron(dron, ruta).p))
                        ));
    }
}
