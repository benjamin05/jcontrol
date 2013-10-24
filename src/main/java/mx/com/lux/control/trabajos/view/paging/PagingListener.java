package mx.com.lux.control.trabajos.view.paging;

import mx.com.lux.control.trabajos.exception.ApplicationException;
import mx.com.lux.control.trabajos.view.render.TableItemRender;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TableItem;

import java.util.List;

public class PagingListener<T> implements Listener {

	private PagingService<T> service;
	private List<T> page;
	private int currentPage;
	private int size = -1;
	private final String SERVICE_ERROR_MESSAGE;
	private final int PAGE_SIZE;
	private final TableItemRender<T> render;

	public PagingListener( int PAGE_SIZE, PagingService<T> service, TableItemRender<T> render, String SERVICE_ERROR_MESSAGE ) {
		this.service = service;
		this.render = render;
		this.PAGE_SIZE = PAGE_SIZE;
		this.SERVICE_ERROR_MESSAGE = SERVICE_ERROR_MESSAGE;
		try {
			size = service.count();
			page = service.getPage( 0, PAGE_SIZE );
			currentPage = 1;
		} catch ( ApplicationException e ) {
			e.printStackTrace();
		}
	}

	@Override
    public void handleEvent( Event event ) {
        TableItem item = ( TableItem ) event.item;
        int index = event.index;
        T t = getItem( index );
        if ( t != null ) {
            item.setText( render.getText( t ) );
        }
    }

	public T getItem( int index ) {
		// obtenemos la posicion dentro de la pagina
		int pagePosition = index % PAGE_SIZE;
		// si sale de la pagina
		if ( index >= PAGE_SIZE * currentPage || index < PAGE_SIZE * ( currentPage - 1 ) ) {
			try {
				page.clear();
				page = service.getPage( index - pagePosition, PAGE_SIZE );
				currentPage = index / PAGE_SIZE + 1;
			} catch ( ApplicationException e ) {
				e.printStackTrace();
				throw new IllegalArgumentException( SERVICE_ERROR_MESSAGE );
			}
		}
        return pagePosition < page.size() ? page.get( pagePosition ) : null;
	}

	public int size() {
		return size;
	}
}
