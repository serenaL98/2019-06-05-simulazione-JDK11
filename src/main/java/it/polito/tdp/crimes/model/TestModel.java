package it.polito.tdp.crimes.model;

public class TestModel {

	public static void main(String[] args) {
		Model mod = new Model();
		
		for(Integer i: mod.prendiGiorni(2015, 05)) {
			System.out.println(i + "\n");
		}
	}

}
