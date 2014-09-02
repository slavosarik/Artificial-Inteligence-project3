package entity;

import java.util.Set;
import java.util.TreeSet;

import logic.InitObject;

public class Jedinec {

	//zoznam instrukcii
	public int[] instrukcie;
	//zoznam najdenych pokladov
	public Set<Integer> najdenePoklady;
	public int fitnessHodnota;
	//public int pocetNajdenychPokladov;
	public int pocetVykonanychInstrukcii;
	public int aktPozicia;
	public boolean vysielMimoMapy;

	public Jedinec() {
	}

	public Jedinec(Jedinec jedinec, InitObject init) {
		this.vysielMimoMapy = false;
		this.aktPozicia = init.startPoint;
		instrukcie = new int[64];
		System.arraycopy(jedinec.instrukcie, 0, this.instrukcie, 0, 64);
		najdenePoklady = new TreeSet<>();
	}

	public Jedinec(InitObject init, int[] instrukcia) {
		this.vysielMimoMapy = false;
		this.aktPozicia = init.startPoint;
		this.instrukcie = new int[64];
		System.arraycopy(instrukcia, 0, this.instrukcie, 0, 64);
		najdenePoklady = new TreeSet<>();
	}

	public Jedinec(InitObject init) {
		this.vysielMimoMapy = false;
		this.aktPozicia = init.startPoint;
		instrukcie = new int[64];
		najdenePoklady = new TreeSet<>();

	}

}
