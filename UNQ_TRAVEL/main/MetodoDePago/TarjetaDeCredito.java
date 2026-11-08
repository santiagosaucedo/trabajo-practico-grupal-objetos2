package MetodoDePago;

import MetodoDePago.Apis.BilleteraVirtualApi;
import MetodoDePago.Apis.TarjetaDeCreditoApi;
import NotificadoresYDocumentos.Vouchers.Documentos.RegistroDocumentos;
import NotificadoresYDocumentos.Vouchers.Documentos.VoucherDeCredito;

import java.util.Date;

public class TarjetaDeCredito extends MetodoDePago {
    private int numeroTarjeta;
    private int cvv;
    private Date fechaVencimiento;
    private TarjetaDeCreditoApi miApi;

    public TarjetaDeCredito(double montoAPagar,int numeroTarjeta, int cvv, Date fechaVencimiento, TarjetaDeCreditoApi miApi) {
        super(montoAPagar);
        this.numeroTarjeta = numeroTarjeta;
        this.cvv = cvv;
        this.fechaVencimiento = fechaVencimiento;
        setApi(miApi);

    }
    private void setApi(TarjetaDeCreditoApi api){
        this.miApi = api;
    }
    public TarjetaDeCreditoApi getApi() {
        return this.miApi;
    }
    @Override
    protected void validarDatos() {
        this.getApi().validarDatos(this.cvv,this.numeroTarjeta,this.fechaVencimiento );
    }

    @Override
    protected void reservarFondos() {
       this.getApi().preAutorizarTransferencia(this.getMontoAPagar());
    }

    @Override
    protected void ejecutarTransaccion() {
       this.getApi().transferirMonto(this.getMontoAPagar());
    }

    @Override
    public void notificarResultado(int transaccionID, RegistroDocumentos registro) {
        registro.guardarDocumento(new VoucherDeCredito(transaccionID, this.getMontoAPagar()));
    }
}
