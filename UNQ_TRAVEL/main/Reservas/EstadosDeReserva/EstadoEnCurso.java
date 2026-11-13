package Reservas.EstadosDeReserva;

import NotificadoresYDocumentos.ObservadorReserva.ObservadorReserva;
import Reservas.Reserva;

public class EstadoEnCurso extends EstadoDeReserva {
	
	@Override
	public void notificarA(ObservadorReserva observador, Reserva reserva) {
	    observador.viajeComenzado(reserva);
	}
}
