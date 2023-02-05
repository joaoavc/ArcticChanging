package ecosystem;

import java.util.ArrayList;
import java.util.List;

import aa.Eye;
import arctic.Arctic;
import physics.Body;
import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

public abstract class Predator extends Animal {
	
	protected Animal favoritePrey;

	public Predator(PVector pos, float mass, float radius, int color, PApplet parent, SubPlot plt, Animal favoritePrey, Sex sex) {
		super(pos, mass, radius, color, parent, plt, sex);
		this.favoritePrey = favoritePrey;
	}
	
	public Predator(Predator predator, boolean mutate, PApplet parent, SubPlot plt) {
		super(predator, mutate, parent, plt);
		this.favoritePrey = predator.favoritePrey;
		this.population = predator.population;
	}

	public void eat(Terrain terrain) {	
		if(favoritePrey == null) return;
		Patch patch = (Patch)terrain.world2Cell(pos.x, pos.y);
		for(Animal animal : population.getAllAnimals()) {
			if (animal.getClass().equals(favoritePrey.getClass())) {
				if(animal.getLocation(terrain).equals(patch)) {
					energy+=animal.energy;
					animal.energy = -1;
				}
			}
		}
	}
	
	public void refreshPredatorVision(List<Body> pradators, Terrain terrain) {
		ArrayList<Body> tracking = new ArrayList<Body>();
		tracking.addAll(pradators); 
		tracking.addAll(((Arctic) terrain).getIce());
		Eye eye = new Eye(this, tracking);
		this.setEye(eye);
	}
}
