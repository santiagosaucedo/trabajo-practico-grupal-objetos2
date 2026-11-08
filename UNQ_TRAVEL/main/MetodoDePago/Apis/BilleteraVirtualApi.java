package MetodoDePago.Apis;

public interface BilleteraVirtualApi {
    public Void validarDatos(double saldoDeLaCuenta , double montoAPagar );
    public Void bloqueoDeSaldo();
    public Void acreditarTransferencia(double montoAPagar);
    void notificarResultado(int transaccionID);

}
