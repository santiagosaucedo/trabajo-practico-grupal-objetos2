package Busqueda;
import java.util.List;
import catalogo.Catalogo;

public class CriterioOR implements CriterioBusqueda {
    private List<CriterioBusqueda> misCriterios;
    public CriterioOR(CriterioBusqueda... criterios) { this.misCriterios = List.of(criterios); }

    @Override
    public boolean cumple(Catalogo catalogo) {
        return misCriterios.stream().anyMatch(c -> c.cumple(catalogo));
    }
}