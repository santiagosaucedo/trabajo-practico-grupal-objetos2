package Busqueda;
import catalogo.Catalogo;

public class PorPrecioMaximo implements CriterioBusqueda {
    private double maximo;
    public PorPrecioMaximo(double maximo) { this.maximo = maximo; }

    @Override
    public boolean cumple(Catalogo catalogo) {
        return catalogo.precioBaseCalculado() <= maximo;
    }
}