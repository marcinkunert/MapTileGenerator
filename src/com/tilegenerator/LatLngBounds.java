package com.tilegenerator;
public class LatLngBounds {

	LatLng start;
	LatLng end;

	public LatLngBounds(LatLng start, LatLng end) {
		this.start = start;
		this.end = end;
	}

	@Override
	public String toString() {
		return "[" + start.toString() + " , " + end.toString() + "]";
	}

}
