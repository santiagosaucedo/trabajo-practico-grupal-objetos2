package NotificadoresYDocumentos.Vouchers;

import NotificadoresYDocumentos.ObservadorReserva.ObservadorReserva;
import NotificadoresYDocumentos.Vouchers.Documentos.ComprobanteFiscal;
import NotificadoresYDocumentos.Vouchers.Documentos.RegistroDocumentos;
import NotificadoresYDocumentos.Vouchers.Documentos.VoucherDeCredito;
import Reservas.Reserva;

public class GeneradorVoucher implements ObservadorReserva {

    private RegistroDocumentos miRegistro;
    private int proximoId = 1;

    public GeneradorVoucher(RegistroDocumentos registro) {
        this.miRegistro = registro;
    }

    @Override
    public void reservaFinalizada(Reserva reserva) {
        int id = proximoId++;
        generarComprobanteFiscal(reserva, id);
        generarVoucher(reserva, id);
    }

    private void generarComprobanteFiscal(Reserva reserva, int id) {
        double monto = reserva.calcularCostoDeServicios();
        ComprobanteFiscal comprobante = new ComprobanteFiscal(id, monto, "Comprobante de reserva finalizada");
        miRegistro.guardarDocumento(comprobante);
    }

    private void generarVoucher(Reserva reserva, int id) {
        double monto = reserva.calcularCostoDeServicios();
        VoucherDeCredito voucher = new VoucherDeCredito(id, monto);
        miRegistro.guardarDocumento(voucher);
    }
    @Override
    public void reservaConfirmada(Reserva reserva) { }
    @Override
    public void checkingRealizado(Reserva reserva) { }
    @Override
    public void viajeComenzado(Reserva reserva) { }
    @Override
    public void reservaCancelada(Reserva reserva) { }
}
