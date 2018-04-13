package taller;

import dominio.entidades.Direccion;
import dominio.entidades.Dron;
import dominio.entidades.Posicion;
import dominio.servicios.DistribuidorAlmuerzos;
import dominio.servicios.DistribuidorAlmuerzosVavr;
import io.vavr.collection.List;
import io.vavr.control.Either;
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
//@RunWith(PowerMockRunner.class)
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
        Dron dron = new Dron(0, new Posicion(0,0,Direccion.N));
        assertEquals(reportarVariasEntregas(dron,pedidos),
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
        Dron dron = new Dron(0, new Posicion(0,0,Direccion.N));
        Try<String> reporte = instrucciones.flatMap(l->Try.of(()->reportarVariasEntregas(dron,l)));
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
        Dron dron = new Dron(0, new Posicion(0,0,Direccion.N));
        Try<String> resultado = importarInstrucciones("src/main/resources/rutas.txt")
                .flatMap(instrucciones -> Try.of(()->reportarVariasEntregas(dron, instrucciones))
                        .flatMap(reporte ->exportarReporte(reporte,"src/main/resources/reporte.txt")
                        ));
        String respuesta = (resultado.isFailure())? "Especificación de ruta inválida": "Operación exitosa";
        assertEquals("Operación exitosa",respuesta);
    }

    @Test(expected = FileNotFoundException.class)
    public void leerArchivoReportarYArchivarError(){
        Dron dron = new Dron(0, new Posicion(0,0,Direccion.N));
        Try<String> resultado = importarInstrucciones("src/main/resources/ruta.txt")
                .flatMap(instrucciones -> Try.of(()->reportarVariasEntregas(dron, instrucciones))
                .flatMap(reporte ->exportarReporte(reporte,"src/main/resources/reporte.txt")
                ));
        resultado.get();
    }

    @Test
    public void validacionFallidaEntregarTresPedidos(){

        List<String> pedidos = List.of("AAAAI","AAAAI","AAAAI","AAAAI");
        Either<String, List<Either<String,Posicion>>> reporte = DistribuidorAlmuerzosVavr.reportarVariasEntregas2(pedidos);
        System.out.println(reporte.getLeft());
        assertTrue(reporte.isLeft());
    }

    @Test
    public void validacionEntregarTresPedidos(){
        List<String> pedidos = List.of("AAAAAAAAAAAAAAAI","AAAAD","AAAAAAAAI");
        Either<String, List<Either<String,Posicion>>> reporte = DistribuidorAlmuerzosVavr.reportarVariasEntregas2(pedidos);
        System.out.println(reporte.get());
        assertTrue(reporte.isRight());
    }

}
