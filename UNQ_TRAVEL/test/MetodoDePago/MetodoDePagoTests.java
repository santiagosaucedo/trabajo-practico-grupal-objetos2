package MetodoDePago;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import NotificadoresYDocumentos.Vouchers.Documentos.CodigoTransaccion;
import NotificadoresYDocumentos.Vouchers.Documentos.DocumentoRegistrable;
import NotificadoresYDocumentos.Vouchers.Documentos.RegistroDocumentos;

class MetodoDePagoTests {

    private static class MetodoDePagoDePrueba extends MetodoDePago {
        final List<String> llamados = new ArrayList<>();

        MetodoDePagoDePrueba(double monto) { super(monto); }

        @Override protected void validarDatos() { llamados.add("validar"); }
        @Override protected void reservarFondos() { llamados.add("reservar"); }
        @Override protected void ejecutarTransaccion() { llamados.add("ejecutar"); }
    }

    private MetodoDePagoDePrueba metodo;

    @BeforeEach
    void setUp() {
        metodo = new MetodoDePagoDePrueba(2500.0);
    }

    @Test
    void guardaElMontoAPagar() {
        assertEquals(2500.0, metodo.getMontoAPagar(), 0.001);
    }

    @Test
    void procesarPagoEjecutaLosTresPasosEnOrden() {
        metodo.procesarPago();

        assertEquals(List.of("validar", "reservar", "ejecutar"), metodo.llamados);
    }

    @Test
    void procesarPagoEjecutaCadaPasoUnaSolaVez() {
        metodo.procesarPago();

        assertEquals(3, metodo.llamados.size());
    }

    @Test
    void notificarResultadoPorDefectoGuardaUnCodigoDeTransaccion() {
        RegistroDocumentos registroMock = mock(RegistroDocumentos.class);

        metodo.notificarResultado(42, registroMock);

        ArgumentCaptor<DocumentoRegistrable> captor = ArgumentCaptor.forClass(DocumentoRegistrable.class);
        verify(registroMock, times(1)).guardarDocumento(captor.capture());
        assertInstanceOf(CodigoTransaccion.class, captor.getValue());
    }

    @Test
    void elCodigoGuardadoTieneElIdYElMontoDelPago() {
        RegistroDocumentos registroMock = mock(RegistroDocumentos.class);

        metodo.notificarResultado(42, registroMock);

        ArgumentCaptor<DocumentoRegistrable> captor = ArgumentCaptor.forClass(DocumentoRegistrable.class);
        verify(registroMock).guardarDocumento(captor.capture());
        assertEquals(42, captor.getValue().getID());
        assertEquals(2500.0, captor.getValue().getMonto(), 0.001);
    }

    @Test
    void conUnRegistroRealElCodigoSePuedeRecuperarPorId() {
        RegistroDocumentos registroReal = new RegistroDocumentos();

        metodo.notificarResultado(7, registroReal);

        assertEquals(2500.0, registroReal.getDocumento(7).getMonto(), 0.001);
    }
}