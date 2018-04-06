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
        return "("+d.p.x+","+d.p.y+","+d.p.d+")";
    }

    public static Dron moverDron(Dron dron, Instruccion instrunccion){
        return null;
    };

    public static String extra(){
        return "hola";
    }
}
