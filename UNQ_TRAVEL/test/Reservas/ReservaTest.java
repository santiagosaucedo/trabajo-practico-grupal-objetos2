package Reservas;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import NotificadoresYDocumentos.ObservadorReserva.ObservadorReserva;
import Reservas.EstadosDeReserva.EstadoCancelado;
import Reservas.EstadosDeReserva.EstadoCheking;
import Reservas.EstadosDeReserva.EstadoConfirmado;
import Reservas.EstadosDeReserva.EstadoDeReserva;
import Reservas.EstadosDeReserva.EstadoEnCurso;
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
	
	@Test
	void desdeConfirmadoSePuedeRealizarElCheckin() {
		
		reserva.cambiarA(new EstadoConfirmado());
	    
	    reserva.realizarCheckin();

	    assertInstanceOf(EstadoCheking.class, reserva.getEstado());
	    verify(observador).checkingRealizado(reserva);
	}
	
	@Test
	void desdeEnCursoSePuedeFinalizarElviaje() {
		
		reserva.cambiarA(new EstadoEnCurso());
	    
	    reserva.finalizarViaje();

	    assertInstanceOf(EstadoFinalizado.class, reserva.getEstado());
	    verify(observador).reservaFinalizada(reserva);
	}
	
	@Test
	void unaReservaFinalizadaNoPermiteNingunaOperacion() {
	    reserva.cambiarA(new EstadoFinalizado());

	    assertAll(
	        () -> assertThrows(OperacionInvalidaException.class, () -> reserva.confirmar()),
	        () -> assertThrows(OperacionInvalidaException.class, () -> reserva.realizarCheckin()),
	        () -> assertThrows(OperacionInvalidaException.class, () -> reserva.comenzarViaje()),
	        () -> assertThrows(OperacionInvalidaException.class, () -> reserva.finalizarViaje()),
	        () -> assertThrows(OperacionInvalidaException.class, () -> reserva.agregarServicio(servicio)),
	        () -> assertThrows(OperacionInvalidaException.class, () -> reserva.quitarServicio(servicio))
	    );
	}
	
	@Test
	void unaReservaCanceladaNoPermiteNingunaOperacion() {
	    reserva.cambiarA(new EstadoCancelado());

	    assertAll(
	        () -> assertThrows(OperacionInvalidaException.class, () -> reserva.confirmar()),
	        () -> assertThrows(OperacionInvalidaException.class, () -> reserva.realizarCheckin()),
	        () -> assertThrows(OperacionInvalidaException.class, () -> reserva.comenzarViaje()),
	        () -> assertThrows(OperacionInvalidaException.class, () -> reserva.finalizarViaje()),
	        () -> assertThrows(OperacionInvalidaException.class, () -> reserva.agregarServicio(servicio)),
	        () -> assertThrows(OperacionInvalidaException.class, () -> reserva.quitarServicio(servicio)),
	        () -> assertThrows(OperacionInvalidaException.class, () -> reserva.cancelar())
	    );
	}
	
	@Test
	void unaReservaConfirmadaNoPermiteOperacionesInvalidas() {
	    reserva.cambiarA(new EstadoConfirmado());

	    assertAll(
	        () -> assertThrows(OperacionInvalidaException.class, () -> reserva.confirmar()),
	        () -> assertThrows(OperacionInvalidaException.class, () -> reserva.comenzarViaje()),
	        () -> assertThrows(OperacionInvalidaException.class, () -> reserva.finalizarViaje()),
	        () -> assertThrows(OperacionInvalidaException.class, () -> reserva.agregarServicio(servicio)),
	        () -> assertThrows(OperacionInvalidaException.class, () -> reserva.quitarServicio(servicio))
	    );
	}
	
	@Test
	void unaReservaEnChekingNoPermiteOperacionesInvalidas() {
	    reserva.cambiarA(new EstadoCheking());

	    assertAll(
	        () -> assertThrows(OperacionInvalidaException.class, () -> reserva.confirmar()),
	        () -> assertThrows(OperacionInvalidaException.class, () -> reserva.realizarCheckin()),
	        () -> assertThrows(OperacionInvalidaException.class, () -> reserva.finalizarViaje()),
	        () -> assertThrows(OperacionInvalidaException.class, () -> reserva.agregarServicio(servicio)),
	        () -> assertThrows(OperacionInvalidaException.class, () -> reserva.quitarServicio(servicio))
	    );
	}
	
	@Test
	void unaReservaEnCursoNoPermiteOperacionesInvalidas() {
	    reserva.cambiarA(new EstadoEnCurso());

	    assertAll(
	        () -> assertThrows(OperacionInvalidaException.class, () -> reserva.confirmar()),
	        () -> assertThrows(OperacionInvalidaException.class, () -> reserva.realizarCheckin()),
	        () -> assertThrows(OperacionInvalidaException.class, () -> reserva.comenzarViaje()),
	        () -> assertThrows(OperacionInvalidaException.class, () -> reserva.agregarServicio(servicio)),
	        () -> assertThrows(OperacionInvalidaException.class, () -> reserva.quitarServicio(servicio))
	    );
	}
	
	@Test
	void unaReservaPendienteNoPermiteOperacionesInvalidas() {
	    

	    assertAll(
	       
	        () -> assertThrows(OperacionInvalidaException.class, () -> reserva.realizarCheckin()),
	        () -> assertThrows(OperacionInvalidaException.class, () -> reserva.comenzarViaje()),
	    	() -> assertThrows(OperacionInvalidaException.class, () -> reserva.finalizarViaje())
	    );
	}
	
	@Test
	void cancelarDesdePendientePasaACanceladoYAvisaAlObservador() {
	    reserva.cancelar();

	    assertInstanceOf(EstadoCancelado.class, reserva.getEstado());
	    verify(observador).reservaCancelada(reserva);
	}

	@Test
	void cancelarDesdePendienteNoAfectaAlCupo() {
	    reserva.agregarServicio(servicio);

	    reserva.cancelar();

	    verify(servicio, never()).liberarCupoConVenta();
	    verify(servicio, never()).decrementarCupoConVenta();
	}
	
	@Test
	void cancelarDesdeConfirmadoPasaACanceladoYAvisaAlObservador() {
	    reserva.cambiarA(new EstadoConfirmado());

	    reserva.cancelar();
	    
	    assertInstanceOf(EstadoCancelado.class, reserva.getEstado());
	    verify(observador).reservaCancelada(reserva);
	    
	}

	@Test
	void cancelarDesdeConfirmadoLiberaElCupoDeLosServicios() {
	    reserva.agregarServicio(servicio);
	    reserva.cambiarA(new EstadoConfirmado());

	    reserva.cancelar();

	    verify(servicio).liberarCupoConVenta();
	    verify(servicio, never()).decrementarCupoConVenta();
	}
	
	@Test
	void unaReservaSinServiciosCuestaCero() {
	    assertEquals(0.0, reserva.calcularCostoDeServicios(), 0.001);
	}

	@Test
	void elCostoDeLosServiciosEsLaSumaDeSusPreciosFinales() {
	    Catalogo otroServicio = mock(Catalogo.class);
	    
	    when(servicio.getPrecioFinal()).thenReturn(1000.0);
	    when(otroServicio.getPrecioFinal()).thenReturn(500.0);
	    
	    reserva.agregarServicio(servicio);
	    reserva.agregarServicio(otroServicio);

	    assertEquals(1500.0, reserva.calcularCostoDeServicios(), 0.001);
	}

}
