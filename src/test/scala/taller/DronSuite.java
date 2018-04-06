package taller;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import serviciosDominio.ServicioDron;
import sustantivos.*;
import static sustantivos.Direccion.*;
import java.util.List;

@RunWith(PowerMockRunner.class)
//@PrepareForTest(fullyQualifiedNames = "serviciosDominio.ServicioDron")
public class DronSuite {

    @Test
    public void probarIdeaNegocio0(){

        Dron dron = ServicioDron.moverDron(new Dron(),"AAIIADAAI");

        System.out.println(dron.toString());
        assertEquals("(-2,1,S)", "" + dron.toString());

    }

}
