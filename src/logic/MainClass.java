package logic;

import genetika.Evolucia;
import genetika.KrizenieInterface;
import genetika.KrizenieRuleta;
import genetika.KrizenieTurnaj;

public class MainClass {

	public static void main(String[] args) {

		int pocetJedincov = 100;
		int pravdepodobnostMutacie = 60;
		boolean elitarizmus = true;
		int elitarizmusPocet = 4;
		int pocetGeneracii = 5000;

		KrizenieInterface krizenieRuleta = new KrizenieRuleta();
		KrizenieInterface krizenieTurnaj = new KrizenieTurnaj();

		Initializer initializer = new Initializer();
		InitObject init = initializer.init();

		KrizenieInterface typKrizenia = krizenieRuleta;

		Evolucia evolucia = new Evolucia(init, pocetJedincov, typKrizenia,
				pravdepodobnostMutacie, elitarizmus, elitarizmusPocet,
				pocetGeneracii);

		evolucia.spustiEvoluciu();
		
	

	}
}
