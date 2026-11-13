package Reservas.EstadosDeReserva;

import NotificadoresYDocumentos.ObservadorReserva.ObservadorReserva;
import Reservas.Reserva;

public class EstadoCheking extends EstadoDeReserva {
	
	@Override
	public void notificarA(ObservadorReserva observador, Reserva reserva) {
	    observador.checkingRealizado(reserva);
	}
}
