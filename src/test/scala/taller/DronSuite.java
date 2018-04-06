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
    public void probarIdeaNegocio0(){
        String nameTest = "Prueba Idea Negocio 0";
        String posicionFinal = ServicioEntregarAlmuerzos.entregarPedido("AAIIADAAI");

        System.out.println("Test"+nameTest +" /Posición Final del dron: "+posicionFinal);
        assertEquals("(-2,1,S)", posicionFinal);
    }

}
