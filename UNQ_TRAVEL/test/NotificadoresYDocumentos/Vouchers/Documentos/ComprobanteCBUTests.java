package NotificadoresYDocumentos.Vouchers.Documentos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import NotificadoresYDocumentos.Vouchers.Documentos.ComprobanteCBU;
import NotificadoresYDocumentos.Vouchers.Documentos.DocumentoRegistrable;

class ComprobanteCBUTests {

    @Test
    void guardaElIdRecibido() {
        ComprobanteCBU comprobante = new ComprobanteCBU(10, 8000.0, 123456);

        assertEquals(10, comprobante.getID());
    }

    @Test
    void guardaElMontoRecibido() {
        ComprobanteCBU comprobante = new ComprobanteCBU(10, 8000.0, 123456);

        assertEquals(8000.0, comprobante.getMonto(), 0.001);
    }

    @Test
    void guardaElCbuRecibido() {
        ComprobanteCBU comprobante = new ComprobanteCBU(10, 8000.0, 123456);

        assertEquals(123456, comprobante.getCbu());
    }

    @Test
    void esUnDocumentoRegistrable() {
        ComprobanteCBU comprobante = new ComprobanteCBU(1, 0, 1);

        assertInstanceOf(DocumentoRegistrable.class, comprobante);
    }
}
