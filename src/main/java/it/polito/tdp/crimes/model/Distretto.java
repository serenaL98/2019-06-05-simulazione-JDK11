package it.polito.tdp.crimes.model;

import com.javadocmd.simplelatlng.LatLng;

public class Distretto{
	
	private Integer id;
	private Double longit;
	private Double latit;
	
	private LatLng centro;
	
	public Distretto(Integer id, LatLng centro) {
		super();
		this.id = id;
		this.centro = centro;
	}

	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Double getLongit() {
		return longit;
	}


	public void setLongit(Double longit) {
		this.longit = longit;
	}


	public Double getLatit() {
		return latit;
	}


	public void setLatit(Double latit) {
		this.latit = latit;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((latit == null) ? 0 : latit.hashCode());
		result = prime * result + ((longit == null) ? 0 : longit.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Distretto other = (Distretto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (latit == null) {
			if (other.latit != null)
				return false;
		} else if (!latit.equals(other.latit))
			return false;
		if (longit == null) {
			if (other.longit != null)
				return false;
		} else if (!longit.equals(other.longit))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Distretto " + id + ", centro: " + centro ;
	}


	public LatLng getCentro() {
		return centro;
	}


	public void setCentro(LatLng centro) {
		this.centro = centro;
	}

	
}
