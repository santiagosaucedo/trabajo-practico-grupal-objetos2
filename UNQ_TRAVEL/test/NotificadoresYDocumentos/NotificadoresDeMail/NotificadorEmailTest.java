package NotificadoresYDocumentos.NotificadoresDeMail;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Reservas.Reserva;
import Traslado.Traslado;
import catalogo.Catalogo;

class NotificadorEmailTest {

    private MailSender mailSender;
    private Reserva reserva;
    private NotificadorEmail notificador;
    private Catalogo servicio;

    @BeforeEach
    void setUp() {
        mailSender = mock(MailSender.class);
        reserva = new Reserva(new ArrayList<>(), "cliente@mail.com", mock(Traslado.class));
        notificador = new NotificadorEmail(mailSender);
        servicio= mock(Catalogo.class);
    	when(servicio.sePuedeReservarCupo()).thenReturn(true);
    }

    @Test
    void unaReservaConfirmadaEnviaMail() {
    	
    	notificador.reservaConfirmada(reserva);
    	verify(mailSender).enviarMail(eq("cliente@mail.com"), anyString(), anyString(), any());
    }
    
    @Test
    void elViajeComenzadoEnviaMail() {
    	notificador.viajeComenzado(reserva);
    	verify(mailSender).enviarMail(eq("cliente@mail.com"), anyString(), anyString(), any());
    }
    
    @Test
    void unaReservaFinalizadaEnviaMail() {
    	notificador.reservaFinalizada(reserva);
    	verify(mailSender).enviarMail(eq("cliente@mail.com"), anyString(), anyString(), any());
    }
    
    @Test
    void unaReservaEnChekingNOEnviaMail() {
    	notificador.checkingRealizado(reserva);
    	verifyNoInteractions(mailSender);
    }
    
    @Test
    void unaReservaCanceladaNOEnviaMail() {
    	notificador.reservaCancelada(reserva);
    	verifyNoInteractions(mailSender);
    }
    
    @Test
    void reservaEnviaElMail() {
    	
    	
    	servicio= mock(Catalogo.class);
    	when(servicio.sePuedeReservarCupo()).thenReturn(true);
    	
    	reserva.agregarObservador(notificador);
    	reserva.agregarServicio(servicio);
    	reserva.confirmar();
    	
    	verify(mailSender).enviarMail(eq("cliente@mail.com"), anyString(), anyString(), any());
    }
}