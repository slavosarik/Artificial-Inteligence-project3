package genetika;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import logic.InitObject;
import entity.Jedinec;
import entity.JedinecComparatorMax;

public class KrizenieTurnaj extends Krizenie implements KrizenieInterface {

	@Override
	public List<Jedinec> krizenie(List<Jedinec> jedince, InitObject init,
			boolean elitarizmus, int elitarizmusPocet) {

		int velkostSkupin = 4;

		// pole pre nove jedince
		List<Jedinec> noveJedince = new ArrayList<>();
		List<Jedinec> elity = new ArrayList<>();

		// elitarstvo
		if (elitarizmus == true) {
			Collections.sort(jedince, new JedinecComparatorMax());

			elity = new ArrayList<>(jedince.subList(0, elitarizmusPocet));

			for (int j = 0; j < elitarizmusPocet; j++)
				jedince.remove(0);

		}

		// rozhadzat nahodne jedince pre turnaj
		Collections.shuffle(jedince);

		Jedinec rodic1;
		Jedinec rodic2 = null;

		// prebiehaju turnaje jedincov
		for (int i = 0; i < jedince.size(); i = i + velkostSkupin) {

			int indexFrom = i;
			int indexTo = i + velkostSkupin;
			if (indexTo > jedince.size())
				indexTo = i + jedince.size() % velkostSkupin;

			List<Jedinec> pomocnePole = new ArrayList<Jedinec>(jedince.subList(
					indexFrom, indexTo));

			// vytriedim vitazov
			Collections.sort(pomocnePole, new JedinecComparatorMax());

			// prekrizime vitazov, porazenych nahradime novou skrizenou
			// generaciou

			rodic1 = new Jedinec(pomocnePole.get(0), init);
			noveJedince.add(new Jedinec(rodic1, init));

			if (pomocnePole.size() > 1) {
				rodic2 = new Jedinec(pomocnePole.get(1), init);
				noveJedince.add(new Jedinec(rodic2, init));
			}

			if (pomocnePole.size() > 2)
				// potomok1
				noveJedince.add(new Jedinec(init, krizeniePrvyPotomok(
						rodic1.instrukcie, rodic2.instrukcie)));

			if (pomocnePole.size() > 3)
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
