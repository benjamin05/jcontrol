package mx.com.lux.control.trabajos.view.render;

import mx.com.lux.control.trabajos.data.vo.Prisma;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

public class PrismaRender implements ILabelProvider {

	@Override
	public Image getImage( Object o ) {
		return null;
	}

	@Override
	public String getText( Object o ) {
		if ( o != null && o instanceof Prisma ) {
			Prisma prisma = (Prisma) o;
			return prisma.getDescripcion().toUpperCase().trim();
		}
		return "";
	}

	@Override
	public void addListener( ILabelProviderListener iLabelProviderListener ) {
	}

	@Override
	public void dispose() {
	}

	@Override
	public boolean isLabelProperty( Object o, String s ) {
		return false;
	}

	@Override
	public void removeListener( ILabelProviderListener iLabelProviderListener ) {
	}
}
