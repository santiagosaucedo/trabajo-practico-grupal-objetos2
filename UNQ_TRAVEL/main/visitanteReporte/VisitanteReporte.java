package visitanteReporte;

import reporte.ReporteOcupacionYCupoDisponible;
import reporte.ReportePorProveedor;
import reporte.ReportePorServiciosMasReservados;

public interface VisitanteReporte {

	void visitarOcupacionYCupoDisponible(ReporteOcupacionYCupoDisponible reporte);

	void visitarReporteServiciosMasReservados(ReportePorServiciosMasReservados reporte);
	
	void visitarReportePorProveedor(ReportePorProveedor reporte);
}
