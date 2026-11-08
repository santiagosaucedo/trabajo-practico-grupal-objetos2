package Traslado;

public class PuntoDeEncuentro extends Traslado {
    private int guiasDisponibles;

    public PuntoDeEncuentro(int guiasDisponibles) {
        this.guiasDisponibles = guiasDisponibles;
    }

    @Override
    public double calcularCosto(double costoReserva) {
        return 0;
    }

    @Override
    public boolean disponibilidad() {
        return guiasDisponibles > 0;
    }
}
