package catalogo;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestServicios {
	
	GestorDeCupo cupoHotel;
	GestorDeCupo cupoHotel2; 
	AtributoDinamico atributoNoValido;
	AtributoDinamico atributoValido;
	ServicioIndividual hotel;
	ServicioIndividual hotelConNullAtributoObligatorio;
	ServicioIndividual hotelConAtributoDinamicoNoValido; 
	
 
	@BeforeEach
	void setUp() {
		cupoHotel = mock(GestorDeCupo.class); 
		cupoHotel2 = mock(GestorDeCupo.class);
		atributoNoValido = mock(AtributoDinamico.class);
		atributoValido = mock(AtributoDinamico.class);
		
		hotelConAtributoDinamicoNoValido = new ServicioIndividual("H-001", "Noche de Hotel Central", "Hoteles SA", "Un viaje para recordar",
				1000.0, new ArrayList<AtributoDinamico>(List.of(atributoNoValido)), cupoHotel2, Categoria.ALOJAMIENTO, 50.0);
		
		hotel = new ServicioIndividual("H-001", "Noche de Hotel Central", "Hoteles SA", "Un viaje para recordar",
				1000.0, new ArrayList<AtributoDinamico>(List.of(atributoValido)), cupoHotel, Categoria.ALOJAMIENTO, 0.0);
		
		hotelConNullAtributoObligatorio = new ServicioIndividual(null, "Noche de Hotel Central", "Hoteles SA", "Un viaje para recordar",
				1000.0, new ArrayList<AtributoDinamico>(List.of(atributoValido)), cupoHotel, Categoria.ALOJAMIENTO, 0.0);
		
		when(cupoHotel.sePuedeReservarCupo()).thenReturn(true);
		when(cupoHotel.sePuedeQuitarCupo()).thenReturn(false);
		when(cupoHotel.getFecha()).thenReturn(LocalDate.of(2026,2,2));
		when(cupoHotel.getCuposTotales()).thenReturn(20);
		when(cupoHotel.getCuposLibres()).thenReturn(10);
		when(cupoHotel.getCuposReservados()).thenReturn(10);
		
		when(cupoHotel2.sePuedeReservarCupo()).thenReturn(false);
		when(cupoHotel2.sePuedeQuitarCupo()).thenReturn(true);
								
		when(atributoNoValido.esValido()).thenReturn(false);
		when(atributoValido.esValido()).thenReturn(true);
	}
	
	@Test
	void testValidezDeAtributos() {
		assertFalse(hotelConNullAtributoObligatorio.tieneAtributosObligatoriosAsignados());
		assertFalse(hotelConAtributoDinamicoNoValido.tieneAtributosAdicionalesAsignados());
		
		assertTrue(hotel.tieneAtributosObligatoriosAsignados());
		assertTrue(hotel.tieneAtributosAdicionalesAsignados());
		assertTrue(hotel.todosSusAtributosEstanAsignados());
		
	}
	
	@Test
	void testAgregarAtributoDinamico() {
		assertEquals(hotel.getAtributosAdicionales(), new ArrayList<AtributoDinamico>(List.of(atributoValido)));
		hotel.agregarAtributo(atributoValido);
		assertEquals(hotel.getAtributosAdicionales(), new ArrayList<AtributoDinamico>(List.of(atributoValido, atributoValido)));
	}

	@Test
	void testAccionesSobreCupos() {
		verify(cupoHotel, times(0)).reservarCupo();
		verify(cupoHotel, times(0)).quitarCupoReservado();
		hotel.decrementarCupo();
		verify(cupoHotel, times(1)).reservarCupo();
		hotel.liberarCupo();
		verify(cupoHotel, times(1)).quitarCupoReservado();
		
		assertEquals(hotel.getCuposTotales(), 20);
		assertEquals(hotel.getCuposLibres(), 10);
		assertEquals(hotel.getCuposReservados(), 10);
		
		assertTrue(hotel.sePuedeReservarCupo());
		assertFalse(hotel.sePuedeQuitarCupo());
		
		assertFalse(hotelConAtributoDinamicoNoValido.sePuedeReservarCupo());
		assertTrue(hotelConAtributoDinamicoNoValido.sePuedeQuitarCupo());

	}
	
	@Test
	void testPrecios() {
		assertEquals(hotel.precioBaseCalculado(), 1000.0);
		assertEquals(hotel.getPrecioFinal(), 1000.0);
		
		assertEquals(hotelConAtributoDinamicoNoValido.precioBaseCalculado(), 1000.0);
		assertEquals(hotelConAtributoDinamicoNoValido.getPrecioFinal(), 500.0);
	}

	@Test
	void testGets() {
		
		assertEquals(hotel.getFechaCupoDelServicio(), LocalDate.of(2026,2,2));
		
		assertEquals(hotel.getNombre(), "Noche de Hotel Central");
		
		assertEquals(hotel.getDescripcion(), "Un viaje para recordar");
		
	}
	
}
