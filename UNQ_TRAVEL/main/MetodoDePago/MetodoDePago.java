package MetodoDePago;

import NotificadoresYDocumentos.Vouchers.Documentos.CodigoTransaccion;
import NotificadoresYDocumentos.Vouchers.Documentos.RegistroDocumentos;

public abstract class MetodoDePago {
    private double montoAPagar;

    public MetodoDePago(double monto){
        this.montoAPagar = monto;
    }

    public void procesarPago(){
        validarDatos();
        reservarFondos();
        ejecutarTransaccion();
    }

    public double getMontoAPagar() {
        return this.montoAPagar;
    }

    protected abstract void validarDatos();
    protected abstract void reservarFondos();
    protected abstract void ejecutarTransaccion();

    protected void notificarResultado(int transaccionID, RegistroDocumentos registro){
        registro.guardarDocumento(new CodigoTransaccion(transaccionID, getMontoAPagar()));
    }
}
