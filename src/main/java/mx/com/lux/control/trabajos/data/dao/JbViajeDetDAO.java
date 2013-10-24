package mx.com.lux.control.trabajos.data.dao;

import mx.com.lux.control.trabajos.data.vo.JbViajeDet;
import mx.com.lux.control.trabajos.exception.DAOException;

import java.util.Date;
import java.util.List;

public interface JbViajeDetDAO extends BasicsDAO {

    public List<JbViajeDet> buscarPorEnviarLaboratorio( String idViaje, Date fecha ) throws DAOException;

    public List<JbViajeDet> buscarRotosExternos( String idViaje, Date fecha ) throws DAOException;

    public List<JbViajeDet> buscarRefRepSP( String idViaje, Date fecha ) throws DAOException;

    public List<JbViajeDet> buscarGar( String idViaje, Date fecha ) throws DAOException;

    public List<JbViajeDet> buscarOS( String idViaje, Date fecha ) throws DAOException;

    public List<JbViajeDet> buscarExternos( String idViaje, Date fecha ) throws DAOException;

    public void saveJbViajeDet( List<JbViajeDet> viajeDet ) throws DAOException;

}
