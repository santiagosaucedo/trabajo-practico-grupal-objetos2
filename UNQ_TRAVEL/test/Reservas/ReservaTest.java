package Reservas;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import NotificadoresYDocumentos.ObservadorReserva.ObservadorReserva;
import Reservas.EstadosDeReserva.EstadoCancelado;
import Reservas.EstadosDeReserva.EstadoDeReserva;
import Reservas.EstadosDeReserva.EstadoFinalizado;
import Traslado.Traslado;
import catalogo.Catalogo;

class ReservaTest {

	private Reserva reserva; // atributo del test, no variable local
	private ObservadorReserva observador;
	private Catalogo servicio;
	

	@BeforeEach
	void setUp() {
		reserva = new Reserva(new ArrayList<>(), "pepito@gmail.com", mock(Traslado.class));
		servicio = mock(Catalogo.class);
		observador = mock(ObservadorReserva.class);
		reserva.agregarObservador(observador);

	}

	@Test
	void unaReservaCanceladaNoPuedeConfirmarse() {

		EstadoDeReserva estadoCancelado = new EstadoCancelado();
		reserva.cambiarA(estadoCancelado);
		assertThrows(OperacionInvalidaException.class, () -> reserva.confirmar());

	}
	
	@Test
	void unaReservaCanceladaAvisaAlObservador() {
		
		//arrange
		EstadoDeReserva estadoCancelado = new EstadoCancelado();
		//act
		reserva.cambiarA(estadoCancelado);
		//assert
		verify(observador).reservaCancelada(reserva);

	}
	
	@Test
	void unaReservaFinalizadaAvisaAlObservador() {

		EstadoDeReserva estadoFinalizado = new EstadoFinalizado();
		reserva.cambiarA(estadoFinalizado);
		verify(observador).reservaFinalizada(reserva);

	}
	
	@Test
	void unaReservaNuevaNoTieneEstadoAnterior() {

		assertTrue(reserva.getEstadoAnterior().isEmpty());
	}
	
	@Test
	void despuesDeUnCambioElEstadoAnteriorEsElInicial() {

		//arrange
		EstadoDeReserva estadoInicial = reserva.getEstado();
		EstadoDeReserva estadoFinalizado = new EstadoFinalizado();
		
		//act
		reserva.cambiarA(estadoFinalizado);
		
		//assert
		
		assertSame(estadoInicial, reserva.getEstadoAnterior().get());
	}
	
	@Test
	void enPendienteSePuedeAgregarUnServicio() {
	   

	    reserva.agregarServicio(servicio);

	    assertTrue(reserva.getServicios().contains(servicio));
	}

	@Test
	void enPendienteSePuedeQuitarUnServicio() {
	    
	    reserva.agregarServicio(servicio);

	    reserva.quitarServicio(servicio);

	    assertFalse(reserva.getServicios().contains(servicio));
	}

	@Test
	void noSePuedeAgregarUnServicioEnEstadoCancelado() {
	    
	    reserva.cambiarA(new EstadoCancelado());

	    assertThrows(OperacionInvalidaException.class,
	                 () -> reserva.agregarServicio(servicio));
	}

	@Test
	void losServiciosQueDevuelveLaReservaNoSePuedenModificarDesdeAfuera() {
	    

	    assertThrows(UnsupportedOperationException.class,
	                 () -> reserva.getServicios().add(servicio));
	}

}
