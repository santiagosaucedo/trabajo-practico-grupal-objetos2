package catalogo;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tipoDeValor.TipoBooleano;
import tipoDeValor.TipoFecha;
import tipoDeValor.TipoNumerico;
import tipoDeValor.TipoTexto;

class TestAtributoDinamico {
		
	AtributoDinamico ratingDeEstrellas;
	
	AtributoDinamico seguroPorAccidentes;
	
	AtributoDinamico finalDeServicio;
	
	AtributoDinamico aceptanMascotas;
	
	@BeforeEach
	void setUp() {
		ratingDeEstrellas = new AtributoDinamico("Estrellas", false, new TipoNumerico(), null);
		seguroPorAccidentes = new AtributoDinamico("Documento de seguro por accidente", true, new TipoTexto(), "Datos interesantes");
		finalDeServicio = new AtributoDinamico("Fecha de final de servicio", true , new TipoFecha(), null);
		aceptanMascotas = new AtributoDinamico("Respuesta a la pregunta de si se aceptan mascotas", true , new TipoBooleano(), 5);
	}
	
	@Test
	void validacionDeAtributoDinamico() {
		assertTrue(ratingDeEstrellas.esValido());
		ratingDeEstrellas.setValor(2);
		assertTrue(ratingDeEstrellas.esValido());
		
		assertTrue(seguroPorAccidentes.esValido());
		
		assertFalse(finalDeServicio.esValido());
		finalDeServicio.setValor(LocalDate.of(2026, 7, 4));
		assertTrue(finalDeServicio.esValido());
		
		assertFalse(aceptanMascotas.esValido());
		aceptanMascotas.setValor(true);
		assert(aceptanMascotas).esValido();
	}
	
}
