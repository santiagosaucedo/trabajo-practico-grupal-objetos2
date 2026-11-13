package Reservas;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import catalogo.Catalogo;
import NotificadoresYDocumentos.ObservadorReserva.ObservadorReserva;
import NotificadoresYDocumentos.Vouchers.Documentos.RegistroDocumentos;
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

	public void agregarServicio(Catalogo servicio) {

		estado.agregarServicio(this, servicio);
	}

	public void agregarObservador(ObservadorReserva observador) {

		observadores.add(observador);

	}

	public void quitarServicio(Catalogo servicio) {

		estado.quitarServicio(this, servicio);
	}

	public void confirmar() {

		estado.confirmarReserva(this);
	}

	public void realizarRegistro() {
		// llamar al del estado realizar rembolso el que lo tenga y que haga new
		// voucherDeCredito
	}

	public EstadoDeReserva getEstado() {

		return this.estado;
	}

	public double calcularCostoDeServicios() {

		return 0;

	}

	public Optional<EstadoDeReserva> getEstadoAnterior() {
		return Optional.ofNullable(estadoAnterior);
	}
}
