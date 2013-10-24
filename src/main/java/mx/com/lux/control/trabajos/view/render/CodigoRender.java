package mx.com.lux.control.trabajos.view.render;

import mx.com.lux.control.trabajos.data.vo.Codigo;
import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

public class CodigoRender implements ILabelProvider {

	@Override
	public void addListener( ILabelProviderListener arg0 ) {
	}

	@Override
	public void dispose() {
	}

	@Override
	public boolean isLabelProperty( Object arg0, String arg1 ) {
		return false;
	}

	@Override
	public void removeListener( ILabelProviderListener arg0 ) {
	}

	@Override
	public Image getImage( Object arg0 ) {
		return null;
	}

	@Override
	public String getText( Object arg0 ) {
		Codigo codigo = (Codigo) arg0;
		if( codigo != null && StringUtils.isNotBlank( codigo.getUsuario() ) ) {
			return codigo.getUsuario();
		}
		return "";
	}

}
