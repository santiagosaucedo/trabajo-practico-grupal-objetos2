package Traslado;

import Direccion.Direccion;
import Traslado.Plataforma.PlataformaDeTransporte;

public class Estandar extends Traslado {

    private int cantidadPasajeros;
    private Direccion direccionDestino;
    private PlataformaDeTransporte transporte;

    public Estandar(int cantidadPasajeros, Direccion direccionDestino, PlataformaDeTransporte transporte) {
        this.cantidadPasajeros = cantidadPasajeros;
        this.direccionDestino = direccionDestino;
        this.transporte = transporte;
    }

    @Override
    public double calcularCosto(double costoReserva) {
        return transporte.estimarTraslado(cantidadPasajeros, direccionDestino);
    }
}
