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
public class Taller1Suite {

    @Test
    public void entregarPedidoDesde0(){
        String nameTest = "Prueba Idea Negocio 0";
        String pedido = "AAAIA";
        String posicionFinal = entregarPedido(pedido);

        assertEquals("(-1,3) Dirección Oeste", posicionFinal);
    }

    @Test
    public void entregarPedidoDesdePDronExistente(){
        String nameTest = "Prueba Idea Negocio 1";
        String pedido = "AAAAD";
        Dron dronPasado = new Dron(1,new Posicion(0,5,Direccion.O),3);//Dron en posición (0,5,O)
        String posicionFinal = entregarPedido(dronPasado,pedido);

        assertEquals("(-4,5) Dirección Norte", posicionFinal);
    }

    @Test
    public void entregarTresPedidos(){
        List<String> pedidos = List.of("AAAAI","AAAAI","AAAAI","AAAA");
        Dron dron = new Dron(0, new Posicion(0,0,Direccion.N),3);
        assertEquals(reportarEntregasDron(dron,pedidos),
                "== Reporte de entregas ==\n" +
                "(0,4) Dirección Oeste\n" +
                "(-4,4) Dirección Sur\n" +
                "(-4,0) Dirección Este\n"+
                "(0,0) Dirección Este\n");
    }


}
