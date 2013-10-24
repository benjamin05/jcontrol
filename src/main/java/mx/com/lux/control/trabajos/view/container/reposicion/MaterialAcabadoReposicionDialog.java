package mx.com.lux.control.trabajos.view.container.reposicion;

import mx.com.lux.control.trabajos.bussiness.service.ExternoService;
import mx.com.lux.control.trabajos.bussiness.service.GarantiaService;
import mx.com.lux.control.trabajos.bussiness.service.ReposicionService;
import mx.com.lux.control.trabajos.bussiness.service.trabajo.TrabajoService;
import mx.com.lux.control.trabajos.data.vo.*;
import mx.com.lux.control.trabajos.util.ServiceLocator;
import mx.com.lux.control.trabajos.util.constants.Constants;
import mx.com.lux.control.trabajos.view.Session;
import mx.com.lux.control.trabajos.view.container.DialogoBase;
import mx.com.lux.control.trabajos.view.controltrabajos.ControlTrabajosWindowElements;
import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MaterialAcabadoReposicionDialog extends DialogoBase {

	private final Logger log = LoggerFactory.getLogger( MaterialAcabadoReposicionDialog.class );

	private Shell shell;
	private String rx;

	private final TrabajoService trabajoService;
	private final ReposicionService reposicionService;
	private final GarantiaService garantiaService;
	private final ExternoService externoService;

	private Button checkboxMaterialPasta;
	private Button checkboxMaterialMetal;
	private Button checkboxMaterialNylon;
	private Button checkboxMaterialAire;
	private Text textoForma;
	private Button checkboxAcabadoOpacadao;
	private Button checkboxAcabadoPulido;
	private Button checkboxAcabadoNinguno;

	protected MaterialAcabadoReposicionDialog( final Shell parentShell, final String rx ) {
		super( parentShell, "Material y Acabado", 4, 50, 5, 260 );
		this.rx = rx;
		garantiaService = ServiceLocator.getBean( GarantiaService.class );
		trabajoService = ServiceLocator.getBean( TrabajoService.class );
		reposicionService = ServiceLocator.getBean( ReposicionService.class );
		externoService = ServiceLocator.getBean( ExternoService.class );
	}

	@Override
	protected Control createContents( Composite parent ) {
		parent.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );

		Composite contenedorPrincipal = crearContenedorPrincipal( parent );
		Composite contenedorIzquierdo = crearContenedorIzquierdo( contenedorPrincipal );
		Composite contenedorDerecho = crearContenedorDerecho( contenedorPrincipal );
		crearCampos( contenedorIzquierdo, contenedorDerecho );
		crearBotones( contenedorPrincipal );
		configurarCampos();
		return parent;
	}

	private Composite crearContenedorPrincipal( final Composite parent ) {
		Composite contenedor = new Composite( parent, SWT.NONE );
		contenedor.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridLayout layout = new GridLayout( numeroColumnas, false );
		layout.marginWidth = 0;
		layout.marginHeight = 5;
		layout.horizontalSpacing = 0;
		contenedor.setLayout( layout );
		GridData gd = new GridData();
		gd.widthHint = getInitialSize().x;
		gd.heightHint = getInitialSize().y;
		gd.verticalIndent = 10;
		gd.horizontalIndent = 10;
		contenedor.setLayoutData( gd );
		return contenedor;
	}

	private Composite crearContenedorIzquierdo( final Composite parent ) {
		Composite contenedor = new Composite( parent, SWT.NONE );
		contenedor.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridLayout layout = new GridLayout( 2, false );
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		layout.horizontalSpacing = 0;
		contenedor.setLayout( layout );
		GridData gd = new GridData();
		gd.widthHint = 120;
		gd.heightHint = 160;
		gd.verticalIndent = 0;
		gd.horizontalIndent = 10;
		gd.horizontalSpan = 2;
		contenedor.setLayoutData( gd );
		return contenedor;
	}

	private Composite crearContenedorDerecho( final Composite parent ) {
		Composite contenedor = new Composite( parent, SWT.NONE );
		contenedor.setBackgroundImage( ControlTrabajosWindowElements.backgroundImagePlate );
		GridLayout layout = new GridLayout( 2, false );
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		layout.horizontalSpacing = 0;
		contenedor.setLayout( layout );
		GridData gd = new GridData();
		gd.widthHint = 120;
		gd.heightHint = 160;
		gd.verticalIndent = 0;
		gd.horizontalIndent = 10;
		gd.horizontalSpan = 2;
		contenedor.setLayoutData( gd );
		return contenedor;
	}

	private void crearCampos( final Composite contenedorIzquierdo, Composite contenedorDerecho ) {
		crearElementoEtiqueta( contenedorIzquierdo, "Material", 2 );
		checkboxMaterialPasta = crearElementoRadio( contenedorIzquierdo, "Pasta", 2 );
		checkboxMaterialMetal = crearElementoRadio( contenedorIzquierdo, "Metal", 2 );
		checkboxMaterialNylon = crearElementoRadio( contenedorIzquierdo, "Nylon", 2 );
		checkboxMaterialAire = crearElementoRadio( contenedorIzquierdo, "Aire", 2 );
		crearElementoEtiqueta( contenedorIzquierdo, "Forma", 2 );

		crearElementoEtiqueta( contenedorDerecho, "Acabado", 2 );
		checkboxAcabadoOpacadao = crearElementoRadio( contenedorDerecho, "Opacado", 2 );
		checkboxAcabadoPulido = crearElementoRadio( contenedorDerecho, "Pulido", 2 );
		checkboxAcabadoNinguno = crearElementoRadio( contenedorDerecho, "Ninguno", 2 );
		crearElementoVacio( contenedorDerecho, 2 );
		textoForma = crearElementoTexto( contenedorDerecho, "", 2, TipoElemento.DESACTIVADO );
        ValidarRadioSelect();

	}


    private void ValidarRadioSelect( ){

        Empleado empleado = (Empleado) Session.getAttribute( Constants.PARAM_USER_LOGGED );
        Sucursal sucursalLoc = empleado.getSucursal();
        Jb jb = trabajoService.findById( rx );
        String idGarantia = rx.replace( "G", "" );
        JbGarantia jbGar = garantiaService.obtenerGarantiaPorFactura( idGarantia );
        Integer sucursal = 0;
        if( jbGar != null ){
            sucursal = Integer.parseInt( jbGar.getSucursal().trim() );
        }
        if( jb.getJbTipo().trim().equalsIgnoreCase( "LAB" ) || ( jb.getJbTipo().trim().equalsIgnoreCase( "GAR" )
                && sucursalLoc.getIdSucursal() == sucursal ) ){
            NotaVenta notaVenta = trabajoService.findNotaVentaByFactura( rx );
            if( notaVenta == null && jbGar != null ){
                notaVenta = trabajoService.findNotaVentaByFactura( jbGar.getFactura() );
            }
            if( notaVenta != null ){
                String MaterialAcab = notaVenta.getUdf2();
                String[] material = MaterialAcab.split( "," );

                if( material[0].trim().equalsIgnoreCase( "Pasta" ) ){
                    checkboxMaterialPasta.setSelection( true );
                } else if( material[0].trim().equalsIgnoreCase( "Metal" ) ){
                    checkboxMaterialMetal.setSelection( true );
                } else if( material[0].trim().equalsIgnoreCase( "Nylon" ) ){
                    checkboxMaterialNylon.setSelection( true );
                } else if( material[0].trim().equalsIgnoreCase( "Aire" ) ){
                    checkboxMaterialAire.setSelection( true );
                }

                if( material.length > 1 ){
                    if( material[1].trim().equalsIgnoreCase( "Opacado" ) ){
                        checkboxAcabadoOpacadao.setSelection( true );
                    } else if( material[1].trim().equalsIgnoreCase( "Pulido" ) ){
                        checkboxAcabadoPulido.setSelection( true );
                    } else if( material[1].trim().equalsIgnoreCase( "Ninguno" ) ){
                        checkboxAcabadoNinguno.setSelection( true );
                    }
                }

                if( notaVenta.getUdf3() != null ){
                    textoForma.setText( notaVenta.getUdf3() );
                }

            }
        }


        if( jb.getJbTipo().trim().equalsIgnoreCase( "GAR" ) && sucursalLoc.getIdSucursal() != sucursal ){
            Reposicion repo = reposicionService.obtenerReposicionPorFactura( rx );
            if( repo != null ){
                String MaterialAcab = repo.getMaterial();
                String[] material = MaterialAcab.split( "," );

                if( material[0].trim().equalsIgnoreCase( "Pasta" ) ){
                    checkboxMaterialPasta.setSelection( true );
                } else if( material[0].trim().equalsIgnoreCase( "Metal" ) ){
                    checkboxMaterialMetal.setSelection( true );
                } else if( material[0].trim().equalsIgnoreCase( "Nylon" ) ){
                    checkboxMaterialNylon.setSelection( true );
                } else if( material[0].trim().equalsIgnoreCase( "Aire" ) ){
                    checkboxMaterialAire.setSelection( true );
                }

                if( material.length > 1 ){
                    if( material[1].trim().equalsIgnoreCase( "Opacado" ) ){
                        checkboxAcabadoOpacadao.setSelection( true );
                    } else if( material[1].trim().equalsIgnoreCase( "Pulido" ) ){
                        checkboxAcabadoPulido.setSelection( true );
                    } else if( material[1].trim().equalsIgnoreCase( "Ninguno" ) ){
                        checkboxAcabadoNinguno.setSelection( true );
                    }
                }

                if( repo.getTratamientos() != null ){
                    textoForma.setText( repo.getTratamientos() );
                }

            }
        }


        if( jb.getJbTipo().trim().equalsIgnoreCase( "EXT" ) ){
            JbExterno externo = externoService.obtenerExternoPorRx( rx );
            if( externo != null ){
                String MaterialAcab = externo.getMaterial();
                String[] material = MaterialAcab.split( "," );

                if( material[0].trim().equalsIgnoreCase( "Pasta" ) ){
                    checkboxMaterialPasta.setSelection( true );
                } else if( material[0].trim().equalsIgnoreCase( "Metal" ) ){
                    checkboxMaterialMetal.setSelection( true );
                } else if( material[0].trim().equalsIgnoreCase( "Nylon" ) ){
                    checkboxMaterialNylon.setSelection( true );
                } else if( material[0].trim().equalsIgnoreCase( "Aire" ) ){
                    checkboxMaterialAire.setSelection( true );
                }

                if( material.length > 1 ){
                    if( material[1].trim().equalsIgnoreCase( "Opacado" ) ){
                        checkboxAcabadoOpacadao.setSelection( true );
                    } else if( material[1].trim().equalsIgnoreCase( "Pulido" ) ){
                        checkboxAcabadoPulido.setSelection( true );
                    } else if( material[1].trim().equalsIgnoreCase( "Ninguno" ) ){
                        checkboxAcabadoNinguno.setSelection( true );
                    }
                }

                if( externo.getForma() != null ){
                    textoForma.setText( externo.getFactura() );
                }
            }
        }
    }

	private void crearBotones( final Composite contenedor ) {
		Button botonCerrar = crearElementoBoton( contenedor, "Cerrar", 2, ControlTrabajosWindowElements.closeIcon );
		botonCerrar.addSelectionListener( new SelectionListener() {
			@Override
			public void widgetSelected( SelectionEvent event ) {
				close();
			}

			@Override
			public void widgetDefaultSelected( SelectionEvent event ) {
			}
		} );
		Button botonGuardar = crearElementoBoton( contenedor, "Guardar", 2, ControlTrabajosWindowElements.saveIcon );
		botonGuardar.addSelectionListener( new SelectionListener() {
			@Override
			public void widgetSelected( SelectionEvent event ) {
				if ( sonDatosValidos() ) {
					try {
						procesarDatos();
						close();
					} catch ( Exception e ) {
						log.error( "Error al procesar los datos" );
					}
				}
			}

			@Override
			public void widgetDefaultSelected( SelectionEvent event ) {
			}
		} );
	}


	private void configurarCampos() {
		checkboxMaterialPasta.addSelectionListener( new SelectionListener() {
			@Override
			public void widgetSelected( SelectionEvent event ) {
				if ( checkboxMaterialPasta.getSelection() ) {
					textoForma.setEnabled( false );
					textoForma.setText( "" );
				}
			}

			@Override
			public void widgetDefaultSelected( SelectionEvent event ) {
			}
		} );
		checkboxMaterialMetal.addSelectionListener( new SelectionListener() {
			@Override
			public void widgetSelected( SelectionEvent event ) {
				if ( checkboxMaterialMetal.getSelection() ) {
					textoForma.setEnabled( false );
					textoForma.setText( "" );
				}
			}

			@Override
			public void widgetDefaultSelected( SelectionEvent event ) {
			}
		} );
		checkboxMaterialNylon.addSelectionListener( new SelectionListener() {
			@Override
			public void widgetSelected( SelectionEvent event ) {
				if ( checkboxMaterialNylon.getSelection() ) {
					textoForma.setEnabled( false );
					textoForma.setText( "" );
				}
			}

			@Override
			public void widgetDefaultSelected( SelectionEvent event ) {
			}
		} );
		checkboxMaterialAire.addSelectionListener( new SelectionListener() {
			@Override
			public void widgetSelected( SelectionEvent event ) {
				if ( checkboxMaterialAire.getSelection() ) {
					textoForma.setEnabled( true );
				}
			}

			@Override
			public void widgetDefaultSelected( SelectionEvent event ) {
			}
		} );
		MaterialAcabado materialAcabado = (MaterialAcabado) Session.getAttribute( "MATERIAL_ACABADO" );
		if ( materialAcabado != null ) {
			switch ( materialAcabado.getMaterial() ) {
				case PASTA:
					checkboxMaterialPasta.setSelection( true );
					break;
				case METAL:
					checkboxMaterialMetal.setSelection( true );
					break;
				case NYLON:
					checkboxMaterialNylon.setSelection( true );
					break;
				case AIRE:
					checkboxMaterialAire.setSelection( true );
					textoForma.setEnabled( true );
					break;
			}
			switch ( materialAcabado.getAcabado() ) {
				case OPACADO:
					checkboxAcabadoOpacadao.setSelection( true );
					break;
				case PULIDO:
					checkboxAcabadoPulido.setSelection( true );
					break;
				default:
					checkboxAcabadoNinguno.setSelection( true );
					break;
			}
			textoForma.setText( materialAcabado.getForma() );
		}
	}

	private boolean sonDatosValidos() {
		if ( !checkboxMaterialPasta.getSelection() && !checkboxMaterialMetal.getSelection() && !checkboxMaterialNylon.getSelection() && !checkboxMaterialAire.getSelection() ) {
			MessageDialog.openError( shell, "Error", "Debe seleccionar un Material" );
			return false;
		}
		if ( checkboxMaterialAire.getSelection() && StringUtils.isBlank( textoForma.getText() ) ) {
			MessageDialog.openError( shell, "Error", "Debe introducir una Forma si el Material es Aire" );
			return false;
		}
		return true;
	}

	private void procesarDatos() {
		MaterialAcabado.Material material = MaterialAcabado.Material.NINGUNO;
		MaterialAcabado.Acabado acabado = MaterialAcabado.Acabado.NINGUNO;
		if ( checkboxMaterialPasta.getSelection() ) {
			material = MaterialAcabado.Material.PASTA;
		} else if ( checkboxMaterialMetal.getSelection() ) {
			material = MaterialAcabado.Material.METAL;
		} else if ( checkboxMaterialNylon.getSelection() ) {
			material = MaterialAcabado.Material.NYLON;
		} else if ( checkboxMaterialAire.getSelection() ) {
			material = MaterialAcabado.Material.AIRE;
		}
		if( checkboxAcabadoOpacadao.getSelection() ) {
			acabado = MaterialAcabado.Acabado.OPACADO;
		} else if( checkboxAcabadoPulido.getSelection() ) {
			acabado = MaterialAcabado.Acabado.PULIDO;
		}
		MaterialAcabado materialAcabado = new MaterialAcabado( material, acabado, textoForma.getText() );
		Session.setAttribute( "MATERIAL_ACABADO", materialAcabado );
	}
}
