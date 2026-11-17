package catalogo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Busqueda.CriterioBusqueda;

public class ServicioIndividual implements Catalogo{

	private String id;
	
	private int ventas;
	
	private String nombre;
	
	private String proveedor;
	
	private String descripcion;
	
	private Double precioBase;
	
	private List<AtributoDinamico> atributosAdicionales;
	
	private GestorDeCupo cupo;
	
	private Categoria categoria;
	
	private Double descuento;
	
	public ServicioIndividual(String id, String nombre, String proveedor, String descripcion, Double precioBase,
			List<AtributoDinamico> atributosAdicionales, GestorDeCupo cupo, Categoria categoria, Double descuento) {
		
		if(descuento > 100 || descuento < 0) {
		throw new RuntimeException ("No es un porcentaje valido");
		}
		this.id = id;
		this.nombre = nombre;
		this.proveedor = proveedor;
		this.descripcion = descripcion;
		this.precioBase = precioBase;
		this.atributosAdicionales = atributosAdicionales;
		this.cupo = cupo;
		this.categoria = categoria;
		this.descuento = descuento;
		
	}
	
	public boolean tieneAtributosObligatoriosAsignados() {
		return id != null 
		&& nombre != null
		&& proveedor != null
		&& descripcion != null
		&& precioBase != null
		&& atributosAdicionales != null
		&& cupo != null
		&& descuento != null
		&& categoria != null;
	}
	
	public boolean tieneAtributosAdicionalesAsignados() {
		return atributosAdicionales.stream().allMatch(atributo -> atributo.esValido());
	}
	
	public boolean todosSusAtributosEstanAsignados(){
		return this.tieneAtributosObligatoriosAsignados() && this.tieneAtributosAdicionalesAsignados();
	}
	
	public void agregarAtributo(AtributoDinamico nuevoAtributo) {
		atributosAdicionales.add(nuevoAtributo);
	}
	
	public List<AtributoDinamico> getAtributosAdicionales() {
		return atributosAdicionales;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public Double precioBaseCalculado() {
		return precioBase;
	}
	
	public void decrementarCupoSinVenta() {
		cupo.reservarCupo();
	}
	
	public void decrementarCupoConVenta() {
		this.decrementarCupoSinVenta();
		ventas = ventas + 1;
	}
	
	public void liberarCupoSinVenta() {
		cupo.quitarCupoReservado();
	}
	
	public void liberarCupoConVenta() {
		this.liberarCupoSinVenta();
		ventas = ventas - 1;
	}
	
	@Override
	public List<Catalogo> buscarA(CriterioBusqueda criterio) {
	    if (criterio.cumple(this)) {
	        return List.of(this);
	    }
	    return List.of();
	}
	
	public Double getPrecioFinal() {
		return precioBase - ((precioBase * descuento) / 100);
	}
	
	public LocalDate getFecha() {
		return cupo.getFecha();
	}
	
	public int getCuposTotales() {
		return cupo.getCuposTotales();
	}


	public int getCuposReservados() {
		return cupo.getCuposReservados();
	}
	
	public int getCuposLibres() {
		return cupo.getCuposLibres();
	}
	
	public boolean sePuedeQuitarCupo() {
		return cupo.sePuedeQuitarCupo();
	}
	
	public boolean sePuedeReservarCupo() {
		return cupo.sePuedeReservarCupo();
	}

	public String getProveedor() {
		return proveedor;
	}
	
	public Categoria getCategoria() {
		return categoria;
	}

	public int getVentas() {
		return ventas;
	}

	@Override
	public boolean esDeCategoria(Categoria categoria) {
		return this.categoria == categoria;
	}

}
