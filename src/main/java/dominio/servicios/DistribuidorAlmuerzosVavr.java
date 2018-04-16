package dominio.servicios;

import dominio.entidades.*;
import io.vavr.collection.List;
import io.vavr.control.Either;
import static dominio.servicios.ServidorRutas.*;

import static dominio.servicios.ServidorPosicion.posicionToString;

public class DistribuidorAlmuerzosVavr extends DistribuidorAlmuerzos{

    public static String reportarEntregasDronVavr(Dron dron, List<String> pedidos){
        return "== Reporte de entregas ==\n"+generarListaPosicionesFinalesVavr(dron, pedidos)
                .map(listaEithers-> listaEithers//Lista de eithers
                        .map(ePosicion ->
                                ePosicion
                                .fold(error ->error + "\n"
                                        ,posicion -> posicionToString(posicion) + "\n"
                                )
                        ) //lista de strings
                        .fold("",(s, s2) -> s + s2)
                )
                .fold(  error -> error + "\n",
                        reporte -> reporte
                );
    };

    public static Either<String, List<Either<String,Posicion>>>  generarListaPosicionesFinalesVavr(Dron dron, List<String> rutas){
        return validarCantidadRutas(dron.capacidad, rutas)
                .map(ls-> {
                    return ls
                            .map(ruta -> validarPosicionPorMaxCuadras(Limites.radio, dron.p)
                                    .flatMap(pos -> validarPosicionPorMaxCuadras(Limites.radio, enviarDron(dron, ruta).p)
                                            .mapLeft(error -> {
                                                return "El dron se ha devuelto a la posición "+
                                                        posicionToString(enviarDron(dron, revertirRuta(ruta)).p)+ " porque:\n\t-"+ error;
                                            })
                                    )
                            )
                            .distinct();
                });
    }
}
