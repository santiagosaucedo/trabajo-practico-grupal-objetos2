package Reservas.EstadosDeReserva;

import catalogo.Catalogo;
import Reservas.Reserva;
import NotificadoresYDocumentos.ObservadorReserva.ObservadorReserva;
import Reservas.OperacionInvalidaException;

public abstract class EstadoDeReserva {

	public void agregarServicio(Reserva unaReserva, Catalogo servicio) {
		throw new OperacionInvalidaException("No se pueden agregar servicios en este estado");
	}

	public void quitarServicio(Reserva unaReserva, Catalogo servicio) {
		throw new OperacionInvalidaException("No se pueden quitar servicios en este estado");
	}

	public void confirmarReserva(Reserva unaReserva) {

		throw new OperacionInvalidaException("No se puede confirmar la reserva en este estado");
	}

	public void notificarA(ObservadorReserva observador, Reserva reserva) {

	}

	public void realizarCheking(Reserva unaReserva) {

		throw new OperacionInvalidaException("No se puede hacer el cheking en este estado");
	}
	
	public void comenzarViaje(Reserva unaReserva) {

		throw new OperacionInvalidaException("No se puede comenzar el viaje en este estado");
	}

	public void finalizarViaje(Reserva unaReserva) {

		throw new OperacionInvalidaException("No se puede terminar el viaje en este estado");
	}

	public void cancelarReserva(Reserva unaReserva) {
		
		throw new OperacionInvalidaException("No se puede cancelar la reserva en este estado");
	}
}
