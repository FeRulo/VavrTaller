package dominio.servicios;

import io.vavr.collection.List;
import io.vavr.control.Try;

import java.io.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ServidorArchivos {

    public static Try<List<String>> importarInstrucciones(String rutaArchivo){
        return Try.of(()->new FileReader(new File(rutaArchivo)))
                .flatMap(fr->Try.of(()->new BufferedReader(fr))
                .flatMap(br->Try.of(()->List.ofAll(br.lines()))));
    }


    public static Try<String> exportarReporte(String reporte, String rutaArchivo) {
        return Try.of(()->new File(rutaArchivo))
                .flatMap(f->Try.of(()->new FileWriter(f))
                .flatMap(fw ->Try.of(()->new BufferedWriter(fw))
                .flatMap(bw ->Try.of(()->{
                    bw.write(reporte);
                    bw.close();
                    return "Escritura exitosa";
                }))));
    }

}
