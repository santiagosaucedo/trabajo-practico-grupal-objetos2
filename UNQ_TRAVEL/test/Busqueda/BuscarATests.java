package Busqueda;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import catalogo.Catalogo;
import catalogo.Categoria;
import catalogo.GestorDeCupo;
import catalogo.PaqueteTuristico;
import catalogo.ServicioIndividual;

class BuscarATests {

    private ServicioIndividual servicio(String nombre, double precio, Categoria categoria) {
        return new ServicioIndividual(
                "ID-" + nombre, nombre, "Proveedor", "Descripcion", precio,
                new ArrayList<>(), mock(GestorDeCupo.class), categoria, 0.0);
    }

    private PaqueteTuristico paquete(String nombre, Catalogo... hijos) {
        PaqueteTuristico p = new PaqueteTuristico(0, nombre, "Descripcion", "Proveedor");
        for (Catalogo hijo : hijos) {
            p.agregarCatalogo(hijo); 
        }
        return p;
    }

    // ---------- ServicioIndividual ----------

    @Test void servicioQueCumpleSeDevuelveASiMismo() {
        ServicioIndividual hotel = servicio("Hotel Central", 15000, Categoria.ALOJAMIENTO);

        assertEquals(List.of(hotel), hotel.buscarA(new PorNombre("hotel")));
    }

    @Test void servicioQueNoCumpleDevuelveListaVacia() {
        ServicioIndividual hotel = servicio("Hotel Central", 15000, Categoria.ALOJAMIENTO);

        assertTrue(hotel.buscarA(new PorNombre("resort")).isEmpty());
    }

    @Test void servicioConCriterioCompuesto() {
        ServicioIndividual hotel = servicio("Hotel Central", 15000, Categoria.ALOJAMIENTO);
        CriterioBusqueda criterio = new CriterioAND(
                new PorCategoria(Categoria.ALOJAMIENTO), new PorPrecioMaximo(20000));

        assertEquals(List.of(hotel), hotel.buscarA(criterio));
    }

    // ---------- PaqueteTuristico (Composite) ----------

    @Test void paqueteDevuelveSoloLosHijosQueCumplen() {
        ServicioIndividual hotel = servicio("Hotel Central", 15000, Categoria.ALOJAMIENTO);
        ServicioIndividual tour = servicio("Tour Museo", 4000, Categoria.EXCURSION);
        PaqueteTuristico escapada = paquete("Escapada", hotel, tour);

        List<Catalogo> resultado = escapada.buscarA(new PorCategoria(Categoria.EXCURSION));

        assertTrue(resultado.contains(tour));
        assertFalse(resultado.contains(hotel));
    }

    @Test void paqueteQueCumpleSeIncluyeASiMismo() {
        ServicioIndividual hotel = servicio("Hotel Central", 15000, Categoria.ALOJAMIENTO);
        PaqueteTuristico escapada = paquete("Escapada Hotel", hotel);

        List<Catalogo> resultado = escapada.buscarA(new PorNombre("hotel"));

        assertTrue(resultado.contains(escapada));
        assertTrue(resultado.contains(hotel));
    }

    @Test void paqueteSinCoincidenciasDevuelveVacio() {
        ServicioIndividual hotel = servicio("Hotel Central", 15000, Categoria.ALOJAMIENTO);
        PaqueteTuristico escapada = paquete("Escapada", hotel);

        assertTrue(escapada.buscarA(new PorNombre("crucero")).isEmpty());
    }

    @Test void paqueteAnidadoBuscaEnTodaLaJerarquia() {
        ServicioIndividual tour = servicio("Tour Gastronomico", 6000, Categoria.EXCURSION);
        PaqueteTuristico interno = paquete("Pack Interno", tour);
        PaqueteTuristico externo = paquete("Gran Tour", interno);

        List<Catalogo> resultado = externo.buscarA(new PorNombre("gastronomico"));

        assertEquals(List.of(tour), resultado);
    }

    @Test void paqueteDelegaLaBusquedaEnSusHijos() {
        Catalogo hijo = mock(Catalogo.class);
        CriterioBusqueda criterio = mock(CriterioBusqueda.class);
        when(hijo.buscarA(criterio)).thenReturn(List.of(hijo));
        PaqueteTuristico paquete = paquete("Pack", hijo);

        assertTrue(paquete.buscarA(criterio).contains(hijo));
        verify(hijo).buscarA(criterio);
    }
}