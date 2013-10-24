package mx.com.lux.control.trabajos.bussiness.service;

import java.util.Map;

public interface TicketService {

	void imprimeEnvioExterno( Map<String, Object> elementos );

	void imprimeRecetaEnvioExterno( Map<String, Object> elementos );

	void imprimeTicketEntregaExterno( String rx );

	void imprimeTicketSucursalDevolucionSp( Integer idJbDev );

	void imprimeTicketSobreDevolucionSp( Integer idJbDev, Integer idSucursal );

	Boolean imprimeTicketGarantia( String rx );

}
