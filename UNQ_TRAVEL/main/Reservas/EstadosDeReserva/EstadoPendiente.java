package Reservas.EstadosDeReserva;
import Reservas.Reserva;
import catalogo.Catalogo;

public class EstadoPendiente extends EstadoDeReserva  {
	
	@Override
	public void agregarServicio(Reserva unaReserva, Catalogo servicio) {
	    
		unaReserva.incorporarServicio(servicio);
	}
	
	@Override
	
	public void quitarServicio(Reserva unaReserva, Catalogo servicio) {
	    
		unaReserva.retirarServicio(servicio);
	}
}
