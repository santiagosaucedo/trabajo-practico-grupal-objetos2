package MetodoDePago;

import MetodoDePago.Apis.TarjetaDeCreditoApi;
import MetodoDePago.Apis.TransferenciaBancariaApi;
import NotificadoresYDocumentos.Vouchers.Documentos.ComprobanteCBU;
import NotificadoresYDocumentos.Vouchers.Documentos.RegistroDocumentos;

import java.util.Date;

public class TransferenciaBancaria extends MetodoDePago {
    private TransferenciaBancariaApi miApi;
    private int cbu;
    private String alias;
    private Date fechaProgramada;


    public TransferenciaBancaria(double monto, TransferenciaBancariaApi miApi, int cbu, String alias) {
        super(monto);
        setApi(miApi);
        this.cbu = cbu;
        this.alias = alias;
    }
    public TransferenciaBancaria(double monto, TransferenciaBancariaApi miApi, int cbu, String alias , Date fechaProgramada) {
        this(monto, miApi, cbu, alias);
        this.fechaProgramada = fechaProgramada;
    }

    private void setApi(TransferenciaBancariaApi api){
        this.miApi = api;
    }
    public TransferenciaBancariaApi getApi() {
        return this.miApi;
    }

    @Override
    protected void validarDatos() {
      this.getApi().validarDatos(cbu, alias);
    }

    @Override
    protected void reservarFondos() {}

    @Override
    protected void ejecutarTransaccion() {
        if (fechaProgramada == null) {
            this.getApi().transferenciaInmediata(this.getMontoAPagar());
        } else {
            this.getApi().trasnferenciaProgramada(this.getMontoAPagar(), fechaProgramada);
        }
    }

    @Override
    public void notificarResultado(int transaccionID, RegistroDocumentos registro) {
        registro.guardarDocumento(new ComprobanteCBU(transaccionID, this.getMontoAPagar(), this.cbu));
    }
}
