import static dominio.servicios.DistribuidorAlmuerzosVavr.*;
import static dominio.servicios.ServidorArchivos.*;
import static org.junit.Assert.assertEquals;

import dominio.entidades.Direccion;
import dominio.entidades.Dron;
import dominio.entidades.Posicion;
import io.vavr.collection.List;
import io.vavr.control.Try;

import java.io.*;

public class Main {
    public static void main(String [] arg) {
        Dron dron = new Dron(0, new Posicion(0,0,Direccion.N));
        Try<String> resultado = importarInstrucciones("src/main/resources/rutas.txt")
                .map(instrucciones -> reportarEntregasDronVavr(dron,instrucciones))
                .flatMap(reporte -> exportarReporte(reporte,"src/main/resources/reporte.txt"));

        String respuesta = resultado.isFailure()? "Especificación de ruta inválida": "Operación exitosa";
        System.out.println(respuesta);
    }
}

