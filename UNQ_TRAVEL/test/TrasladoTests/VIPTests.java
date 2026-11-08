package TrasladoTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Traslado.Vip;

class VIPTests {

    @Test
    void calculaCostoBaseMasPorcentajeDeLaReserva() {
        Vip traslado = new Vip(1000, 0.10);

        assertEquals(2000.0, traslado.calcularCosto(10000), 0.001);
    }

    @Test
    void conReservaEnCeroCobraSoloElCostoBase() {
        Vip traslado = new Vip(1000, 0.10);

        assertEquals(1000.0, traslado.calcularCosto(0), 0.001);
    }

    @Test
    void estaDisponiblePorDefecto() {
        Vip traslado = new Vip(1000, 0.10);

        assertTrue(traslado.disponibilidad());
    }
}
