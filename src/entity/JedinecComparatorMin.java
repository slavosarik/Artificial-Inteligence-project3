package entity;

import java.util.Comparator;

public class JedinecComparatorMin implements Comparator<Jedinec> {

	@Override
	public int compare(Jedinec jedinec1, Jedinec jedinec2) {

		if (jedinec1.fitnessHodnota < jedinec2.fitnessHodnota) {
			return -1;
		}
		if (jedinec1.fitnessHodnota > jedinec2.fitnessHodnota) {
			return 1;
		}
		return 0;
	}

}
