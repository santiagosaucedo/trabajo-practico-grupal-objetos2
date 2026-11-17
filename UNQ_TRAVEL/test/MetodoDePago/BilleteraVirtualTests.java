package MetodoDePago;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import MetodoDePago.Apis.BilleteraVirtualApi;
import NotificadoresYDocumentos.Vouchers.Documentos.RegistroDocumentos;

class BilleteraVirtualTests {

    private BilleteraVirtualApi apiMock;
    private BilleteraVirtual billetera;

    @BeforeEach
    void setUp() {
        apiMock = mock(BilleteraVirtualApi.class);
        billetera = new BilleteraVirtual(1000.0, 5000.0, apiMock);
    }

    @Test
    void guardaElMontoYLaApi() {
        assertEquals(1000.0, billetera.getMontoAPagar(), 0.001);
        assertSame(apiMock, billetera.getApi());
    }

    @Test
    void validarDatosEnviaSaldoYMonto() {
        billetera.validarDatos();

        verify(apiMock).validarDatos(5000.0, 1000.0);
    }

    @Test
    void reservarFondosBloqueaElSaldo() {
        billetera.reservarFondos();

        verify(apiMock).bloqueoDeSaldo();
    }

    @Test
    void ejecutarTransaccionAcreditaElMonto() {
        billetera.ejecutarTransaccion();

        verify(apiMock).acreditarTransferencia(1000.0);
    }

    @Test
    void notificarResultadoEnviaElPushYNoRegistraNada() {
        RegistroDocumentos registroMock = mock(RegistroDocumentos.class);

        billetera.notificarResultado(9, registroMock);

        verify(apiMock).notificarResultado(9);
        verifyNoInteractions(registroMock);
    }

    @Test
    void procesarPagoLlamaALaApiEnOrden() {
        billetera.procesarPago();

        InOrder orden = inOrder(apiMock);
        orden.verify(apiMock).validarDatos(5000.0, 1000.0);
        orden.verify(apiMock).bloqueoDeSaldo();
        orden.verify(apiMock).acreditarTransferencia(1000.0);
    }
}