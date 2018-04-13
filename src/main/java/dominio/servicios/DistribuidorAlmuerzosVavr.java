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

    public static String reportarEntregasDron2(Dron dron, List<String> pedidos){
        final String[] reporte = {"== Reporte de entregas ==\n"};

        generarListaPosicionesFinales2(dron, pedidos)
                .map(listaEithers-> listaEithers//Lista de eithers
                        .map(ePosicion ->
                                ePosicion
                                .map(posicion -> {
                                            reporte[0] += posicionToString(posicion) + "\n";
                                            return posicionToString(posicion);
                                        }
                                )
                                .mapLeft(error -> {
                                            reporte[0] += "El drón no se ha movido por: \n\t-"+ error + "\n";
                                            return error;
                                        }
                                )
                        )
                )
                .mapLeft(error -> {
                            reporte[0] += error + "\n";
                            return error;
                        }
                );
        return reporte[0];
    };

    public static Either<String, List<Either<String,Posicion>>>  generarListaPosicionesFinales2(Dron dron, List<String> rutas){
        return validarCantidadRutas(3, rutas)
                .map(ls-> ls
                        .map(ruta -> validarPosicionPorMaxCuadras(10,enviarDron(dron,"DDDD").p)//machetazo
                                .flatMap(p-> validarPosicionPorMaxCuadras(10,enviarDron(dron, ruta).p))
                                .mapLeft(error->error+"")
                        )
                        .distinct()
                );
    }
}
