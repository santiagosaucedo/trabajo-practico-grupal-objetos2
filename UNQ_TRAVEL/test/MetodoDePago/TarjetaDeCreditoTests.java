package MetodoDePago;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;

import MetodoDePago.Apis.TarjetaDeCreditoApi;
import NotificadoresYDocumentos.Vouchers.Documentos.DocumentoRegistrable;
import NotificadoresYDocumentos.Vouchers.Documentos.RegistroDocumentos;
import NotificadoresYDocumentos.Vouchers.Documentos.VoucherDeCredito;

class TarjetaDeCreditoTests {

    private TarjetaDeCreditoApi apiMock;
    private Date vencimiento;
    private TarjetaDeCredito tarjeta;

    @BeforeEach
    void setUp() {
        apiMock = mock(TarjetaDeCreditoApi.class);
        vencimiento = new Date();
        tarjeta = new TarjetaDeCredito(3000.0, 123456, 321, vencimiento, apiMock);
    }

    @Test
    void guardaElMontoYLaApi() {
        assertEquals(3000.0, tarjeta.getMontoAPagar(), 0.001);
        assertSame(apiMock, tarjeta.getApi());
    }

    @Test
    void validarDatosEnviaCvvNumeroYVencimiento() {
        tarjeta.validarDatos();

        verify(apiMock).validarDatos(321, 123456, vencimiento);
    }

    @Test
    void reservarFondosPreAutorizaElMonto() {
        tarjeta.reservarFondos();

        verify(apiMock).preAutorizarTransferencia(3000.0);
    }

    @Test
    void ejecutarTransaccionTransfiereElMonto() {
        tarjeta.ejecutarTransaccion();

        verify(apiMock).transferirMonto(3000.0);
    }

    @Test
    void notificarResultadoGuardaUnVoucherConIdYMonto() {
        RegistroDocumentos registroMock = mock(RegistroDocumentos.class);

        tarjeta.notificarResultado(15, registroMock);

        ArgumentCaptor<DocumentoRegistrable> captor = ArgumentCaptor.forClass(DocumentoRegistrable.class);
        verify(registroMock, times(1)).guardarDocumento(captor.capture());
        assertInstanceOf(VoucherDeCredito.class, captor.getValue());
        assertEquals(15, captor.getValue().getID());
        assertEquals(3000.0, captor.getValue().getMonto(), 0.001);
    }

    @Test
    void procesarPagoLlamaALaApiEnOrden() {
        tarjeta.procesarPago();

        InOrder orden = inOrder(apiMock);
        orden.verify(apiMock).validarDatos(321, 123456, vencimiento);
        orden.verify(apiMock).preAutorizarTransferencia(3000.0);
        orden.verify(apiMock).transferirMonto(3000.0);
    }
}