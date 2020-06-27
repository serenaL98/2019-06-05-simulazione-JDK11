package it.polito.tdp.crimes.model;

import java.time.LocalDateTime;

public class Evento implements Comparable<Evento>{
	
	public enum EventType{
		CRIMINE, ARRIVO, GESTITO
	}

	private LocalDateTime data;
	private EventType tipo;
	private Event crimine;
	
	
	public Evento(LocalDateTime data, EventType tipo, Event crimine) {
		super();
		this.data = data;
		this.tipo = tipo;
		this.crimine = crimine;
	}
	
	
	public LocalDateTime getData() {
		return data;
	}
	public void setData(LocalDateTime data) {
		this.data = data;
	}
	public EventType getTipo() {
		return tipo;
	}
	public void setTipo(EventType tipo) {
		this.tipo = tipo;
	}
	public Event getCrimine() {
		return crimine;
	}
	public void setCrimine(Event crimine) {
		this.crimine = crimine;
	}
	@Override
	public int compareTo(Evento o) {
		return this.data.compareTo(o.getData());
	}
	
	
}
