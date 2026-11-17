package Busqueda;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import catalogo.Catalogo;
import catalogo.Categoria;

class CriteriosCombinadosTests {
    private Catalogo item(String nombre, double precio, Categoria cat, int cuposLibres) {
        Catalogo c = mock(Catalogo.class);
        when(c.getNombre()).thenReturn(nombre);
        when(c.precioBaseCalculado()).thenReturn(precio);
        when(c.esDeCategoria(any())).thenAnswer(inv -> inv.getArgument(0) == cat);
        when(c.getCuposLibres()).thenReturn(cuposLibres);
        return c;
    }

    @Test void alojamientoAndPrecioMaximoAndDisponible() {
        CriterioBusqueda criterio = new CriterioAND(
                new PorCategoria(Categoria.ALOJAMIENTO), new PorPrecioMaximo(20000), new PorDisponibilidad());

        assertTrue(criterio.cumple(item("Hotel", 15000, Categoria.ALOJAMIENTO, 2)));
        assertFalse(criterio.cumple(item("Hotel", 15000, Categoria.ALOJAMIENTO, 0)));
    }

    @Test void nombreHotelOResort() {
        CriterioBusqueda criterio = new CriterioOR(new PorNombre("hotel"), new PorNombre("resort"));

        assertTrue(criterio.cumple(item("Gran Resort", 1, Categoria.ALOJAMIENTO, 1)));
        assertFalse(criterio.cumple(item("Cabaña", 1, Categoria.ALOJAMIENTO, 1)));
    }

    @Test void anidadoNombrePackAndNotExcursionAndPrecioMaximo() {
        CriterioBusqueda criterio = new CriterioAND(
                new PorNombre("pack"),
                new CriterioNot(new PorCategoria(Categoria.EXCURSION)),
                new PorPrecioMaximo(25000));

        assertTrue(criterio.cumple(item("Pack Urbano", 18900, Categoria.ALOJAMIENTO, 1)));
        assertFalse(criterio.cumple(item("Pack Tour", 18900, Categoria.EXCURSION, 1)));
    }

    @Test void orDeDosAndsAnidados() {
        CriterioBusqueda criterio = new CriterioOR(
                new CriterioAND(new PorCategoria(Categoria.ALOJAMIENTO), new PorDisponibilidad()),
                new PorNombre("oferta"));

        assertTrue(criterio.cumple(item("Oferta Vuelo", 1, Categoria.VUELO, 0)));
        assertFalse(criterio.cumple(item("Vuelo", 1, Categoria.VUELO, 0)));
    }

    @Test void precioEntreMinimoYMaximo() {
        CriterioBusqueda criterio = new CriterioAND(new PorPrecioMinimo(5000), new PorPrecioMaximo(10000));

        assertTrue(criterio.cumple(item("A", 7000, Categoria.VUELO, 1)));
        assertFalse(criterio.cumple(item("B", 12000, Categoria.VUELO, 1)));
    }
}
