package taller;


import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DronSuite {
    public class Posicion{

    }
    @Test
    public void probarDron(){
        Dron dron = new Dron();
        String reporte = servicioMover.moverDron(dron,"AADID");
        Dron.entregar();
        assertEquals(reporte, "(-2,4)" );

    }
}
