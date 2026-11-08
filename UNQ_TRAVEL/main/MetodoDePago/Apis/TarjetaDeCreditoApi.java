package MetodoDePago.Apis;

import java.util.Date;

public interface TarjetaDeCreditoApi {
    public Void validarDatos(int cvv , int numeroTarjeta, Date fechaVencimiento );
    public Void preAutorizarTransferencia(double montoAPagar);
    public void transferirMonto(double montoAPagar);
}
