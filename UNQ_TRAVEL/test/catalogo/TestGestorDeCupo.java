package catalogo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestGestorDeCupo {

	GestorDeCupo gestor;
	
	@BeforeEach
	void setUp() {
		gestor = new GestorDeCupo (1, LocalDate.of(2004, 2, 7));
	}
	
	@Test
	void testCreacionDeGestorInvalidado() {
		  assertThrows(RuntimeException.class, () -> new GestorDeCupo (-1, LocalDate.of(2004, 2, 7)));
	}
	
	@Test
	void testeoDeGestorDeCupo() {
		assertEquals(1, gestor.getCuposTotales());
		assertEquals(LocalDate.of(2004, 2, 7), gestor.getFecha());
		assertEquals(0 , gestor.getCuposReservados());
		assertTrue(gestor.hayCuposDisponibles());
		assertThrows(RuntimeException.class, () -> gestor.quitarCupoReservado());
		gestor.reservarCupo();
		assertFalse(gestor.hayCuposDisponibles());
		assertEquals(0, gestor.getCuposLibres());
		assertEquals(1, gestor.getCuposReservados());
		assertThrows(RuntimeException.class, () -> gestor.reservarCupo());
		gestor.quitarCupoReservado();
		assertEquals(0, gestor.getCuposReservados());
		assertEquals(1, gestor.getCuposLibres());
		
	}
	
}
