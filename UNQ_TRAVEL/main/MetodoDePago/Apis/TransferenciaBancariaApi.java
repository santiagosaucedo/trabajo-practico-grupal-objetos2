package MetodoDePago.Apis;

import java.util.Date;

public interface TransferenciaBancariaApi {
    public void validarDatos(int cbu, String alias);
    public void transferenciaInmediata(double montoAPagar);
    public void trasnferenciaProgramada(double montoAPagar, Date fechaProgramada);
}
