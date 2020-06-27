package it.polito.tdp.crimes.db;

import it.polito.tdp.crimes.model.Distretto;
import it.polito.tdp.crimes.model.Event;

public class TestDao {

	public static void main(String[] args) {
		EventsDao dao = new EventsDao();
		//for(Event e : dao.listAllEvents())
			//System.out.println(e);
		
		for(Distretto d: dao.prendiCoordinate(2017)) {
			System.out.println(d.toString()+"\n");
		}
		
		for(Integer i: dao.getDayFromYear(2015, 13)) {
			System.out.println(i + "\n");
		}
	}

}
