package taller;

import dominio.entidades.Direccion;
import dominio.entidades.Dron;
import dominio.entidades.Posicion;
import io.vavr.collection.List;
import io.vavr.control.Try;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import static dominio.servicios.ServidorArchivos.exportarReporte;
import static dominio.servicios.ServidorArchivos.importarInstrucciones;
import static io.vavr.API.Success;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import static dominio.servicios.DistribuidorAlmuerzos.*;
import dominio.entidades.*;

import java.io.FileNotFoundException;
@RunWith(PowerMockRunner.class)
//@PrepareForTest(fullyQualifiedNames = "DistribuidorAlmuerzos")
public class DronSuite {

    @Test
    public void entregarPedidoDesde0(){
        String nameTest = "Prueba Idea Negocio 0";
        String pedido = "AAAIA";
        String posicionFinal = entregarPedido(pedido);

        System.out.println("Test"+nameTest +" /Posición Final del dron: "+posicionFinal);
        assertEquals("(-1,3) Dirección Oeste", posicionFinal);
    }

    @Test
    public void entregarPedidoDesdePDronExistente(){
        String nameTest = "Prueba Idea Negocio 1";
        String pedido = "AAAAD";
        Dron dronPasado = new Dron(1,new Posicion(0,5,Direccion.O));//Dron en posición (0,5,O)
        String posicionFinal = entregarPedido(dronPasado,pedido);

        System.out.println("Test"+nameTest +" /Posición Final del dron: "+posicionFinal);
        assertEquals("(-4,5) Dirección Norte", posicionFinal);
    }

    @Test
    public void entregarTresPedidos(){
        List<String> pedidos = List.of("AAAAI","AAAAI","AAAAI","AAAA");
        System.out.println(reportarVariasEntregas(pedidos));
        assertEquals(reportarVariasEntregas(pedidos),
                "== Reporte de entregas ==\n" +
                "(0,4) Dirección Oeste\n" +
                "(-4,4) Dirección Sur\n" +
                "(-4,0) Dirección Este\n"+
                "(0,0) Dirección Este\n");
    }

    @Test
    public void leerArchivo(){
        Try<List<String>> instrucciones = importarInstrucciones("src/main/resources/rutas.txt");
        assertEquals(instrucciones,
                Success(List.of("AAAAAAAAAI",
                "AIDAAID",
                "AAAIDDDDD",
                "AAADA",
                "AAAAIADAA")));
    }

    @Test
    public void leerArchivoYReportar(){
        Try<List<String>> instrucciones = importarInstrucciones("src/main/resources/rutas.txt");
        Try<String> reporte = instrucciones.flatMap(l->Try.of(()->reportarVariasEntregas(l)));
        System.out.println(reporte);
        assertEquals(reporte, Success("== Reporte de entregas ==\n" +
                "(0,9) Dirección Oeste\n" +
                "(-3,9) Dirección Oeste\n" +
                "(-6,9) Dirección Oeste\n" +
                "(-9,10) Dirección Norte\n" +
                "(-10,16) Dirección Norte\n"
                ));

    }

    @Test
    public void leerArchivoReportarYArchivar(){
        Try<String> resultado = importarInstrucciones("src/main/resources/rutas.txt")
                .flatMap(instrucciones -> Try.of(()->reportarVariasEntregas(instrucciones))
                        .flatMap(reporte ->exportarReporte(reporte,"src/main/resources/reporte.txt")
                        ));
        resultado.get();
        String respuesta = (resultado.isFailure())? "Especificación de ruta inválida": "Operación exitosa";
        assertEquals("Operación exitosa",respuesta);
    }

    @Test(expected = FileNotFoundException.class)
    public void leerArchivoReportarYArchivarError(){
        Try<String> resultado = importarInstrucciones("src/main/resources/ruta.txt")
                .flatMap(instrucciones -> Try.of(()->reportarVariasEntregas(instrucciones))
                .flatMap(reporte ->exportarReporte(reporte,"src/main/resources/reporte.txt")
                ));
        resultado.get();
    }



}
