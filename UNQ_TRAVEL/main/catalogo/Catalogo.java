package catalogo;

import java.util.List;

public interface Catalogo {

	public String getNombre();
	
	public String getDescripcion();
	
	public Double precioBaseCalculado();
	
	public void decrementarCupo();
	
	public void liberarCupo();
	
	public List<Catalogo> buscarA (CriterioBusqueda criterio);

	public Double getPrecioFinal();
	
	public int getCuposTotales();
	
	public int getCuposReservados();

	public boolean sePuedeReservarCupo();
	
	public boolean sePuedeQuitarCupo();

}
