package catalogo;

import java.time.LocalDate;
import java.util.List;

import Busqueda.CriterioBusqueda;

public interface Catalogo {

	public String getNombre();
	
	public String getDescripcion();
	
	public Double precioBaseCalculado();
	
	public void decrementarCupoSinVenta();
	
	public void decrementarCupoConVenta();
	
	public void liberarCupoSinVenta();
	
	public void liberarCupoConVenta();
	
	public List<Catalogo> buscarA (CriterioBusqueda criterio);

	public Double getPrecioFinal();
	
	public int getCuposTotales();
	
	public int getCuposReservados();
	
	public int getCuposLibres();

	public boolean sePuedeReservarCupo();
	
	public boolean sePuedeQuitarCupo();
	
	public LocalDate getFecha();
	
	public String getProveedor();
	
	public int getVentas();
	
	public boolean esDeCategoria(Categoria categoria);

}
