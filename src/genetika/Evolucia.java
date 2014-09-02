package genetika;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import logic.InitObject;
import logic.VirtualMachine;
import entity.Jedinec;

public class Evolucia {

	InitObject init;
	int pocetJedincov;
	KrizenieInterface krizenie;
	int pravdepodobnostMutacie;
	boolean elitarizmus;
	int elitarizmusPocet;
	int pocetGeneracii;
	Random random;

	// konstruktor pre nastavenie hodnot
	public Evolucia(InitObject init, int pocetJedincov,
			KrizenieInterface krizenie, int pravdepodobnostMutacie,
			boolean elitarizmus, int elitarizmusPocet, int pocetGeneracii) {

		this.init = init;
		this.pocetJedincov = pocetJedincov;
		this.krizenie = krizenie;
		this.elitarizmus = elitarizmus;
		this.elitarizmusPocet = elitarizmusPocet;
		this.pocetGeneracii = pocetGeneracii;
		this.random = new Random();
		this.pravdepodobnostMutacie = pravdepodobnostMutacie;

	}

	// nahodne generovanie instrukcii
	public void initRandInstrukcie(int[] instrukcie, int rozsah, int pocet) {

		for (int i = 0; i < pocet; i++)
			instrukcie[i] = random.nextInt(rozsah);
	}

	// inicializovanie zaciatocnych hodnot jedincov
	public List<Jedinec> initStart() {

		List<Jedinec> jedince = new ArrayList<>();

		for (int i = 0; i < pocetJedincov; i++) {
			jedince.add(new Jedinec(init));
			// 256 je rozsah pre instrukciu, 64 je pocet instrukcii
			initRandInstrukcie(jedince.get(i).instrukcie, 256, 64);
		}

		return jedince;

	}

	// vypocet fitness
	// za kazdy 1 najdeny poklad = 1000
	// kazda jedna navyse pouzita instrukcia je sa odpocitava 1 z celkovej
	// fitness
	public void countFitness(Jedinec jedinec) {

		jedinec.fitnessHodnota = 1000 * jedinec.najdenePoklady.size()
				- jedinec.pocetVykonanychInstrukcii;
	}

	// mutovanie jedincov
	public void mutacia(List<Jedinec> jedince) {

		int sanca;
		int bit;

		// iterujem cez kazdeho jedinca
		for (Jedinec jedinec : jedince) {
			sanca = random.nextInt(101);

			// pravdepodobnost mutacie jedinca
			if (sanca <= pravdepodobnostMutacie) {

				for (int i = 0; i < jedinec.instrukcie.length; i++) {
					sanca = random.nextInt(101);
					// pravdepodobnost mutacie instrukcie
					if (sanca <= pravdepodobnostMutacie) {

						// invertovanie jedneho nahodneho bitu z instrukcie
						bit = random.nextInt(8);
						if ((jedinec.instrukcie[i] & (int) Math.pow(2, bit)) == (int) Math
								.pow(2, bit))
							jedinec.instrukcie[i] -= (int) Math.pow(2, bit);
						else
							jedinec.instrukcie[i] += (int) Math.pow(2, bit);

					}
				}
			}
		}
	}

	// funkcia pre evoluciu
	public void spustiEvoluciu() {

		VirtualMachine vm = new VirtualMachine();
		final List<Integer> mediany = new ArrayList<>();
		List<Integer> maxy = new ArrayList<>();
		Jedinec vitaz;
		int median;

		boolean najdeneRiesenie = false;
		int generaciaRisenia = 0;
		int fitnesVitaza = 0;

		List<Jedinec> jedince = initStart();

		System.out.println("StartSimulacie");

		// evolucia cez generacie
		for (int i = 0; i < pocetGeneracii; i++) {

			int[] fitnesy = new int[pocetJedincov];

			// hladam poklady s kazdym jedincom
			for (int j = 0; j < jedince.size(); j++) {

				// vykonavanie instrukcii na virtualnom stroji
				if (vm.run(jedince.get(j), init) == true) {
					// true znamena, ze hlaadac nasiel vsetky poklady

					vitaz = new Jedinec(jedince.get(j), init);

					System.out.println("Nasli sme riesenie v Generacii: " + i);
					generaciaRisenia = i;
					najdeneRiesenie = true;
					System.out.println("Vypis: ");

					// vypis instrukcii
					vm.vykonajInstrukcie(vitaz, init);

					System.out.println("Koniec vypisu\n\n\n");

					// ukonncenie evolucie
					i = pocetGeneracii;
				}

				// pocitanie fitness
				countFitness(jedince.get(j));

				if (najdeneRiesenie == true && fitnesVitaza == 0)
					fitnesVitaza = jedince.get(j).fitnessHodnota;

				fitnesy[j] = jedince.get(j).fitnessHodnota;
			}

			// triedenie pola s fitness hodnotami
			Arrays.sort(fitnesy);

			// vypocet medianu
			if (fitnesy.length % 2 == 0)
				median = (int) ((double) fitnesy[fitnesy.length / 2] + (double) fitnesy[fitnesy.length / 2 - 1]) / 2;
			else
				median = fitnesy[fitnesy.length / 2];
			mediany.add(median);
			// vypocet maximalnej fitness
			maxy.add(fitnesy[fitnesy.length - 1]);

			// ak nenasli sme riesenie doteraz
			if (i >= pocetGeneracii - 1)
				break;

			// krizenie jedincov
			jedince = krizenie.krizenie(jedince, init, elitarizmus,
					elitarizmusPocet);

			// mutacia jedincov
			mutacia(jedince);

		}

		// vypis pre median a maximum fitness
		for (int i = 0; i < mediany.size(); i++)
			System.out.println("Generacia: " + i + ", median: "
					+ mediany.get(i) + ", maximum: " + maxy.get(i));

		if (najdeneRiesenie == true)
			System.out.println("Riesenie sme nasli v generacii: "
					+ generaciaRisenia + ", Fitnes vitaza: " + fitnesVitaza);

		// vypis medianu a maxima do suboru
		PrintWriter writer;
		try {
			writer = new PrintWriter("mediany.txt", "UTF-8");

			for (int i = 0; i < mediany.size(); i++)
				writer.println(mediany.get(i));
			writer.close();
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		try {
			writer = new PrintWriter("maxy.txt", "UTF-8");
			for (int i = 0; i < mediany.size(); i++)
				writer.println(maxy.get(i));
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}
}
