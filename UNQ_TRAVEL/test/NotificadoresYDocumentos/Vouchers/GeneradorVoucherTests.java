package NotificadoresYDocumentos.Vouchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import NotificadoresYDocumentos.Vouchers.Documentos.ComprobanteFiscal;
import NotificadoresYDocumentos.Vouchers.Documentos.DocumentoRegistrable;
import NotificadoresYDocumentos.Vouchers.Documentos.RegistroDocumentos;
import NotificadoresYDocumentos.Vouchers.Documentos.VoucherDeCredito;
import Reservas.Reserva;

class GeneradorVoucherTests {

    private RegistroDocumentos registroMock;
    private Reserva reservaMock;
    private GeneradorVoucher generador;

    @BeforeEach
    void setUp() {
        registroMock = mock(RegistroDocumentos.class);
        reservaMock = mock(Reserva.class);
        when(reservaMock.calcularCostoDeServicios()).thenReturn(15000.0);

        generador = new GeneradorVoucher(registroMock);
    }

    private List<DocumentoRegistrable> documentosGuardados(int cantidadEsperada) {
        ArgumentCaptor<DocumentoRegistrable> captor = ArgumentCaptor.forClass(DocumentoRegistrable.class);
        verify(registroMock, times(cantidadEsperada)).guardarDocumento(captor.capture());
        return captor.getAllValues();
    }

    private ComprobanteFiscal comprobanteGuardado(int cantidadEsperada) {
        return (ComprobanteFiscal) documentosGuardados(cantidadEsperada).stream()
                .filter(d -> d instanceof ComprobanteFiscal)
                .findFirst()
                .get();
    }

    private VoucherDeCredito voucherGuardado(int cantidadEsperada) {
        return (VoucherDeCredito) documentosGuardados(cantidadEsperada).stream()
                .filter(d -> d instanceof VoucherDeCredito)
                .findFirst()
                .get();
    }

    // ---------- reservaFinalizada ----------

    @Test
    void alFinalizarGuardaUnComprobanteFiscalYUnVoucher() {
        generador.reservaFinalizada(reservaMock);

        List<DocumentoRegistrable> guardados = documentosGuardados(2);
        assertEquals(2, guardados.size());
        assertEquals(1, guardados.stream().filter(d -> d instanceof ComprobanteFiscal).count());
        assertEquals(1, guardados.stream().filter(d -> d instanceof VoucherDeCredito).count());
    }

    @Test
    void elComprobanteFiscalTieneElMontoDeLosServicios() {
        generador.reservaFinalizada(reservaMock);

        assertEquals(15000.0, comprobanteGuardado(2).getMonto(), 0.001);
    }

    @Test
    void elComprobanteFiscalTieneUnaDescripcion() {
        generador.reservaFinalizada(reservaMock);

        assertEquals("Comprobante de reserva finalizada", comprobanteGuardado(2).getDescripcion());
    }

    @Test
    void elVoucherTieneElMontoDeLosServicios() {
        generador.reservaFinalizada(reservaMock);

        assertEquals(15000.0, voucherGuardado(2).getMonto(), 0.001);
    }

    @Test
    void elComprobanteYElVoucherDeUnaMismaReservaComparteId() {
        generador.reservaFinalizada(reservaMock);

        List<DocumentoRegistrable> guardados = documentosGuardados(2);

        assertEquals(guardados.get(0).getID(), guardados.get(1).getID());
    }

    @Test
    void laPrimeraReservaFinalizadaUsaElIdUno() {
        generador.reservaFinalizada(reservaMock);

        assertEquals(1, documentosGuardados(2).get(0).getID());
    }

    @Test
    void cadaReservaFinalizadaUsaUnIdDistinto() {
        generador.reservaFinalizada(reservaMock);
        generador.reservaFinalizada(reservaMock);

        List<DocumentoRegistrable> guardados = documentosGuardados(4);

        assertEquals(1, guardados.get(0).getID());
        assertEquals(1, guardados.get(1).getID());
        assertEquals(2, guardados.get(2).getID());
        assertEquals(2, guardados.get(3).getID());
    }

    @Test
    void consultaElCostoDeLosServiciosDeLaReserva() {
        generador.reservaFinalizada(reservaMock);

        verify(reservaMock, atLeastOnce()).calcularCostoDeServicios();
    }

    // ---------- Eventos que no le interesan ----------

    @Test
    void reservaConfirmadaNoGuardaNada() {
        generador.reservaConfirmada(reservaMock);

        verifyNoInteractions(registroMock);
    }

    @Test
    void checkingRealizadoNoGuardaNada() {
        generador.checkingRealizado(reservaMock);

        verifyNoInteractions(registroMock);
    }

    @Test
    void viajeComenzadoNoGuardaNada() {
        generador.viajeComenzado(reservaMock);

        verifyNoInteractions(registroMock);
    }

    @Test
    void reservaCanceladaNoGuardaNada() {
        generador.reservaCancelada(reservaMock);

        verifyNoInteractions(registroMock);
    }

    // ---------- Prueba Con Registro ----------

    @Test
    void conUnRegistroRealLosDocumentosGeneradosSePuedenRecuperarPorId() {
        RegistroDocumentos registroReal = new RegistroDocumentos();
        GeneradorVoucher generadorReal = new GeneradorVoucher(registroReal);

        generadorReal.reservaFinalizada(reservaMock);

        DocumentoRegistrable documento = registroReal.getDocumento(1);
        assertEquals(15000.0, documento.getMonto(), 0.001);
    }
}
