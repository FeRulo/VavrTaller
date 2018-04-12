import static dominio.servicios.DistribuidorAlmuerzos.reportarVariasEntregas;
import static dominio.servicios.ServidorArchivos.*;
import static org.junit.Assert.assertEquals;

import io.vavr.collection.List;
import io.vavr.control.Try;

import java.io.*;

public class Main {
    public static void main(String [] arg) {
        importarInstrucciones("src/main/resources/rutas.txt")
                .flatMap(instrucciones -> Try.of(()->reportarVariasEntregas(instrucciones))
                .flatMap(reporte ->Try.of(()->exportarReporte(reporte,"src/main/resources/reporte.txt"))));
    }
}

