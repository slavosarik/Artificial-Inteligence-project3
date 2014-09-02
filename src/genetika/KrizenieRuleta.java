package genetika;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import logic.InitObject;
import entity.Jedinec;
import entity.JedinecComparatorMin;

public class KrizenieRuleta extends Krizenie implements KrizenieInterface {

	@Override
	public List<Jedinec> krizenie(List<Jedinec> jedince, InitObject init,
			boolean elitarizmus, int elitarizmusPocet) {

		Random random = new Random();
		int velkostSkupin = 4;

		// list pre nove jedince
		List<Jedinec> noveJedince = new ArrayList<>();
		List<Jedinec> elity = new ArrayList<>();

		// usporiadam si jedince podla ich fitness
		Collections.sort(jedince, new JedinecComparatorMin());

		// elitarstvo? vyberiem zo zoznamu elitnych jedincov, ktory automaticky
		// prejdu do dalsej generacie
		if (elitarizmus == true) {
			// vyber elit
			elity = new ArrayList<>(jedince.subList(jedince.size()
					- elitarizmusPocet, jedince.size()));

			// resetnutie doterajsieho stavu elit + vymazanie z povodneho
			// zoznamu
			for (int j = 0; j < elitarizmusPocet; j++)
				jedince.remove(jedince.size() - 1);
		}

		// sucet znamena sucet poctu prvkov zoznamu - n*(n-1)/2
		int sucet = (jedince.size() * (jedince.size() - 1)) / 2;
		Set<Integer> vybrateJedince = new TreeSet<>();
		Jedinec rodic1, rodic2 = null;
		int zvysok = 0;

		// prebiehaju výbery dvojic metodou vyberu ruletou
		for (int i = 0; i < jedince.size(); i = i + velkostSkupin) {

			int pos1, pos2;
			if (i > jedince.size() - velkostSkupin)
				zvysok = jedince.size() - i;

			// vyber pozicie rodica pomocou diskriminantu
			pos1 = (int) ((-1 + Math
					.sqrt(1 + 4 * 2 * random.nextInt(sucet + 1))) / 2);
			if (vybrateJedince.contains(pos1) == false)
				vybrateJedince.add(pos1);
			else
				while (vybrateJedince.contains(pos1) == true) {
					pos1 = (int) ((-1 + Math.sqrt(1 + 4 * 2 * random
							.nextInt(sucet + 1))) / 2);
				}

			pos2 = (int) ((-1 + Math
					.sqrt(1 + 4 * 2 * random.nextInt(sucet + 1))) / 2);
			if (vybrateJedince.contains(pos2) == false)
				vybrateJedince.add(pos2);
			else
				// keby som vybral toho isteho rodica, tak vyberiem ineho
				while (pos1 == pos2 || vybrateJedince.contains(pos2) == true) {
					pos2 = (int) ((-1 + Math.sqrt(1 + 4 * 2 * random
							.nextInt(sucet + 1))) / 2);
				}

			// vyber rodica
			rodic1 = jedince.get(pos1);
			// rodic1
			noveJedince.add(new Jedinec(rodic1, init));

			if (zvysok == 0 || zvysok > 1) {
				rodic2 = jedince.get(pos2);
				// rodic2
				noveJedince.add(new Jedinec(rodic2, init));
			}

			if (zvysok == 0 || zvysok > 2)
				// potomok1
				noveJedince.add(new Jedinec(init, krizeniePrvyPotomok(
						rodic1.instrukcie, rodic2.instrukcie)));

			if (zvysok == 0 || zvysok > 3)
				// potomok2
				noveJedince.add(new Jedinec(init, krizenieDruhyPotomok(
						rodic1.instrukcie, rodic2.instrukcie)));

		}

		// vynulovanie hodnot a pridanie elitnych jedincov do novej generacie
		if (elitarizmus == true)
			for (int j = 0; j < elitarizmusPocet; j++)
				noveJedince.add(new Jedinec(elity.get(j), init));

		return noveJedince;
	}

}
