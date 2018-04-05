package ejemplos;

import org.junit.Test;

import static ejemplos.EnumSuite.Juego.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class EnumSuite {

    public enum Juego {
        ROCA() {

            @Override
            public boolean ganaContra(Juego other) {
                return other == TIJERAS;
            }
        },
        PAPEL, TIJERAS;


        public boolean ganaContra(Juego other) {
            return ordinal() - other.ordinal() == 1;
        }
    }

    @Test
    public void papelContraRoca() {
        assertTrue(PAPEL.ganaContra(ROCA)==true);
        assertTrue(ROCA.ganaContra(PAPEL)==false);
    }

    @Test
    public void tijerasContraPapel() {
        assertTrue(TIJERAS.ganaContra(PAPEL)==true);
        assertTrue(PAPEL.ganaContra(TIJERAS)==false);
    }

    @Test
    public void rocaContraTijeras() {
        assertTrue(ROCA.ganaContra(TIJERAS)==true);
        assertTrue(TIJERAS.ganaContra(ROCA)==false);
    }


}