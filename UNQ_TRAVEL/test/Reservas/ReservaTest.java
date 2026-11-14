package Reservas;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import NotificadoresYDocumentos.ObservadorReserva.ObservadorReserva;
import Reservas.EstadosDeReserva.EstadoCancelado;
import Reservas.EstadosDeReserva.EstadoConfirmado;
import Reservas.EstadosDeReserva.EstadoDeReserva;
import Reservas.EstadosDeReserva.EstadoFinalizado;
import Reservas.EstadosDeReserva.EstadoPendiente;
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
	
	@Test
	void confirmarConCupoBloqueaElCupoYPasaAConfirmado() {
	    when(servicio.sePuedeReservarCupo()).thenReturn(true);
	    reserva.agregarServicio(servicio);

	    reserva.confirmar();

	    verify(servicio).decrementarCupoConVenta();
	    verify(servicio, never()).decrementarCupoSinVenta();
	    assertInstanceOf(EstadoConfirmado.class, reserva.getEstado());
	    verify(observador).reservaConfirmada(reserva);
	}

	@Test
	void confirmarSinCupoLanzaExcepcionYNoBloqueaNada() {
	    when(servicio.sePuedeReservarCupo()).thenReturn(false);
	    reserva.agregarServicio(servicio);

	    assertThrows(OperacionInvalidaException.class, () -> reserva.confirmar());

	    verify(servicio, never()).decrementarCupoConVenta();
	    assertInstanceOf(EstadoPendiente.class, reserva.getEstado());
	}

	@Test
	void siUnServicioNoTieneCupoNingunoSeBloquea() {
	    Catalogo sinCupo = mock(Catalogo.class);
	    when(servicio.sePuedeReservarCupo()).thenReturn(true);
	    when(sinCupo.sePuedeReservarCupo()).thenReturn(false);
	    reserva.agregarServicio(servicio);
	    reserva.agregarServicio(sinCupo);

	    assertThrows(OperacionInvalidaException.class, () -> reserva.confirmar());

	    verify(servicio, never()).decrementarCupoConVenta();
	    verify(sinCupo, never()).decrementarCupoConVenta();
	}

	@Test
	void noSePuedeConfirmarUnaReservaSinServicios() {
	    assertTrue(reserva.getServicios().isEmpty());

	    OperacionInvalidaException error = assertThrows(OperacionInvalidaException.class,
	            () -> reserva.confirmar());

	    assertEquals("La reserva no tiene servicios", error.getMessage());
	    assertInstanceOf(EstadoPendiente.class, reserva.getEstado());
	}
	

	@Test
	void noSePuedeConfirmarDosVeces() {
	    when(servicio.sePuedeReservarCupo()).thenReturn(true);
	    reserva.agregarServicio(servicio);
	    reserva.confirmar();

	    assertThrows(OperacionInvalidaException.class, () -> reserva.confirmar());
	}

}
