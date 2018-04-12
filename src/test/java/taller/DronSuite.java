package taller;

import dominio.entidades.Direccion;
import dominio.entidades.Dron;
import dominio.entidades.Posicion;
import io.vavr.collection.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import static dominio.servicios.DistribuidorAlmuerzos.*;
import dominio.entidades.*;

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



}
