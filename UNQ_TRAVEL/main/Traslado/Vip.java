package Traslado;

public class Vip extends Traslado{
    private double costoBase;
    private double porcentaje;

    public Vip(double costoBase, double porcentaje) {
        this.costoBase = costoBase;
        this.porcentaje = porcentaje;
    }

    @Override
    public double calcularCosto(double costoReserva) {
        return costoBase + costoReserva * porcentaje;
    }
}
