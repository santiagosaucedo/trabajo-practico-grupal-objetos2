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
}
