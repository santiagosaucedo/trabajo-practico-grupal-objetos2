package NotificadoresYDocumentos.Vouchers.Documentos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class VoucherDeCreditoTests {

    @Test
    void guardaElIdRecibido() {
        VoucherDeCredito voucher = new VoucherDeCredito(3, 2500.0);

        assertEquals(3, voucher.getID());
    }

    @Test
    void guardaElMontoRecibido() {
        VoucherDeCredito voucher = new VoucherDeCredito(3, 2500.0);

        assertEquals(2500.0, voucher.getMonto(), 0.001);
    }

    @Test
    void esUnDocumentoRegistrable() {
        VoucherDeCredito voucher = new VoucherDeCredito(1, 0);

        assertInstanceOf(DocumentoRegistrable.class, voucher);
    }
}
