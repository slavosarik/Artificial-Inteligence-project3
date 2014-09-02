package genetika;

import java.util.List;

import logic.InitObject;
import entity.Jedinec;

public interface KrizenieInterface {

	public List<Jedinec> krizenie(List<Jedinec> jedince, InitObject init, boolean elitarizmus, int elitarizmusPocet);

}
