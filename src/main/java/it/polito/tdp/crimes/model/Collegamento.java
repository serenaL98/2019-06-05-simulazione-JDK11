package it.polito.tdp.crimes.model;

public class Collegamento implements Comparable<Collegamento> {
	
	private Integer distr1;
	private Integer distr2;
	private Double distanza;
	
	public Collegamento(Integer distr1, Integer distr2, double distanza) {
		super();
		this.distr1 = distr1;
		this.distr2 = distr2;
		this.distanza = distanza;
	}
	public Integer getDistr1() {
		return distr1;
	}
	public void setDistr1(Integer distr1) {
		this.distr1 = distr1;
	}
	public Integer getDistr2() {
		return distr2;
	}
	public void setDistr2(Integer distr2) {
		this.distr2 = distr2;
	}
	public double getDistanza() {
		return distanza;
	}
	public void setDistanza(double distanza) {
		this.distanza = distanza;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(distanza);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((distr1 == null) ? 0 : distr1.hashCode());
		result = prime * result + ((distr2 == null) ? 0 : distr2.hashCode());
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
		Collegamento other = (Collegamento) obj;
		if (Double.doubleToLongBits(distanza) != Double.doubleToLongBits(other.distanza))
			return false;
		if (distr1 == null) {
			if (other.distr1 != null)
				return false;
		} else if (!distr1.equals(other.distr1))
			return false;
		if (distr2 == null) {
			if (other.distr2 != null)
				return false;
		} else if (!distr2.equals(other.distr2))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return distr1 + ", " + distr2 + ", distanza= " + distanza ;
	}
	@Override
	public int compareTo(Collegamento o) {
		return this.distanza.compareTo(o.getDistanza());
	}
	

}
