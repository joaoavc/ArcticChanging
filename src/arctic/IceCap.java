package arctic;

import ca.CellularAutomata;
import ecosystem.Patch;

import java.util.Random;

import arctic.ArcticConstants.PatchType;
import processing.core.PApplet;

public class IceCap extends Patch {

	private float temperatureChange;
	public IceCap(CellularAutomata ca, int row, int col, PApplet p) {
		super(ca, row, col);
		float min = -12;
		float max = 0;
		Random r = new Random();
		float random = min + r.nextFloat() * (max - min);
		temperatureChange = random;
	}
	
	public void changeWaterState(PApplet p, float temperature) {
		if(temperature > temperatureChange) {
			if(getState()==PatchType.ICE.ordinal()) {
				setState(PatchType.WATER.ordinal());
			}
		}
	}
	
	


}
