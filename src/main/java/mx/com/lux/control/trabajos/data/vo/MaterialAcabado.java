package mx.com.lux.control.trabajos.data.vo;

import org.apache.commons.lang.StringUtils;

public class MaterialAcabado {

	private final Material material;
	private final String forma;
	private final Acabado acabado;

	public MaterialAcabado( Material material, Acabado acabado, String forma ) {
		this.material = material;
		this.acabado = acabado;
		this.forma = forma;
	}

	public MaterialAcabado( final String materialAcabado, final String forma ) {
		String[] cadenas = StringUtils.isNotBlank( materialAcabado ) ? StringUtils.split( materialAcabado, "," ) : new String[]{ };
		switch ( cadenas.length ) {
			case 2:
				material = StringUtils.isNotBlank( cadenas[0] ) ? Material.valueOf( cadenas[0].trim().toUpperCase() ) : Material.NINGUNO;
				acabado = StringUtils.isNotBlank( cadenas[1] ) ? Acabado.valueOf( cadenas[1].trim().toUpperCase() ) : Acabado.NINGUNO;
				break;
			case 1:
				material = StringUtils.isNotBlank( cadenas[0] ) ? Material.valueOf( cadenas[0].trim().toUpperCase() ) : Material.NINGUNO;
				acabado = Acabado.NINGUNO;
				break;
			default:
				material = Material.NINGUNO;
				acabado = Acabado.NINGUNO;
		}
		this.forma = StringUtils.isNotBlank( forma ) ? forma : "";
	}

	public Material getMaterial() {
		return material;
	}

	public String getForma() {
		return forma;
	}

	public Acabado getAcabado() {
		return acabado;
	}

	public enum Material {
		PASTA,
		METAL,
		NYLON,
		AIRE,
		NINGUNO
	}

	public enum Acabado {
		OPACADO,
		PULIDO,
		NINGUNO
	}
}
