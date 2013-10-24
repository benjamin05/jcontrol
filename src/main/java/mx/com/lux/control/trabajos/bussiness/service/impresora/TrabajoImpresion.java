package mx.com.lux.control.trabajos.bussiness.service.impresora;

import mx.com.lux.control.trabajos.data.vo.*;
import mx.com.lux.control.trabajos.exception.ApplicationException;

import java.util.Date;
import java.util.List;

public interface TrabajoImpresion {

	void imprimirPackingPrevio( Date fecha, int idViaje, Empleado empleado, Sucursal sucursal, List<Jb> trabajosLab, List<Jb> trabajosRotosExternos, List<Jb> trabajosRefRepSp, List<Jb> trabajosGarantias, List<Jb> trabajosOrdenesServicio, List<Jb> trabajosExternos, List<JbSobre> sobresVacios, List<JbSobre> sobresNoVacios, List<JbDev> trabajosDevoluciones, List<DoctoInv> doctoInvList ) throws ApplicationException;

	void imprimirSobre( Empleado empleado, JbSobre sobre ) throws ApplicationException;

	void imprimirPackingCerrado( Date fecha, int idViaje, String folioViaje, Empleado empleado, Sucursal sucursal, List<Jb> trabajosLab, List<Jb> trabajosRotosExternos, List<Jb> trabajosRefRepSp, List<Jb> trabajosGarantias, List<Jb> trabajosOrdenesServicio, List<Jb> trabajosExternos, List<JbSobre> sobresVacios, List<JbSobre> sobresNoVacios, List<JbDev> trabajosDevoluciones, List<DoctoInv> doctoInvList ) throws ApplicationException;

    void imprimirPackingCerradoHist( Date fecha, int idViaje, String folioViaje, Empleado empleado, Sucursal sucursal, List<JbViajeDet> trabajosLab, List<JbViajeDet> trabajosRotosExternos, List<JbViajeDet> trabajosRefRepSp, List<JbViajeDet> trabajosGarantias, List<JbViajeDet> trabajosOrdenesServicio, List<JbViajeDet> trabajosExternos, List<JbSobre> sobresVacios, List<JbSobre> sobresNoVacios, List<JbDev> trabajosDevoluciones, List<DoctoInv> doctoInvList ) throws ApplicationException;

	void imprimirNoSatisfactorio( Jb jb, JbRoto jbRoto, Empleado empleado, Sucursal sucursal ) throws ApplicationException;

	void imprimirOrdenServicio( JbNota jbNota, Empleado empleado, Jb jb, Cliente cliente, Sucursal sucursal, Boolean flag ) throws ApplicationException;

}
