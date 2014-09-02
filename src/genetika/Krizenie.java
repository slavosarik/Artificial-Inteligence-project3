package genetika;

public abstract class Krizenie {

	public int[] krizeniePrvyPotomok(int[] instrRodic1, int[] instrRodic2) {

		int[] noveInstrukcie = new int[instrRodic1.length];

		System.arraycopy(instrRodic1, 0, noveInstrukcie, 0,
				(int) (instrRodic1.length / 2));
		System.arraycopy(instrRodic2, instrRodic1.length / 2, noveInstrukcie,
				instrRodic2.length / 2, instrRodic2.length / 2
						+ instrRodic2.length % 2);

		return noveInstrukcie;
	}

	public int[] krizenieDruhyPotomok(int[] instrRodic1, int[] instrRodic2) {

		int[] noveInstrukcie = new int[instrRodic1.length];

		System.arraycopy(instrRodic2, 0, noveInstrukcie, 0,
				(int) (instrRodic2.length / 2));

		System.arraycopy(instrRodic1, instrRodic2.length / 2, noveInstrukcie,
				instrRodic1.length / 2, instrRodic1.length / 2
						+ instrRodic1.length % 2);

		return noveInstrukcie;
	}

}
