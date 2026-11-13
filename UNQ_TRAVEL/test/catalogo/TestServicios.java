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
	void testInstanciamientoInvalidoDePaquete() {
		assertThrows(RuntimeException.class, () -> new ServicioIndividual ("Hotel A-23", "Noche de hotel A-23", "Hoteles SA",
				"Estancia de lujo", 500.0, new ArrayList<AtributoDinamico>(List.of(atributoValido)), cupoHotel2, Categoria.ALOJAMIENTO, -1.0));
		
		assertThrows(RuntimeException.class, () -> new ServicioIndividual ("Hotel A-23", "Noche de hotel A-23", "Hoteles SA",
				"Estancia de lujo", 500.0, new ArrayList<AtributoDinamico>(List.of(atributoValido)), cupoHotel2, Categoria.ALOJAMIENTO, 101.0));
		
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
		assertEquals( new ArrayList<AtributoDinamico>(List.of(atributoValido)), hotel.getAtributosAdicionales());
		hotel.agregarAtributo(atributoValido);
		assertEquals(new ArrayList<AtributoDinamico>(List.of(atributoValido, atributoValido)), hotel.getAtributosAdicionales());
	}

	@Test
	void testAccionesSobreCupos() {
		verify(cupoHotel, times(0)).reservarCupo();
		verify(cupoHotel, times(0)).quitarCupoReservado();
		hotel.decrementarCupoConVenta();
		verify(cupoHotel, times(1)).reservarCupo();
		hotel.liberarCupoConVenta();
		verify(cupoHotel, times(1)).quitarCupoReservado();
		
		assertEquals(20, hotel.getCuposTotales());
		assertEquals(10, hotel.getCuposLibres());
		assertEquals(10, hotel.getCuposReservados());
		
		assertTrue(hotel.sePuedeReservarCupo());
		assertFalse(hotel.sePuedeQuitarCupo());
		
		assertFalse(hotelConAtributoDinamicoNoValido.sePuedeReservarCupo());
		assertTrue(hotelConAtributoDinamicoNoValido.sePuedeQuitarCupo());

	}
	
	@Test
	void testPrecios() {
		assertEquals(1000.0, hotel.precioBaseCalculado());
		assertEquals(1000.0, hotel.getPrecioFinal());
		
		assertEquals(1000.0, hotelConAtributoDinamicoNoValido.precioBaseCalculado());
		assertEquals(500.0, hotelConAtributoDinamicoNoValido.getPrecioFinal());
	}

	@Test
	void testGets() {
		
		assertEquals(LocalDate.of(2026,2,2), hotel.getFecha());
		
		assertEquals(hotel.getNombre(), "Noche de Hotel Central");
		
		assertEquals(hotel.getDescripcion(), "Un viaje para recordar");
			
		assertEquals(Categoria.ALOJAMIENTO, hotel.getCategoria());
		
		assertEquals("Hoteles SA", hotel.getProveedor());
	}
	
	@Test
	void verificacionDeCategoria () {
		assertTrue(hotel.esDeCategoria(Categoria.ALOJAMIENTO));
		
		assertFalse(hotel.esDeCategoria(Categoria.EXCURSION));
	}
	
	@Test
	void testVentas() {
		assertEquals(0, hotel.getVentas());
		
		hotel.decrementarCupoConVenta();
		
		assertEquals(1, hotel.getVentas());
		
		hotel.decrementarCupoSinVenta();
		
		assertEquals(1, hotel.getVentas());
		
		hotel.liberarCupoSinVenta();
		
		assertEquals(1, hotel.getVentas());

		hotel.liberarCupoConVenta();
		
		assertEquals(0, hotel.getVentas());

	}
	
}
