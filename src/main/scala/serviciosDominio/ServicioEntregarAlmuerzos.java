package serviciosDominio;

import sustantivos.Dron;
import sustantivos.Instruccion;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ServicioEntregarAlmuerzos {
    public static String entregarPedido(String instrucciones){
        Dron d = instrucciones
                .codePoints()
                .mapToObj(c -> Instruccion.valueOf(String.valueOf((char) c)))
                .collect(new DronCollector());
        return "("+d.p.x+","+d.p.y+") dirección "+d.p.d;
    }

    public static String entregarPedido(Dron dron0, String instrucciones){
        dron0 = instrucciones
                .codePoints()
                .mapToObj(c -> Instruccion.valueOf(String.valueOf((char) c)))
                .collect(new DronCollector(dron0));
        return "("+dron0.p.x+","+dron0.p.y+") dirección "+dron0.p.d;
    };
}
