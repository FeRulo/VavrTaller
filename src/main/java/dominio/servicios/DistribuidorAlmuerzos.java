package dominio.servicios;

import static dominio.servicios.ServidorPosicion.posicionToString;

import dominio.entidades.Direccion;
import dominio.entidades.Dron;
import dominio.entidades.Instruccion;
import dominio.entidades.Posicion;
import io.vavr.collection.List;
import io.vavr.control.Either;


import java.util.Arrays;
import java.util.stream.Stream;

public class DistribuidorAlmuerzos {

    private static Stream<Instruccion> hacerListaInstrucciones(String instrucciones){
        return instrucciones
                .codePoints()
                .mapToObj(c -> Instruccion.valueOf(String.valueOf((char) c)));
    }

    private static Dron enviarDronDesde0(String instrucciones){
        return DistribuidorAlmuerzos.hacerListaInstrucciones(instrucciones)
                .collect(new DronCollector());
    }

    protected static Dron enviarDron(Dron dron, String instrucciones){
        return DistribuidorAlmuerzos.hacerListaInstrucciones(instrucciones)
                .collect(new DronCollector(dron));
    }

    public static String entregarPedido(String instrucciones){
        Dron d = DistribuidorAlmuerzos.enviarDronDesde0(instrucciones);
        return posicionToString(d.p);
    }

    public static String entregarPedido(Dron dron0, String instrucciones){
        dron0 = DistribuidorAlmuerzos.hacerListaInstrucciones(instrucciones)
                .collect(new DronCollector(dron0));
        return posicionToString(dron0.p);
    };

    public static String reportarVariasEntregas(List<String> entregas){
        final String[] reporte = {"== Reporte de entregas ==\n"};
        final Dron[] dron = {new Dron(0, new Posicion(0, 0, Direccion.N))};
        entregas.forEach(instruccion-> {
            dron[0] = enviarDron(dron[0], instruccion);
            reporte[0] += posicionToString(dron[0].p) + "\n";
        });
        return reporte[0];
    };
}
