package MetodoDePago;

import MetodoDePago.Apis.BilleteraVirtualApi;
import NotificadoresYDocumentos.Vouchers.Documentos.RegistroDocumentos;

public class BilleteraVirtual extends MetodoDePago {
    private BilleteraVirtualApi miApi;
    private double saldoTotal;


    public BilleteraVirtual(double montoAPagar, double saldoTotal ,BilleteraVirtualApi miApi) {
        super(montoAPagar);
        setApi(miApi);
        this.saldoTotal = saldoTotal;
    }

    private void setApi(BilleteraVirtualApi api){
        this.miApi = api;
    }
    public BilleteraVirtualApi getApi() {
        return this.miApi;
    }

    @Override
    protected void validarDatos() {
       this.getApi().validarDatos(this.saldoTotal,this.getMontoAPagar());
    }

    @Override
    protected void reservarFondos() {
       miApi.bloqueoDeSaldo();
    }

    @Override
    protected void ejecutarTransaccion() {
        this.getApi().acreditarTransferencia(this.getMontoAPagar());
    }

    @Override
    public void notificarResultado(int transaccionID, RegistroDocumentos registro) {
        miApi.notificarResultado(transaccionID);
    }
}
