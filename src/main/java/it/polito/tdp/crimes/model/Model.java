package it.polito.tdp.crimes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.crimes.db.EventsDao;

public class Model {
	
	private EventsDao dao;
	private List<Integer> anni;
	
	//grafo semplice pesato non orientato
	private Graph<Distretto, DefaultWeightedEdge> grafo;
	
	private List<Distretto> distretti;
	
	public Model() {
		this.dao = new EventsDao();
		this.anni = this.dao.getYears();
		//this.distretti = this.dao.getDistricts();
	}
	
	public List<Integer> elencoAnni(){
		return this.anni;
	}
	
	public void creaGrafo(int anno) {
		
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		//VERTICI
		this.distretti = this.dao.prendiCoordinate(anno);
		
		Graphs.addAllVertices(this.grafo, this.distretti);
		
		//ARCHI
		Collegamento vuoto = null;
		List<Collegamento> collegamenti = new ArrayList<>();
		
		double distanza;
		//LengthUnit KILOMETER = null;
		
		for(Distretto d: this.distretti) {
			for(Distretto t: this.distretti) {
				if(!d.equals(t) && (this.contieneDistretti(collegamenti, d.getId(), t.getId())) == false ) {
					
					distanza = LatLngTool.distance(d.getCentro(), t.getCentro(), LengthUnit.KILOMETER);
					vuoto = new Collegamento(d.getId(), t.getId(), distanza);
					collegamenti.add(vuoto);
					
					Graphs.addEdge(this.grafo, d, t, distanza);
					
				}
			}
		}
		
	}
	
	//la lista dei collegamenti contiene gia i distretti?
	public boolean contieneDistretti(List<Collegamento> col, Integer d1, Integer d2) {
		
		for(Collegamento c: col) {
			if( (c.getDistr1().equals(d1) && c.getDistr2().equals(d2)) || (c.getDistr1().equals(d2) && c.getDistr2().equals(d1))) {
				return true;
			}
		}
		
		return false;
	}
	
	public int numeroVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int numeroArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public String distrettiAdiacenti(Distretto d) {
		String stampa = "";
		List<Distretto> vicini = new ArrayList<>();
		List<Collegamento> col = new ArrayList<>();
		
		//for(Distretto d: this.grafo.vertexSet()) {
			vicini = Graphs.neighborListOf(this.grafo, d);
			for(Distretto di: vicini) {
				for(DefaultWeightedEdge e: this.grafo.edgeSet()) {
					if(this.grafo.getEdgeSource(e).equals(d) && this.grafo.getEdgeTarget(e).equals(di)) {
						col.add(new Collegamento(d.getId(), di.getId(), this.grafo.getEdgeWeight(e)));
					}
				}
			}
		//}
		
		Collections.sort(col);
		
		for(Collegamento c: col) {
			stampa += c.toString()+"\n";
		}
		
		return stampa;
	}
	
	public List<Distretto> verticiGrafo(){
		List<Distretto> lista = new ArrayList<>();
		for(Distretto d: this.grafo.vertexSet()) {
			lista.add(d);
		}
		return lista;
	}
	
	//-------PUNTO 2-------
	List<Integer> mesi;
	List<Integer> giorni;
	private Simulatore sim;
	
	public List<Integer> prendiMesi(int anno){
		this.mesi = this.dao.getMonthFromYear(anno);
		return this.mesi;
	}
	
	public List<Integer> prendiGiorni(int anno, int mese){
		this.giorni = this.dao.getDayFromYear(anno, mese);
		return this.giorni;
	}
	
	//public int malgestiti(int anno, int mese, int gg, int numero,Graph<Distretto, DefaultWeightedEdge> grafo) {
		
		//this.sim.inizio(anno, mese, gg, numero, this.grafo);
		//return this.sim.getCasiMalgestiti();
		
	//}
}
