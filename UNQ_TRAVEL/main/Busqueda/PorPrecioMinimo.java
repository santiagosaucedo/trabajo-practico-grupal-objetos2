package Busqueda;
import catalogo.Catalogo;

public class PorPrecioMinimo implements CriterioBusqueda {
    private double minimo;
    public PorPrecioMinimo(double minimo) { this.minimo = minimo; }

    @Override
    public boolean cumple(Catalogo catalogo) {
        return catalogo.precioBaseCalculado() >= minimo;
    }
}