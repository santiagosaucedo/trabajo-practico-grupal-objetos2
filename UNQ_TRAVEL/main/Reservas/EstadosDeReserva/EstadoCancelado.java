package Reservas.EstadosDeReserva;

import NotificadoresYDocumentos.ObservadorReserva.ObservadorReserva;
import Reservas.Reserva;

public class EstadoCancelado extends EstadoDeReserva {
	
	    @Override
		public void notificarA(ObservadorReserva observador, Reserva reserva) {
		    observador.reservaCancelada(reserva);
		}
}
