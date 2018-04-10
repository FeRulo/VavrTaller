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
        assertEquals("(-1,3) dirección O", posicionFinal);
    }

    @Test
    public void entregarPedidoDesdePDronExistente(){
        String nameTest = "Prueba Idea Negocio 1";
        String pedido = "AAAAD";
        Dron dronPasado = new Dron(1,new Posicion(0,5,Direccion.O));//Dron en posición (0,5,O)
        String posicionFinal = ServicioEntregarAlmuerzos.entregarPedido(dronPasado,pedido);

        System.out.println("Test"+nameTest +" /Posición Final del dron: "+posicionFinal);
        assertEquals("(-4,5) dirección N", posicionFinal);
    }

    @Test
    public void entregarTresPedidos(){
        String[] pedidos = {"AAAAI","AAAAI","AAAAD"};
        assertEquals(ServicioEntregarAlmuerzos.reportarVariasEntregas(pedidos),
                "== Reporte de entregas ==\n" +
                "(0,4) Dirección O\n" +
                "(-4,4) Dirección S\n" +
                "(-4,0) Dirección O\n");
    }



}
