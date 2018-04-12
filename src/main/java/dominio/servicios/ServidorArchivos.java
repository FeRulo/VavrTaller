package dominio.servicios;

import io.vavr.collection.List;
import io.vavr.control.Try;

import java.io.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ServidorArchivos {
    public static Try<List<String>> importarInstrucciones(String ruta){
        File archivo = new File(ruta);
        return Try.of(()->new FileReader(archivo))
                .flatMap(f->Try.of(()->new BufferedReader(f))
                .flatMap(br->Try.of(()->List.ofAll(br.lines()))));
    }

    public static String exportarReporte(String reporte, String ruta) throws IOException{
        File archivo = new File(ruta);
        BufferedWriter bw;
        if (archivo.exists()) {
            bw = new BufferedWriter(new FileWriter(archivo));
            bw.write(reporte);
        } else {
            bw = new BufferedWriter(
                    new FileWriter(archivo));
            bw.write(reporte);
        }
        bw.close();
        return "hola";
    }

}
