package NotificadoresYDocumentos.NotificadoresDeMail;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;


import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import Reservas.Reserva;
import Traslado.Traslado;
import catalogo.Catalogo;

class ClubViajerosTest {

	private MailSender mailSender;
    private Reserva reserva;
    private NotificadorEmail notificador;
    private Catalogo servicio;
    private ClubViajeros unClub;

    @BeforeEach
    void setUp() {
        mailSender = mock(MailSender.class);
        reserva = new Reserva(new ArrayList<>(), "cliente@mail.com", mock(Traslado.class));
        notificador = new NotificadorEmail(mailSender);
        servicio= mock(Catalogo.class);
        unClub = new ClubViajeros(mailSender);
    }
	
	
	@Test
	void elClubEnviaElCuponAlRecibirElAvisoDeCancelacion() {
		
		unClub.reservaCancelada(reserva);
    	verify(mailSender).enviarMail(eq("cliente@mail.com"), anyString(), contains("5%"), any());
		
	}
	
	@Test
	void elClubSoloEnviaMailAlCancelarseLaReserva() {
	    unClub.reservaConfirmada(reserva);
	    unClub.checkingRealizado(reserva);
	    unClub.viajeComenzado(reserva);
	    unClub.reservaFinalizada(reserva);

	    verifyNoInteractions(mailSender);
	}
	
	@Test
	
	void alCancelarUnaReservaElClubEnviaElCupon() {
		
		reserva.agregarObservador(unClub);
		reserva.cancelar();
		
		verify(mailSender).enviarMail(eq("cliente@mail.com"), anyString(), contains("5%"), any());
		
	}

}
