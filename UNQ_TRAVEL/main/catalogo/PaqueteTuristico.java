package catalogo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


//Un paquete no puede tenerse a si mismo dentro ni admitir catalogos duplicados. 
//Es decir, dos mismas estancias de un elemento no pueden existir en una lista de paquetes.
//Ej: ViajeACanarias no puede estar en un paquete turistico dos veces, ya sea de forma directa o dentro de
//otro paquete.
public class PaqueteTuristico implements Catalogo {

	private List<Catalogo> contenido;
	
	private int porcentajeDeDescuento;
	
	private String nombre;
	
	private String descripcion;
	
	//Se toma como "proveedor" aquel que cree el paquete sin importar los proveedores de sus elementos.
	private String proveedor;
	
	private int ventas;
	
	
	public void agregarCatalogo(Catalogo nuevoCatalogo) {
		if (nuevoCatalogo == this) {
			throw new RuntimeException ("Un paquete no puede contenerse a si mismo");
		}
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
	public void decrementarCupoConVenta() {
		this.decrementarCupoSinVenta();
		ventas = ventas +1;
	}
	
	@Override
	public void decrementarCupoSinVenta() {
		if (!this.sePuedeReservarCupo()) {
			throw new RuntimeException("No pueden reservarse cupos.");
		}
		Stream<Catalogo> productos = contenido.stream();
		productos.forEach(producto -> producto.decrementarCupoSinVenta());
	}

	public void liberarCupoConVenta() {
		this.liberarCupoSinVenta();
		ventas = ventas - 1;
	}
	
	@Override
	public void liberarCupoSinVenta() {
		if(!this.sePuedeQuitarCupo()) {
			throw new RuntimeException("No pueden liberarse cupos.");
		}
		Stream<Catalogo> productos = contenido.stream();
		productos.forEach(producto -> producto.liberarCupoSinVenta());
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
		return productos.mapToInt(producto -> producto.getCuposTotales()).min()
				.orElseThrow(()-> new RuntimeException("El paquete esta vacio"));
	}
	@Override
	public int getCuposReservados() {
		return this.getCuposTotales() - this.getCuposLibres();
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
		return productos.mapToInt(producto -> producto.getCuposLibres()).min()
				.orElseThrow(() -> new RuntimeException("El paquete esta vacio"));
	}
	
	@Override
	public LocalDate getFecha() {
		Stream<Catalogo> productos = contenido.stream();
	    return productos.map(producto -> producto.getFecha()).min((fecha1, fecha2) -> fecha1.compareTo(fecha2))
	    		.orElseThrow(() -> new RuntimeException("El paquete esta vacio"));
	}

	@Override
	public int getVentas() {
		return ventas;
	}

	public List<Catalogo> getContenido() {
		return contenido;
	}
	@Override
	public boolean esDeCategoria(Categoria categoria) {
		Stream<Catalogo> productos = contenido.stream();
		return productos.anyMatch(servicio -> servicio.esDeCategoria(categoria));
	}
	
}
