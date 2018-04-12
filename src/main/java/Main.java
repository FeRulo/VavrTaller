import static dominio.servicios.DistribuidorAlmuerzos.reportarVariasEntregas;
import static dominio.servicios.ServidorArchivos.*;
import static org.junit.Assert.assertEquals;

import io.vavr.collection.List;
import io.vavr.control.Try;

import java.io.*;

public class Main {
    public static void main(String [] arg) {
        Try<String> resultado = importarInstrucciones("src/main/resources/rutas.txt")
                .map(instrucciones -> reportarVariasEntregas(instrucciones))
                .flatMap(reporte -> exportarReporte(reporte,"src/main/resources/reporte.txt"));
        String respuesta = resultado.isFailure()? "Especificación de ruta inválida": "Operación exitosa";
        System.out.println(respuesta);
    }
}

