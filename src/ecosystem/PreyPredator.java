package ecosystem;

import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

public abstract class PreyPredator extends Animal {
	
	private Animal favoritePrey;
	@SuppressWarnings("unused")
	private Animal myPredator;
	private Population population;

	public PreyPredator(PVector pos, float mass, float radius, int color, PApplet parent, SubPlot plt, Animal favoritePrey, Animal myPredator, Sex sex) {
		super(pos, mass, radius, color, parent, plt, sex);
		this.favoritePrey = favoritePrey;
		this.myPredator = myPredator;
	}
	
	public PreyPredator(PreyPredator prey, boolean mutate, PApplet parent, SubPlot plt) {
		super(prey, mutate, parent, plt);
	}

	public void eat(Terrain terrain) {	
		if(favoritePrey == null) return;
		Patch patch = (Patch)terrain.world2Cell(pos.x, pos.y);
		for(Animal animal : population.getAllAnimals()) {
			if (animal.getClass().equals(favoritePrey.getClass())) {
				if(animal.getLocation(terrain).equals(patch)) {
					energy+=75;
					animal.energy = -1;
				}
			}
		}
	}

}
