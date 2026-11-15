package Reservas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import catalogo.Catalogo;
import NotificadoresYDocumentos.ObservadorReserva.ObservadorReserva;
import NotificadoresYDocumentos.Vouchers.Documentos.RegistroDocumentos;
import NotificadoresYDocumentos.Vouchers.Documentos.VoucherDeCredito;
import Reservas.EstadosDeReserva.EstadoDeReserva;
import Traslado.Traslado;
import Reservas.EstadosDeReserva.EstadoPendiente;

public class Reserva {

	private EstadoDeReserva estado;
	private List<Catalogo> servicios;
	private List<ObservadorReserva> observadores;
	private String direccionMailCliente;
	private Traslado metodoDeTraslado;
	private RegistroDocumentos historialVouchers;
	private EstadoDeReserva estadoAnterior;
	private int contadorDeId = 1;

	public Reserva(List<Catalogo> servicios, String direccionMailCliente, Traslado metodoDeTraslado) {

		this.estado = new EstadoPendiente();
		this.servicios = new ArrayList<>(servicios);
		this.direccionMailCliente = direccionMailCliente;
		this.metodoDeTraslado = metodoDeTraslado;
		this.historialVouchers = new RegistroDocumentos();
		this.observadores = new ArrayList<>();
	}

	public void cambiarA(EstadoDeReserva nuevoEstado) {

		estadoAnterior = this.getEstado();
		estado = nuevoEstado;
		observadores.forEach(observador -> estado.notificarA(observador, this));

	}

	

	public void agregarObservador(ObservadorReserva observador) {

		observadores.add(observador);

	}

	//La reserva (context) le delega todas estas responsabilidades al estadoDeReserva (State), respetando asi el patron al dejarle decidir a los distintos estados (concrete states) como proceder
	
	public void quitarServicio(Catalogo servicio) {

		estado.quitarServicio(this, servicio);
	}
	
	public void agregarServicio(Catalogo servicio) {

		estado.agregarServicio(this, servicio);
	}

	public void confirmar() {

		estado.confirmarReserva(this);
	}
	
	public void comenzarViaje() {
		
		estado.comenzarViaje(this);
	}
	
	public void finalizarViaje() {
		
		estado.finalizarViaje(this);
	}

	public void realizarCheckin() {
		
		estado.realizarCheking(this);
	}

	public EstadoDeReserva getEstado() {

		return this.estado;
	}

	public double calcularCostoDeServicios() {

		return servicios.stream()
                .mapToDouble(Catalogo::getPrecioFinal)
                .sum();
	}

	public Optional<EstadoDeReserva> getEstadoAnterior() {
		return Optional.ofNullable(estadoAnterior);
	}

	public List<Catalogo> getServicios() {
		
		return Collections.unmodifiableList(servicios);
	}
	
	public void incorporarServicio(Catalogo servicio) {
	    servicios.add(servicio);
	}

	public void retirarServicio(Catalogo servicio) {
	    servicios.remove(servicio);
	}

	public void cancelar() {
		
		estado.cancelarReserva(this);
	}
	
	public void liberarCupoDeServicios() {
		
		getServicios().forEach(servicio -> servicio.liberarCupoConVenta());
		
	}
	
	private void registrarReembolso(double monto) {
		
		historialVouchers.guardarDocumento(new VoucherDeCredito(contadorDeId++, monto));
		
	}
	
	public double montoTotalDelHistorialDeVouchers() {
		
		return historialVouchers.montoTotal();
	}

	public void reembolsarServiciosYTasas() {
		
		double costoServicio = this.calcularCostoDeServicios();  
		double tasas = this.metodoDeTraslado.calcularCosto(costoServicio);
		this.registrarReembolso(costoServicio + tasas);
	}
}
 