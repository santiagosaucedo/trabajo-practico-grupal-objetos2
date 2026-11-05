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
		assertEquals(gestor.getCuposTotales(), 1);
		assertEquals(gestor.getFecha(),LocalDate.of(2004, 2, 7));
		assertEquals(gestor.getCuposReservados(), 0);
		assertTrue(gestor.hayCuposDisponibles());
		assertThrows(RuntimeException.class, () -> gestor.quitarCupoReservado());
		gestor.reservarCupo();
		assertFalse(gestor.hayCuposDisponibles());
		assertEquals(gestor.getCuposLibres(), 0);
		assertEquals(gestor.getCuposReservados(), 1);
		assertThrows(RuntimeException.class, () -> gestor.reservarCupo());
		gestor.quitarCupoReservado();
		
	}
	
}
