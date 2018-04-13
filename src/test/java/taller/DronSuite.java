package taller;

import dominio.entidades.Direccion;
import dominio.entidades.Dron;
import dominio.entidades.Posicion;
import dominio.servicios.DistribuidorAlmuerzos;
import static dominio.servicios.DistribuidorAlmuerzosVavr.*;
import io.vavr.collection.List;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import static dominio.servicios.ServidorArchivos.exportarReporte;
import static dominio.servicios.ServidorArchivos.importarInstrucciones;

import static dominio.servicios.ServidorPosicion.posicionToString;

import static dominio.entidades.Posicion.*;
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
        assertEquals(reportarEntregasDron(dron,pedidos),
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
        Try<String> reporte = instrucciones.flatMap(l->Try.of(()->reportarEntregasDron(dron,l)));
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
                .flatMap(instrucciones -> Try.of(()->reportarEntregasDron(dron, instrucciones))
                        .flatMap(reporte ->exportarReporte(reporte,"src/main/resources/reporte.txt")
                        ));
        String respuesta = (resultado.isFailure())? "Especificación de ruta inválida": "Operación exitosa";
        assertEquals("Operación exitosa",respuesta);
    }

    @Test(expected = FileNotFoundException.class)
    public void leerArchivoReportarYArchivarError(){
        Dron dron = new Dron(0, new Posicion(0,0,Direccion.N));
        Try<String> resultado = importarInstrucciones("src/main/resources/ruta.txt")
                .flatMap(instrucciones -> Try.of(()->reportarEntregasDron(dron, instrucciones))
                .flatMap(reporte ->exportarReporte(reporte,"src/main/resources/reporte.txt")
                ));
        resultado.get();
    }

    @Test
    public void validacionFallidaEntregarTresPedidos(){
        List<String> pedidos = List.of("AAAAI","AAAAI","AAAAI","AAAAI");
        Dron dron = new Dron(0, new Posicion(0,0,Direccion.N));
        Either<String, List<Either<String,Posicion>>> reporte = generarListaPosicionesFinales2(dron, pedidos);
        String retornoConsola= reporte.isLeft()? reporte.getLeft(): "";
        System.out.println(retornoConsola);
        assertEquals("El Número de Rutas Excede la capacidad del drón", retornoConsola);
    }

    @Test
    public void validacionEntregarTresPedidos(){
        List<String> pedidos = List.of("AAAAI","AAAAD","AAAAI");
        Dron dron = new Dron(0, new Posicion(0,0,Direccion.N));
        Either<String, List<Either<String,Posicion>>> listaPosiciones = generarListaPosicionesFinales2(dron, pedidos);
        String retorno = "";
        if(listaPosiciones.isLeft()){
            retorno = listaPosiciones.getLeft();
        }
        else{
            final String[] reporte = {"== Reporte de entregas ==\n"};
            List<Either<String,String>> l = listaPosiciones
                    .get()//Lista de eithers
                    .map(ePosicion -> ePosicion
                            .map(posicion -> {
                                        reporte[0] += posicionToString(posicion) + "\n";
                                        return posicionToString(posicion);
                                }
                            )
                    );
            retorno = reporte[0];
        }
        assertEquals("== Reporte de entregas ==\n" +
                "(0,4) Dirección Oeste\n" +
                "(-4,4) Dirección Norte\n" +
                "(-4,8) Dirección Oeste\n" ,retorno);
    }
    @Test
    public void validacionEntregarTresPedidosSaliendoseLímites(){
        List<String> pedidos = List.of("AAAAI","AAAAD","AAAAAAAAI");
        Dron dron = new Dron(0, new Posicion(0,0,Direccion.N));
        Either<String, List<Either<String,Posicion>>> listaPosiciones = generarListaPosicionesFinales2(dron, pedidos);
        String retorno = "";
        if(listaPosiciones.isLeft()){
            retorno = listaPosiciones.getLeft();
        }
        else{
            final String[] reporte = {"== Reporte de entregas ==\n"};
            List<Either<String,String>> l = listaPosiciones
                    .get()//Lista de eithers
                    .map(ePosicion -> ePosicion
                            .map(posicion -> {
                                        reporte[0] += posicionToString(posicion) + "\n";
                                        return posicionToString(posicion);
                                    }
                            )
                            .mapLeft(error -> {
                                        reporte[0] += error + "\n";
                                        return error;
                                    }
                            )
                    );
            retorno = reporte[0];
        }
        System.out.println(retorno);
    }

    @Test
    public void validacionEntregarTresPedidos2(){
        List<String> pedidos = List.of("AAAAAAAAAAAAAAAI","AAAAD","AAAAAAAAI");
        Dron dron = new Dron(0, new Posicion(0,0,Direccion.N));
        Either<String, List<Either<String,Posicion>>> reporte = generarListaPosicionesFinales2(dron, pedidos);
        System.out.println(reporte.get());
        assertTrue(reporte.isRight());
    }

}
