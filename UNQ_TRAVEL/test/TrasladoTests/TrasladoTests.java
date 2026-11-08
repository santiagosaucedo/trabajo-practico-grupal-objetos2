package TrasladoTests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

import Direccion.Direccion;
import Traslado.Estandar;
import Traslado.PuntoDeEncuentro;
import Traslado.Traslado;
import Traslado.Vip;
import Traslado.Plataforma.PlataformaDeTransporte;

class TrasladoTests {

    @Test
    void cualquierTrasladoRespondeAlMismoContrato() {
        PlataformaDeTransporte plataformaMock = mock(PlataformaDeTransporte.class);
        Direccion direccionMock = mock(Direccion.class);
        when(plataformaMock.estimarTraslado(1, direccionMock)).thenReturn((double) 800f);

        Traslado estandar = new Estandar(1, direccionMock, plataformaMock);
        Traslado vip = new Vip(500, 0.20);
        Traslado punto = new PuntoDeEncuentro(2);

        assertEquals(800.0, estandar.calcularCosto(1000), 0.001);
        assertEquals(700.0, vip.calcularCosto(1000), 0.001);
        assertEquals(0.0, punto.calcularCosto(1000), 0.001);
    }
}