package MetodoDePago;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import MetodoDePago.Apis.TransferenciaBancariaApi;
import NotificadoresYDocumentos.Vouchers.Documentos.ComprobanteCBU;
import NotificadoresYDocumentos.Vouchers.Documentos.DocumentoRegistrable;
import NotificadoresYDocumentos.Vouchers.Documentos.RegistroDocumentos;

class TransferenciaBancariaTests {

    private TransferenciaBancariaApi apiMock;
    private TransferenciaBancaria inmediata;
    private TransferenciaBancaria programada;
    private Date fecha;

    @BeforeEach
    void setUp() {
        apiMock = mock(TransferenciaBancariaApi.class);
        fecha = new Date();
        inmediata = new TransferenciaBancaria(8000.0, apiMock, 555, "mi.alias");
        programada = new TransferenciaBancaria(8000.0, apiMock, 555, "mi.alias", fecha);
    }

    @Test
    void guardaElMontoYLaApi() {
        assertEquals(8000.0, inmediata.getMontoAPagar(), 0.001);
        assertSame(apiMock, inmediata.getApi());
    }

    @Test
    void elConstructorConFechaTambienGuardaMontoYApi() {
        assertEquals(8000.0, programada.getMontoAPagar(), 0.001);
        assertSame(apiMock, programada.getApi());
    }

    @Test
    void validarDatosEnviaCbuYAlias() {
        inmediata.validarDatos();

        verify(apiMock).validarDatos(555, "mi.alias");
    }

    @Test
    void reservarFondosNoHaceNada() {
        inmediata.reservarFondos();

        verifyNoInteractions(apiMock);
    }

    @Test
    void sinFechaLaTransferenciaEsInmediata() {
        inmediata.ejecutarTransaccion();

        verify(apiMock).transferenciaInmediata(8000.0);
        verify(apiMock, never()).trasnferenciaProgramada(anyDouble(), any());
    }

    @Test
    void conFechaLaTransferenciaEsProgramada() {
        programada.ejecutarTransaccion();

        verify(apiMock).trasnferenciaProgramada(8000.0, fecha);
        verify(apiMock, never()).transferenciaInmediata(anyDouble());
    }

    @Test
    void notificarResultadoGuardaUnComprobanteCBU() {
        RegistroDocumentos registroMock = mock(RegistroDocumentos.class);

        inmediata.notificarResultado(21, registroMock);

        ArgumentCaptor<DocumentoRegistrable> captor = ArgumentCaptor.forClass(DocumentoRegistrable.class);
        verify(registroMock, times(1)).guardarDocumento(captor.capture());
        ComprobanteCBU comprobante = assertInstanceOf(ComprobanteCBU.class, captor.getValue());
        assertEquals(21, comprobante.getID());
        assertEquals(8000.0, comprobante.getMonto(), 0.001);
        assertEquals(555, comprobante.getCbu());
    }

    @Test
    void procesarPagoInmediataNoUsaLaApiProgramada() {
        inmediata.procesarPago();

        verify(apiMock).validarDatos(555, "mi.alias");
        verify(apiMock).transferenciaInmediata(8000.0);
    }
}
