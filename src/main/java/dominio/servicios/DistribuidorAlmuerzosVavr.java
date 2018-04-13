package dominio.servicios;

import dominio.entidades.Direccion;
import dominio.entidades.Dron;
import dominio.entidades.Instruccion;
import dominio.entidades.Posicion;
import io.vavr.collection.List;
import io.vavr.control.Either;
import static dominio.servicios.ValidadorRutas.*;
import java.util.stream.Stream;

import static dominio.servicios.ServidorPosicion.posicionToString;

public class DistribuidorAlmuerzosVavr extends DistribuidorAlmuerzos{
    public static Either<String, List<Either<String,Posicion>>>  reportarVariasEntregas2(List<String> rutas){
        final String reporte = "== Reporte de entregas ==\n";
        final Dron[] dron = {new Dron(0, new Posicion(0, 0, Direccion.N))};
        return validarCantidadRutas(3, rutas)
                .map(ls-> ls
                .map(ruta->{
                        dron[0] = enviarDron(dron[0], ruta);
                        return validarPosicionPorMaxCuadras(10,dron[0].p);
                    }));
    }
}
