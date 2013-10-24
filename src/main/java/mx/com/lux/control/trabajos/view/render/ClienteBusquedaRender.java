package mx.com.lux.control.trabajos.view.render;

import mx.com.lux.control.trabajos.data.vo.Cliente;

public class ClienteBusquedaRender implements TableItemRender<Cliente> {

    @Override
    public String[] getText( Cliente arg ) {
        String[] text = new String[ 3 ];
        text[ 0 ] = String.valueOf( arg != null ? arg.getIdCliente() : "" );
        text[ 1 ] = arg != null ? arg.getNombreCompleto( false ) : "";
        text[ 2 ] = arg != null ? arg.getDireccionCli() : "";
        return text;
    }

}
