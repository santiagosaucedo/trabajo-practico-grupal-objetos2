package catalogo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServicioIndividual implements Catalogo{

	private String id;
	
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
	
	public void decrementarCupo() {
		cupo.reservarCupo();
	}
	
	public void liberarCupo() {
		cupo.quitarCupoReservado();
	}
	
	public List<Catalogo> buscarA(CriterioBusqueda criterio){ //PENDIENTE
		return new ArrayList<>();
	}
	
	public Double getPrecioFinal() {
		return precioBase - ((precioBase * descuento) / 100);
	}
	
	public LocalDate getFechaCupoDelServicio() {
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
	
}
