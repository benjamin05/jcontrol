package mx.com.lux.control.trabajos.data.vo;

import java.io.Serializable;

public class TrackView implements Serializable {

	private TrackViewId id;

	public TrackView() {
	}

	public TrackView( TrackViewId id ) {
		super();
		this.id = id;
	}

	public TrackViewId getId() {
		return id;
	}

	public void setId( TrackViewId id ) {
		this.id = id;
	}
}
