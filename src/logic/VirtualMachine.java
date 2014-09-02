package logic;

import java.util.Arrays;

import entity.Jedinec;

public class VirtualMachine {

	public boolean vypisInstrukcie(int b, Jedinec jedinec, InitObject init) {

		if ((b & 4) == 4) {

			System.out.print("HORE ");
			// System.out.println(jedinec.position);
			if (jedinec.aktPozicia - init.mapSizeX < 0)
				return false;
			else
				jedinec.aktPozicia = jedinec.aktPozicia - init.mapSizeX;
			// System.out.println(jedinec.position);

		} else if ((b & 2) == 2) {
			System.out.print("DOLE ");
			// System.out.println(jedinec.position);

			if (jedinec.aktPozicia + init.mapSizeX >= (init.mapSizeX * init.mapSizeY))
				return false;
			else
				jedinec.aktPozicia = jedinec.aktPozicia + init.mapSizeX;
			// System.out.println(jedinec.position);

		} else if ((b & 1) == 1) {
			System.out.print("VPRAVO ");
			// System.out.println(jedinec.position);

			if (jedinec.aktPozicia % init.mapSizeX == init.mapSizeX - 1)
				return false;
			else
				jedinec.aktPozicia = jedinec.aktPozicia + 1;
			// System.out.println(jedinec.position);

		} else {
			System.out.print("VLAVO ");
			// System.out.println(jedinec.position);

			if (jedinec.aktPozicia % init.mapSizeX == 0)
				return false;
			else
				jedinec.aktPozicia = jedinec.aktPozicia - 1;
			// System.out.println(jedinec.position);

		}

		return true;

	}

	public boolean bezVypisuInstrukcie(int b, Jedinec jedinec, InitObject init) {

		if ((b & 4) == 4) {

			// System.out.print("HORE ");
			// System.out.println(jedinec.position);
			if (jedinec.aktPozicia - init.mapSizeX < 0)
				return false;
			else
				jedinec.aktPozicia = jedinec.aktPozicia - init.mapSizeX;
			// System.out.println(jedinec.position);

		} else if ((b & 2) == 2) {
			// System.out.print("DOLE ");
			// System.out.println(jedinec.position);

			if (jedinec.aktPozicia + init.mapSizeX >= (init.mapSizeX * init.mapSizeY))
				return false;
			else
				jedinec.aktPozicia = jedinec.aktPozicia + init.mapSizeX;
			// System.out.println(jedinec.position);

		} else if ((b & 1) == 1) {
			// System.out.print("VPRAVO ");
			// System.out.println(jedinec.position);

			if (jedinec.aktPozicia % init.mapSizeX == init.mapSizeX - 1)
				return false;
			else
				jedinec.aktPozicia = jedinec.aktPozicia + 1;
			// System.out.println(jedinec.position);

		} else {
			// System.out.print("VLAVO ");
			// System.out.println(jedinec.position);

			if (jedinec.aktPozicia % init.mapSizeX == 0)
				return false;
			else
				jedinec.aktPozicia = jedinec.aktPozicia - 1;
			// System.out.println(jedinec.position);

		}

		return true;

	}

	public void vykonajInstrukcie(Jedinec jedinec, InitObject init) {

		int[] pamat = new int[jedinec.instrukcie.length];
		System.arraycopy(jedinec.instrukcie, 0, pamat, 0,
				jedinec.instrukcie.length);

		int i = 0;
		final int limitPoctuInsturkcii = 500;

		while (i < pamat.length
				&& jedinec.pocetVykonanychInstrukcii < limitPoctuInsturkcii) {

			if ((pamat[i] & 192) < 64) {
				if ((pamat[(pamat[i] & 63)] & 255) == 255)
					pamat[(pamat[i] & 63)] = 0;
				else
					pamat[(pamat[i] & 63)]++;
				// System.out.println("inkrement");
			} else if ((pamat[i] & 64) == 64 && (pamat[i] & 128) == 0) {
				if (pamat[(pamat[i] & 63)] == 0)
					pamat[(pamat[i] & 63)] = 255;
				else
					pamat[(pamat[i] & 63)]--;
				// System.out.println("dekrement");
			} else if ((pamat[i] & 192) == 192) {
				if (vypisInstrukcie(pamat[i], jedinec, init) == false) {
					System.out.println("\nVysiel som mimo mapu");
					break;
				}
				if (init.poklady.contains(jedinec.aktPozicia) == true
						&& !jedinec.najdenePoklady.contains(jedinec.aktPozicia)) {
					jedinec.najdenePoklady.add(jedinec.aktPozicia);
					System.out.println("\nNasiel som poklad");
				}

				if (init.pocetHladanychPokladov == jedinec.najdenePoklady
						.size()) {
					// System.out.println("Vyhrali sme, mame vsetky poklady");
					break;
				}

			} else if ((pamat[i] & 128) == 128) {
				i = pamat[i] & 63;
				// System.out.println("skok");
			}

			i++;

			jedinec.pocetVykonanychInstrukcii++;

		}

	}

	public boolean run(Jedinec jedinec, InitObject init) {

		int[] pamat = Arrays.copyOfRange(jedinec.instrukcie, 0,
				jedinec.instrukcie.length);

		int i = 0;
		final int limitPoctuInsturkcii = 500;

		while (i < pamat.length
				&& jedinec.pocetVykonanychInstrukcii < limitPoctuInsturkcii) {

			if ((pamat[i] & 192) < 64) {
				if ((pamat[(pamat[i] & 63)] & 255) == 255)
					pamat[(pamat[i] & 63)] = 0;
				else
					pamat[(pamat[i] & 63)]++;
				// System.out.println("inkrement");
			} else if ((pamat[i] & 64) == 64 && (pamat[i] & 128) == 0) {
				if (pamat[(pamat[i] & 63)] == 0)
					pamat[(pamat[i] & 63)] = 255;
				else
					pamat[(pamat[i] & 63)]--;
				// System.out.println("dekrement");
			} else if ((pamat[i] & 192) == 192) {
				if (bezVypisuInstrukcie(pamat[i], jedinec, init) == false) {
					// System.out.println("Vysiel som mimo mapu");
					jedinec.vysielMimoMapy = true;
					break;
				}
				if (init.poklady.contains(jedinec.aktPozicia) == true
						&& !jedinec.najdenePoklady.contains(jedinec.aktPozicia)) {
					jedinec.najdenePoklady.add(jedinec.aktPozicia);
				}

				if (init.pocetHladanychPokladov == jedinec.najdenePoklady
						.size()) {
					// System.out.println("Vyhrali sme, mame vsetky poklady");
					return true;
				}

			} else if ((pamat[i] & 128) == 128) {
				i = pamat[i] & 63;
				// System.out.println("skok");
			}

			i++;

			jedinec.pocetVykonanychInstrukcii++;

		}

		return false;

	}

}
