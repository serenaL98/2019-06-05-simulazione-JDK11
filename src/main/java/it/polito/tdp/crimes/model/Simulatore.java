package it.polito.tdp.crimes.model;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.crimes.db.EventsDao;
import it.polito.tdp.crimes.model.Evento.EventType;

public class Simulatore {
	
	//TIPI DI EVENTO
	//1Evento criminoso--> agentte piu vicino--> non c'è: CRIMINE MAL GESTITO
	//										--> c'è: agente occupato
	
	//agente arriva sul posto--> durata intervento
	//						--> malgestito?
	
	//crimine terminato: agente torna libero
	
	//INPUT
	private Integer N;
	private Integer anno;
	private Integer mese;
	private Integer gg;
	
	//MODELLO DEL MONDO
	private Graph<Integer, DefaultWeightedEdge> grafo;
	
	//CODA DEGLI EVENTI
	private PriorityQueue<Evento> coda;
	private Map<Integer, Integer> agentiLiberi;	//agenti liberi nel distretto
	
	//OUTPUT
	private Integer casiMalgestiti;
	
	//SIMULAZIONE
		//INIZIALIZZAZIONE
	public void inizio(Integer anno, Integer mese, Integer gg, Integer numero, Graph<Integer, DefaultWeightedEdge> grafo) {
		this.anno = anno;
		this.mese = mese;
		this.gg = gg;
		this.N = numero;
		this.grafo = grafo;
		
		this.casiMalgestiti = 0;
		this.agentiLiberi = new HashMap<>();
		
		//tutti gli agenti sono in un distretto
		for(Integer i: this.grafo.vertexSet()) {
			agentiLiberi.put(i, 0);
		}
		
		//tutti gli agenti nel distretto con minor criminalita
		EventsDao dao = new EventsDao();
		Integer min = dao.distrettoMinimoCriminal(anno);
		agentiLiberi.put(min, N);
		
		//creo e inizializzo coda
		this.coda = new PriorityQueue<Evento>();

		for(Event e: dao.listAllEventsByDate(anno, mese, gg)) {
			coda.add(new Evento(e.getReported_date(), EventType.CRIMINE, e));
		}
		
	}
	
	public void avvia(){
		while(!coda.isEmpty()) {
			Evento e = coda.poll();
			System.out.print(e);
			processEvent(e);
		}
	}
	
	public void processEvent(Evento e)  {
		
		switch(e.getTipo()) {
		case CRIMINE:
			//agente libero piu vicino?
			Integer partenza = null;
			partenza = cercaAgente(e.getCrimine().getDistrict_id());
			
			if(partenza != null) {
				//agente libero--> diventa occupato
				agentiLiberi.put(partenza, this.agentiLiberi.get(partenza)-1);
				
				//quanto ci mette ad arrivare?
				Double distanza;
				
				if(partenza == e.getCrimine().getDistrict_id()) {
					distanza = 0.0;
				}else {
					//la prendo dal grafo
					distanza = this.grafo.getEdgeWeight(this.grafo.getEdge(partenza, e.getCrimine().getDistrict_id()));
				}
				
				Long secondi = (long) (distanza*3600)/60;
				this.coda.add(new Evento(e.getData().plusSeconds(secondi), EventType.ARRIVO, e.getCrimine()));
				
			}else {
				//nessun agente libero
				casiMalgestiti++;
			}
			
			break;
			
		case ARRIVO:
			//quanto dura l'intervento?
			Long durata = getdurata(e.getCrimine().getOffense_category_id());
			this.coda.add(new Evento(e.getData().plusMinutes(durata), EventType.GESTITO, e.getCrimine()));
			
			//il crimine è ma gestito?
			if( e.getData().isAfter(e.getCrimine().getReported_date().plusMinutes(15)) ) {
				casiMalgestiti++;
			}
			break;
			
		case GESTITO:
			this.agentiLiberi.put(e.getCrimine().getDistrict_id(), agentiLiberi.get(e.getCrimine().getDistrict_id())+1);
			break;
		}
	}
	
	public Integer cercaAgente(Integer distretto) {
		Double distanza = Double.MAX_VALUE;
		Integer daTrovare = null;
		
		//ciclo nei distretti
		for(Integer i: this.agentiLiberi.keySet()) {
			//se ci sono agenti in quel distretto
			if(agentiLiberi.get(i)>0){
				if(i.equals(distretto)) {
					distanza = 0.0;
					daTrovare = i;
				}else if(this.grafo.getEdgeWeight(this.grafo.getEdge(distretto, i))<distanza){
					distanza = this.grafo.getEdgeWeight(this.grafo.getEdge(distretto, i));
					daTrovare = i;
				}
			}
			
		}
		
		return daTrovare;
	}

	private Long getdurata(String categoria) {
		
		if(categoria.equals("all_other_crimes")) {
			double prob = Math.random();
			if(prob>0.5) {
				//ci metto due ore
				return Long.valueOf(2*60*60);
			}else
				//ci metto un'ora
				return Long.valueOf(60*60);
		}else {
			return Long.valueOf(2*60*60);
		}
		
	}

	public Integer getCasiMalgestiti() {
		return this.casiMalgestiti;
	}
	
}
