package NotificadoresYDocumentos.Vouchers.Documentos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CodigoTransaccionTests {

    @Test
    void guardaElIdRecibido() {
        CodigoTransaccion codigo = new CodigoTransaccion(7, 1500.0);

        assertEquals(7, codigo.getID());
    }

    @Test
    void guardaElMontoRecibido() {
        CodigoTransaccion codigo = new CodigoTransaccion(7, 1500.0);

        assertEquals(1500.0, codigo.getMonto(), 0.001);
    }

    @Test
    void esUnDocumentoRegistrable() {
        CodigoTransaccion codigo = new CodigoTransaccion(1, 0);

        assertInstanceOf(DocumentoRegistrable.class, codigo);
    }
}
