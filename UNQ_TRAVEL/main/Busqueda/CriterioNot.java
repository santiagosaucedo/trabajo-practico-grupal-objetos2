package Busqueda;
import catalogo.Catalogo;

public class CriterioNot implements CriterioBusqueda {
    private CriterioBusqueda miCriterio;
    public CriterioNot(CriterioBusqueda criterio) { this.miCriterio = criterio; }

    @Override
    public boolean cumple(Catalogo catalogo) {
        return !miCriterio.cumple(catalogo);
    }
}