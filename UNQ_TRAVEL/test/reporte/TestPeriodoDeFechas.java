package reporte;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class TestPeriodoDeFechas {
	
	@Test
	void test() {
		
		PeriodoDeFechas periodo = new PeriodoDeFechas (LocalDate.of(2000, 2, 12), LocalDate.of(2000, 2, 18));
		
		assertTrue(periodo.laFechaEstaEnElPeriodo(LocalDate.of(2000, 2, 12)));
		
		assertTrue(periodo.laFechaEstaEnElPeriodo(LocalDate.of(2000, 2, 18)));
		
		assertTrue(periodo.laFechaEstaEnElPeriodo(LocalDate.of(2000, 2, 16)));
		
		assertFalse(periodo.laFechaEstaEnElPeriodo(LocalDate.of(2000, 2, 19)));
		
		assertFalse(periodo.laFechaEstaEnElPeriodo(LocalDate.of(2000, 2, 11)));

		
	}

}
