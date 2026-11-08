package NotificadoresYDocumentos.Vouchers.Documentos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ComprobanteFiscalTests {

    @Test
    void guardaElIdRecibido() {
        ComprobanteFiscal comprobante = new ComprobanteFiscal(4, 9000.0, "Factura");

        assertEquals(4, comprobante.getID());
    }

    @Test
    void guardaElMontoRecibido() {
        ComprobanteFiscal comprobante = new ComprobanteFiscal(4, 9000.0, "Factura");

        assertEquals(9000.0, comprobante.getMonto(), 0.001);
    }

    @Test
    void guardaLaDescripcionRecibida() {
        ComprobanteFiscal comprobante = new ComprobanteFiscal(4, 9000.0, "Factura");

        assertEquals("Factura", comprobante.getDescripcion());
    }

    @Test
    void esUnDocumentoRegistrable() {
        ComprobanteFiscal comprobante = new ComprobanteFiscal(1, 0, "");

        assertInstanceOf(DocumentoRegistrable.class, comprobante);
    }
}
