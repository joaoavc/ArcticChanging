package ecosystem;

import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

public abstract class Predator extends Animal {
	
	private Animal favoritePrey;
	private Population population;

	public Predator(PVector pos, float mass, float radius, int color, PApplet parent, SubPlot plt, Animal favoritePrey, Sex sex) {
		super(pos, mass, radius, color, parent, plt, sex);
		this.favoritePrey = favoritePrey;
	}
	
	public Predator(Predator predator, boolean mutate, PApplet parent, SubPlot plt) {
		super(predator, mutate, parent, plt);
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
