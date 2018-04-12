package serviciosDominio;

import static sustantivos.Direccion.*;
import sustantivos.Dron;
import sustantivos.Instruccion;
import sustantivos.Posicion;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ServicioEntregarAlmuerzos {

    private static Stream<Instruccion> hacerListaInstrucciones(String instrucciones){
        return instrucciones
                .codePoints()
                .mapToObj(c -> Instruccion.valueOf(String.valueOf((char) c)));
    }

    private static Dron enviarDronDesde0(String instrucciones){
        return ServicioEntregarAlmuerzos.hacerListaInstrucciones(instrucciones)
                .collect(new DronCollector());
    }

    private static Dron enviarDron(Dron dron, String instrucciones){
        return ServicioEntregarAlmuerzos.hacerListaInstrucciones(instrucciones)
                .collect(new DronCollector(dron));
    }

    public static String entregarPedido(String instrucciones){
        Dron d = ServicioEntregarAlmuerzos.enviarDronDesde0(instrucciones);
        return "("+d.p.x+","+d.p.y+") Dirección "+d.p.d;
    }

    public static String entregarPedido(Dron dron0, String instrucciones){
        dron0 = ServicioEntregarAlmuerzos.hacerListaInstrucciones(instrucciones)
                .collect(new DronCollector(dron0));
        return "("+dron0.p.x+","+dron0.p.y+") Dirección "+dron0.p.d;
    };

    public static String reportarVariasEntregas(String[] entregas){
        final String[] reporte = {"== Reporte de entregas ==\n"};
        final Dron[] dron = {new Dron(0, new Posicion(0, 0, N))};
        Arrays.stream(entregas)
        .forEach(instruccion-> {
            dron[0] = enviarDron(dron[0], instruccion);
            reporte[0] += "("+ dron[0].p.x+","+ dron[0].p.y+") Dirección "+ dron[0].p.d + "\n";
        });
        return reporte[0];
    };
}
