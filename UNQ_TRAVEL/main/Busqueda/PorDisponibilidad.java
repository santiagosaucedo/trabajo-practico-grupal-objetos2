package Busqueda;
import catalogo.Catalogo;

public class PorDisponibilidad implements CriterioBusqueda {
    @Override
    public boolean cumple(Catalogo catalogo) {
        return catalogo.getCuposLibres() > 0;
    }
}