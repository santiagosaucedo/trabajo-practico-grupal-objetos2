package TrasladoTests;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Traslado.PuntoDeEncuentro;

class PuntoDeEncuentroTests {

    @Test
    void siempreCuestaCero() {
        PuntoDeEncuentro traslado = new PuntoDeEncuentro(5);

        assertEquals(0.0, traslado.calcularCosto(50000), 0.001);
        assertEquals(0.0, traslado.calcularCosto(0), 0.001);
    }

    @Test
    void disponibleSiHayGuias() {
        PuntoDeEncuentro traslado = new PuntoDeEncuentro(1);

        assertTrue(traslado.disponibilidad());
    }

    @Test
    void noDisponibleSinGuias() {
        PuntoDeEncuentro traslado = new PuntoDeEncuentro(0);

        assertFalse(traslado.disponibilidad());
    }
}
