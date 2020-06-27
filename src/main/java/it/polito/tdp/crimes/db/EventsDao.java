package it.polito.tdp.crimes.db;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javadocmd.simplelatlng.LatLng;

import it.polito.tdp.crimes.model.Distretto;
import it.polito.tdp.crimes.model.Event;



public class EventsDao {
	
	public List<Event> listAllEvents(){
		String sql = "SELECT * FROM events" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Event> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Event(res.getLong("incident_id"),
							res.getInt("offense_code"),
							res.getInt("offense_code_extension"), 
							res.getString("offense_type_id"), 
							res.getString("offense_category_id"),
							res.getTimestamp("reported_date").toLocalDateTime(),
							res.getString("incident_address"),
							res.getDouble("geo_lon"),
							res.getDouble("geo_lat"),
							res.getInt("district_id"),
							res.getInt("precinct_id"), 
							res.getString("neighborhood_id"),
							res.getInt("is_crime"),
							res.getInt("is_traffic")));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Integer> getYears(){
		String sql = "SELECT DISTINCT YEAR(e.reported_date) anno" + 
				" FROM `events` e" + 
				" ORDER BY YEAR(e.reported_date)";
		List<Integer> lista = new ArrayList<>();
		
		try {
			Connection con = DBConnect.getConnection();
			PreparedStatement st = con.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			
			while(res.next()) {
				Integer ann = res.getInt("anno");
				
				lista.add(ann);
			}
			
			con.close();
			
		}catch(SQLException e) {
			throw new RuntimeException("ERRORE DB: impossibile prendere gli anni.\n", e);
		}
		
		return lista;
		
	}
	
	public List<Integer> getDistricts(){
		String sql = "SELECT DISTINCT e.district_id num" + 
				" FROM `events` e" + 
				" ORDER BY e.district_id";
		List<Integer> lista = new ArrayList<>();
		
		try {
			Connection con = DBConnect.getConnection();
			PreparedStatement st = con.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			
			while(res.next()) {
				Integer n = res.getInt("num");
				
				lista.add(n);
			}
			
			con.close();
			
		}catch(SQLException e) {
			throw new RuntimeException("ERRORE DB: impossibile prendere i distretti.\n", e);
		}
		
		return lista;
	}
	
	public List<Distretto> prendiCoordinate(int anno){
		String sql = "SELECT e.district_id cod, e.geo_lon lon, e.geo_lat lat" + 
				" FROM `events` e" + 
				" WHERE YEAR(e.reported_date) = ? " + 
				" GROUP BY e.district_id" + 
				" ORDER BY e.district_id";
		List<Distretto> lista = new ArrayList<>();
		
		try {
			Connection con = DBConnect.getConnection();
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet res = st.executeQuery();
			
			while(res.next()) {
				Integer id = res.getInt("cod");
				double lon = res.getDouble("lon");
				double lat = res.getDouble("lat");
				
				LatLng centro = new LatLng(lat, lon);
				
				Distretto d = new Distretto(id, centro);
				
				lista.add(d);
				
			}
			
			con.close();
			
		}catch(SQLException e) {
			throw new RuntimeException("ERRORE DB: impossibile prendere i distretti e le coordinate date quell'anno.\n", e);
		}
		
		return lista;
	}

	public List<Integer> getMonthFromYear(int anno){
		String sql = "SELECT distinct MONTH(e.reported_date) dat" + 
				" FROM `events` e" + 
				" WHERE YEAR(e.reported_date) = ? " + 
				" ORDER BY date(e.reported_date)";
		List<Integer> lista = new ArrayList<>();
		
		try {
			Connection con = DBConnect.getConnection();
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet res = st.executeQuery();
			
			while(res.next()) {
				Integer n = res.getInt("dat");
				
				lista.add(n);
			}
			
			con.close();
			
		}catch(SQLException e) {
			throw new RuntimeException("ERRORE DB: impossibile prendere i mesi.\n", e);
		}
		
		return lista;
		
	}
	
	public List<Integer> getDayFromYear(int anno, int mese){
		String sql = "SELECT distinct DAY(e.reported_date) dat" + 
				" FROM `events` e" + 
				" WHERE YEAR(e.reported_date) = ? AND MONTH(e.reported_date) = ? " + 
				" ORDER BY date(e.reported_date)";
		List<Integer> lista = new ArrayList<>();
		
		try {
			Connection con = DBConnect.getConnection();
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, anno);
			st.setInt(2, mese);
			ResultSet res = st.executeQuery();
			
			while(res.next()) {
				Integer n = res.getInt("dat");
				
				lista.add(n);
			}
			
			con.close();
			
		}catch(SQLException e) {
			throw new RuntimeException("ERRORE DB: impossibile prendere i giorni.\n", e);
		}
		
		return lista;
		
	}

	public Integer distrettoMinimoCriminal(Integer anno) {
		String sql = "SELECT e.district_id" + 
				" FROM events e" + 
				" WHERE Year(reported_date) = ? " + 
				" GROUP BY district_id " + 
				" ORDER BY COUNT(*) ASC " + 
				" LIMIT 1";
		Integer n = 0;
		
		try {
			Connection con = DBConnect.getConnection();
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet res = st.executeQuery();
			
			while(res.next()) {
				n = res.getInt("dat");
			}
			
			con.close();
			
		}catch(SQLException e) {
			throw new RuntimeException("ERRORE DB: impossibile prendere i giorni.\n", e);
		}
		
		return n;
		
	}
	
	public List<Event> listAllEventsByDate(int anno, int mese, int gg){
		String sql = "SELECT * FROM events WHERE Year(reported_date) = ? AND month(reported_date) = ? AND DAY(reported_date) = ? " ;
		
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, anno);
			st.setInt(2, mese);
			st.setInt(3, gg);
			
			List<Event> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Event(res.getLong("incident_id"),
							res.getInt("offense_code"),
							res.getInt("offense_code_extension"), 
							res.getString("offense_type_id"), 
							res.getString("offense_category_id"),
							res.getTimestamp("reported_date").toLocalDateTime(),
							res.getString("incident_address"),
							res.getDouble("geo_lon"),
							res.getDouble("geo_lat"),
							res.getInt("district_id"),
							res.getInt("precinct_id"), 
							res.getString("neighborhood_id"),
							res.getInt("is_crime"),
							res.getInt("is_traffic")));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	
}
