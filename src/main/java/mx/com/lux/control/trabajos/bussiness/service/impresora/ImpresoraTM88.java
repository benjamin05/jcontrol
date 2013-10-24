package mx.com.lux.control.trabajos.bussiness.service.impresora;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImpresoraTM88 {

	private FileOutputStream fos;
	private final int ESC = 27;
	private final int AT = 64;
	private final int GS = 29;
	private final int CR = 13;
	private final int LF = 10;
	private final int WRAP_LENGTH = 55;

	public ImpresoraTM88( String nombreArchivo ) throws IOException {
		fos = new FileOutputStream( nombreArchivo );
		fos.write( new byte[]{ ESC, AT } );
	}

	public ImpresoraTM88( File archivo ) throws IOException {
		fos = new FileOutputStream( archivo );
		fos.write( new byte[]{ ESC, AT } );
	}

	private void imprimirInt( int i ) throws IOException {
		fos.write( i );
	}

	public void saltoLinea() throws IOException {
		fos.write( LF );
	}

	public void imprimirString( String string ) throws IOException {
		fos.write( WordUtils.wrap( string, WRAP_LENGTH ).getBytes() );
	}

	public void imprimirString( int anchoColumna, String string ) throws IOException {
		String data = String.format( "%-" + anchoColumna + "s", StringUtils.defaultIfEmpty( string, "" ) );
		imprimirString( data );
	}

	public void imprimirString( String label, String contenido ) throws IOException {
		if ( StringUtils.isNotBlank( label ) && StringUtils.isNotBlank( contenido ) ) {
			String spacedLabel = new StringBuilder( label ).append( " " ).toString();
			StringBuilder sb = new StringBuilder( spacedLabel ).append( contenido );
			String text = WordUtils.wrap( sb.toString(), WRAP_LENGTH ).substring( spacedLabel.length() );
			establecerTipoImpresion( TipoImpresion.ENFATIZADO, TipoFuente.B, false );
			fos.write( spacedLabel.getBytes() );
			establecerTipoImpresion( TipoImpresion.NORMAL, TipoFuente.B, false );
			fos.write( text.getBytes() );
			saltoLinea();
		}
	}

	public void alinear( TipoAlineacion alineacion ) throws IOException {
		fos.write( new byte[]{ ESC, 'a', alineacion.n } );
	}

	public void cortarPapel( TipoCorte tipo ) throws IOException {
		fos.write( new byte[]{ GS, 'V', 49 } );
	}

	public void alimentarPapel( int tamanioo ) throws IOException {
		fos.write( new byte[]{ ESC, 'J', (byte) tamanioo } );
	}

	public void avanzar( int lineas ) throws IOException {
		fos.write( new byte[]{ ESC, 'd', (byte) lineas } );
	}

	public void establecerTamanioLetra( int ancho, int alto ) throws IOException {
		int valor = ( ancho * 16 ) + alto;
		fos.write( new byte[]{ GS, 33, (byte) valor } );
	}

	public void retornoCarro() throws IOException {
		fos.write( CR );
	}

	public void imprimirSubtitulo( String subtitulo ) throws IOException {
		alinear( TipoAlineacion.CENTRO );
		establecerTipoImpresion( TipoImpresion.ENFATIZADO, TipoFuente.A, true );
		imprimirString( subtitulo );
		saltoLinea();
	}

	public void imprimirEncabezado( int anchoColumna, String encabezado ) throws IOException {
		int relleno = anchoColumna - encabezado.length();
		establecerTipoImpresion( TipoImpresion.ENFATIZADO, TipoFuente.B, true );
		imprimirString( encabezado );
		establecerTipoImpresion( TipoImpresion.ENFATIZADO, TipoFuente.B, false );
		if ( relleno > 0 ) {
			imprimirString( String.format( "%-" + relleno + "s", "" ) );
		}
	}

	public void imprimeTitulo( String titulo ) throws IOException {

	}

	public void establecerTipoImpresion( TipoImpresion tipoImpresion, TipoFuente tipoFuente, boolean subrayar ) throws IOException {
		imprimirInt( ESC );
		imprimirInt( 33 );
		int subrayado = subrayar ? 128 : 0;
		int fuente = TipoFuente.A.equals( tipoFuente ) ? 0 : 1;
		switch ( tipoImpresion ) {
			case NORMAL:
				imprimirInt( fuente + subrayado );
				break;
			case ENFATIZADO:
				imprimirInt( 8 + fuente + subrayado );
				// 00001000 negrita 		8
				// 00000001 fuente          1
				// 10000000 subrayado		128
				// 00010000 doblealtura		16
				// 00100000 dobleanchura    32
				break;
			case DOBLEALTURA_ENFATIZADO:
				imprimirInt( 24 + fuente + subrayado );
				// 00011000
				break;
			case DOBLEANCHURA_ENFATIZADO:
				imprimirInt( 40 + fuente + subrayado );
				// 00101000
				break;
			case DOBLEALTURA_DOBLEANCHURA_ENFATIZADO:
				imprimirInt( 56 + fuente + subrayado );
				// 00111000
				break;
			default:

		}
	}

	public void finalizarImpresion() throws IOException {
		fos.close();
	}

	public void imprimirSeparacion() throws IOException {
		fos.write( "__________________________________________".getBytes() );
		fos.write( LF );
	}

	public void imprimirSeparacionInterna() throws IOException {
		fos.write( "------------------------------------------------------".getBytes() );
		fos.write( LF );
	}

	public void imprimirLineaFirma() throws IOException {
		fos.write( "______________________________".getBytes() );
		fos.write( LF );
	}

	public void imprimeCodigoBarras( String valor, TipoAnchuraCodigoBarras tipo, boolean interpretacion ) throws IOException {
		fos.write( new byte[]{ GS, 'h', 70 } );// altura
		fos.write( new byte[]{ GS, 'w', tipo.n } );// anchura
		fos.write( new byte[]{ GS, 'H', (byte) ( interpretacion ? 1 : 0 ) } );// posicion HRI
		fos.write( new byte[]{ GS, 'k', 4 } );// imprimir
		fos.write( valor.getBytes() );// valor
		fos.write( new byte[]{ 0, ESC, 'd', 1 } );
	}
}
