package catalogo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


//No se admiten catalogos duplicados. Es decir, dos mismas estancias de un elemento no pueden existir en una lista de paquetes ni en la
//lista de un negocio. Ej: ViajeACanarias no puede estar en un paquete turistico dos veces, ya sea de forma directa o dentro de
//otro paquete.
public class PaqueteTuristico implements Catalogo {

	private List<Catalogo> contenido;
	
	private int porcentajeDeDescuento;
	
	private String nombre;
	
	private String descripcion;
	
	private String proveedor;
	
	
	public void agregarCatalogo(Catalogo nuevoCatalogo) {
		contenido.add(nuevoCatalogo);
	}
	
	public PaqueteTuristico(int descuento, String nombre, String descripcion, String proveedor) {
		if(descuento > 100 || descuento < 0) {
			throw new RuntimeException ("No es un porcentaje valido");
		}
		this.contenido = new ArrayList<Catalogo>();
		this.porcentajeDeDescuento = descuento;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.proveedor = proveedor;
	}
	
	public String getProveedor() {
		return proveedor;
	}
	
	@Override
	public String getNombre() {
		return nombre;
	}

	@Override
	public String getDescripcion() {
		return descripcion;
	}

	@Override
	public Double precioBaseCalculado() {
		Stream<Catalogo> productos = contenido.stream();
		return productos.mapToDouble(producto -> producto.precioBaseCalculado()).sum();
	}

	@Override
	public void decrementarCupo() {
		if (!this.sePuedeReservarCupo()) {
			throw new RuntimeException("No pueden reservarse cupos.");
		}
		Stream<Catalogo> productos = contenido.stream();
		productos.forEach(producto -> producto.decrementarCupo());
	}

	@Override
	public void liberarCupo() {
		if(!this.sePuedeQuitarCupo()) {
			throw new RuntimeException("No pueden liberarse cupos.");
		}
		Stream<Catalogo> productos = contenido.stream();
		productos.forEach(producto -> producto.liberarCupo());
		
	}

	@Override
	public List<Catalogo> buscarA(CriterioBusqueda criterio) { // PENDIENTE
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double getPrecioFinal() {
		Double precioBase = this.precioBaseCalculado();
		return precioBase - ((precioBase * porcentajeDeDescuento) / 100);
	}

	@Override
	public int getCuposTotales() {
		Stream<Catalogo> productos = contenido.stream();
		return productos.mapToInt(producto -> producto.getCuposTotales()).sum();
	}

	@Override
	public int getCuposReservados() {
		Stream<Catalogo> productos = contenido.stream();
		return productos.mapToInt(producto -> producto.getCuposReservados()).sum();
	}
	
	public boolean sePuedeReservarCupo() {
		Stream<Catalogo> productos = contenido.stream();
		return productos.allMatch(producto -> producto.sePuedeReservarCupo());
	}

	@Override
	public boolean sePuedeQuitarCupo() {
		Stream<Catalogo> productos = contenido.stream();
		return productos.allMatch(producto -> producto.sePuedeQuitarCupo());
	}

	@Override
	public int getCuposLibres() {
		Stream<Catalogo> productos = contenido.stream();
		return productos.mapToInt(producto -> producto.getCuposLibres()).sum();
	}
	
}
