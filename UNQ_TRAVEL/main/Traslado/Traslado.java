package Traslado;

public abstract class Traslado {

    public abstract double calcularCosto(double costoReserva);

    public boolean disponibilidad() {
        return true;
    }
}
