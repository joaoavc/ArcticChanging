package ecosystem;

import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

public abstract class Prey extends Animal{
	
	public Prey(PVector pos, float mass, float radius, int color, PApplet parent, SubPlot plt, Sex sex) {
		super(pos, mass, radius, color, parent, plt, sex);
	}
	
	public Prey(Prey prey, boolean mutate, PApplet parent, SubPlot plt) {
		super(prey, mutate, parent, plt);
	}
	
	@Override
	public void eat(Terrain terrain) {
		Patch patch = (Patch)terrain.world2Cell(pos.x, pos.y);
		if(patch.getState() == WorldConstants.PatchType.FOOD.ordinal()) {
			energy+=WorldConstants.ENERGY_FROM_PLANT;
			patch.setFertile();
		}
	}
}