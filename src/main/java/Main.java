import static dominio.servicios.DistribuidorAlmuerzos.reportarVariasEntregas;
import static dominio.servicios.ServidorArchivos.*;
import static org.junit.Assert.assertEquals;

import io.vavr.collection.List;

import java.io.*;

public class Main {
    public static void main(String [] arg) {
        try{
            List<String> instrucciones = importarInstrucciones("src/main/resources/rutas.txt");
            String reporte = reportarVariasEntregas(instrucciones);
            exportarReporte(reporte,"src/main/resources/reporte.txt");
        }catch (IOException e){
            System.out.println("Error: " + e.getMessage());
        }
    }
}

