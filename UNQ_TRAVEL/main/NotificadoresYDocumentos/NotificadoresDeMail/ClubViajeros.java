package NotificadoresYDocumentos.NotificadoresDeMail;

import NotificadoresYDocumentos.ObservadorReserva.ObservadorReserva;
import Reservas.Reserva;

public class ClubViajeros implements ObservadorReserva {
	
	private MailSender unMailSender;

	public ClubViajeros(MailSender unMailSender) {
		
		this.unMailSender = unMailSender;
	}
	
	
	@Override
	public void reservaCancelada(Reserva unaReserva) {
		
		unMailSender.enviarMail(unaReserva.getDireccionMailCliente(),   
                				"Reserva cancelada",                
                				"Lamentamos que cancele su reserva. En compensacion, le damos un cupon del 5% de descuento en su siguiente compra.",        
                null);              
		
	}
	
	
	
}
