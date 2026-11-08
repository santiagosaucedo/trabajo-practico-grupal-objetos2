package TrasladoTests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Direccion.Direccion;
import Traslado.Estandar;
import Traslado.Plataforma.PlataformaDeTransporte;

class EstandarTests {

    private PlataformaDeTransporte plataformaMock;
    private Direccion direccionMock;

    @BeforeEach
    void setUp() {
        plataformaMock = mock(PlataformaDeTransporte.class);
        direccionMock = mock(Direccion.class);
    }

    @Test
    void delegaElCalculoEnLaPlataforma() {
        when(plataformaMock.estimarTraslado(3, direccionMock)).thenReturn((double) 5000f);
        Estandar traslado = new Estandar(3, direccionMock, plataformaMock);

        double costo = traslado.calcularCosto(100000);

        assertEquals(5000.0, costo, 0.001);
        verify(plataformaMock, times(1)).estimarTraslado(3, direccionMock);
    }

    @Test
    void ignoraElCostoDeLaReserva() {
        when(plataformaMock.estimarTraslado(2, direccionMock)).thenReturn((double) 1200f);
        Estandar traslado = new Estandar(2, direccionMock, plataformaMock);

        double conReservaChica = traslado.calcularCosto(10);
        double conReservaGrande = traslado.calcularCosto(999999);

        assertEquals(conReservaChica, conReservaGrande, 0.001);
        verify(plataformaMock, times(2)).estimarTraslado(2, direccionMock);
    }

    @Test
    void estaDisponiblePorDefecto() {
        Estandar traslado = new Estandar(1, direccionMock, plataformaMock);

        assertTrue(traslado.disponibilidad());
        verifyNoInteractions(plataformaMock);
    }
}