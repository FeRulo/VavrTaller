package taller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import serviciosDominio.ServicioEntregarAlmuerzos;
import sustantivos.*;

@RunWith(PowerMockRunner.class)
//@PrepareForTest(fullyQualifiedNames = "serviciosDominio.ServicioEntregarAlmuerzos")
public class DronSuite {

    @Test
    public void entregarPedidoDesde0(){
        String nameTest = "Prueba Idea Negocio 0";
        String pedido = "AAAIA";
        String posicionFinal = ServicioEntregarAlmuerzos.entregarPedido(pedido);

        System.out.println("Test"+nameTest +" /Posición Final del dron: "+posicionFinal);
        assertEquals("(-1,3) Dirección Oeste", posicionFinal);
    }

    @Test
    public void entregarPedidoDesdePDronExistente(){
        String nameTest = "Prueba Idea Negocio 1";
        String pedido = "AAAAD";
        Dron dronPasado = new Dron(1,new Posicion(0,5,Direccion.O));//Dron en posición (0,5,O)
        String posicionFinal = ServicioEntregarAlmuerzos.entregarPedido(dronPasado,pedido);

        System.out.println("Test"+nameTest +" /Posición Final del dron: "+posicionFinal);
        assertEquals("(-4,5) Dirección Norte", posicionFinal);
    }

    @Test
    public void entregarTresPedidos(){
        String[] pedidos = {"AAAAI","AAAAI","AAAAI","AAAA"};
        System.out.println(ServicioEntregarAlmuerzos.reportarVariasEntregas(pedidos));
        assertEquals(ServicioEntregarAlmuerzos.reportarVariasEntregas(pedidos),
                "== Reporte de entregas ==\n" +
                "(0,4) Dirección Oeste\n" +
                "(-4,4) Dirección Sur\n" +
                "(-4,0) Dirección Este\n"+
                "(0,0) Dirección Este\n");
    }



}
