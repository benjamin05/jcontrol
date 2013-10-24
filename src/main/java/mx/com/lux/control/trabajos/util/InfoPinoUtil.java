package mx.com.lux.control.trabajos.util;

import mx.com.lux.control.trabajos.data.dto.InfoPinoDTO;
import mx.com.lux.control.trabajos.util.constants.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class InfoPinoUtil {


	public static void setInfoPino( String urlString, List<InfoPinoDTO> seguimiento, List<InfoPinoDTO> estacion ) {
		List<String> content = getReponseFromURL( urlString );
		parseInfoPino( content, seguimiento, estacion );
	}


	public static List<InfoPinoDTO> getInfoPino( String urlString ) {
		List<String> content = getReponseFromURL( urlString );
		return parseInfoPino( content );
	}

	public static List<String[]> getInfoPinoArray( String urlString ) {
		List<String> content = getReponseFromURL( urlString );
		return parseInfoPinoArray( content );
	}

	public static List<String> getReponseFromURL( String urlString ) {
		List<String> content = new ArrayList<String>();
		String inputLine;
		try {
			URL url = new URL( urlString );
			BufferedReader in = new BufferedReader( new InputStreamReader( url.openStream() ) );

			while ( ( inputLine = in.readLine() ) != null )
				content.add( inputLine.trim() );

			in.close();
		} catch ( MalformedURLException e ) {
			e.printStackTrace();
		} catch ( IOException e ) {
			e.printStackTrace();
		}
		return content;
	}

	public static void parseInfoPino( List<String> content, List<InfoPinoDTO> seguimiento, List<InfoPinoDTO> estacion ) {
		List<InfoPinoDTO> infoPinoDTOList = new ArrayList<InfoPinoDTO>();
		for ( String string : content ) {
			if ( string.startsWith( InfoPinoDTO.TIPO_ESTACION ) ) {
				estacion.add( getInfoPinoEstacion( string.trim() ) );
			} else if ( string.startsWith( InfoPinoDTO.TIPO_SEGUIMIENTO ) ) {
				seguimiento.add( getInfoPinoSeguimientos( string.trim() ) );
			}
		}
	}

	public static List<InfoPinoDTO> parseInfoPino( List<String> content ) {
		List<InfoPinoDTO> infoPinoDTOList = new ArrayList<InfoPinoDTO>();
		for ( String string : content ) {
			if ( string.startsWith( InfoPinoDTO.TIPO_ESTACION ) ) {
				infoPinoDTOList.add( getInfoPinoEstacion( string.trim() ) );
			} else if ( string.startsWith( InfoPinoDTO.TIPO_SEGUIMIENTO ) ) {
				infoPinoDTOList.add( getInfoPinoSeguimientos( string.trim() ) );
			}
		}

		return infoPinoDTOList;
	}

	public static List<String[]> parseInfoPinoArray( List<String> content ) {
		List<String[]> infoPinoList = new ArrayList<String[]>();
		for ( String string : content ) {
			infoPinoList.add( string.split( Constants.SYMBOL_PIPE ) );
		}
		return infoPinoList;
	}


	public static InfoPinoDTO getInfoPinoEstacion( String content ) {
		String[] data = content.split( InfoPinoDTO.SEPARATOR );
		InfoPinoDTO infoPinoDTO = new InfoPinoDTO( data[0], data[1], data[2], data[3] );
		return infoPinoDTO;
	}

	public static InfoPinoDTO getInfoPinoSeguimientos( String content ) {
		String[] data = content.split( InfoPinoDTO.SEPARATOR );
		InfoPinoDTO infoPinoDTO = new InfoPinoDTO( data[0], data[1] );
		return infoPinoDTO;
	}

}
