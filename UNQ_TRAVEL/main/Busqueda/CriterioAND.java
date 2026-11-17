package Busqueda;
import java.util.List;
import catalogo.Catalogo;

public class CriterioAND implements CriterioBusqueda {
    private List<CriterioBusqueda> misCriterios;
    public CriterioAND(CriterioBusqueda... criterios) { this.misCriterios = List.of(criterios); }

    @Override
    public boolean cumple(Catalogo catalogo) {
        return misCriterios.stream().allMatch(c -> c.cumple(catalogo));
    }
}