package ecosystem;

import java.util.ArrayList;
import java.util.List;

import aa.Eye;
import arctic.Arctic;
import physics.Body;
import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

public abstract class PreyPredator extends Animal {
	
	protected Animal favoritePrey;
	protected Animal myPredator;

	public PreyPredator(PVector pos, float mass, float radius, int color, PApplet parent, SubPlot plt, Animal favoritePrey, 
			Animal myPredator, Sex sex) {
		super(pos, mass, radius, color, parent, plt, sex);
		this.favoritePrey = favoritePrey;
		this.myPredator = myPredator;
	}
	
	public PreyPredator(PreyPredator preyPredator, boolean mutate, PApplet parent, SubPlot plt) {
		super(preyPredator, mutate, parent, plt);
		this.favoritePrey = preyPredator.favoritePrey;
		this.myPredator = preyPredator.myPredator;
		this.population = preyPredator.population;
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
	
	public void refreshPreyPredatorVision(List<Body> preys, List<Body> pradators, Terrain terrain) {
		ArrayList<Body> tracking = new ArrayList<Body>();
		tracking.addAll(preys); 
		tracking.addAll(pradators); 
		tracking.addAll(((Arctic) terrain).getIce());
		tracking.addAll(((Arctic) terrain).getWater());
		Eye eye = new Eye(this, tracking);
		this.setEye(eye);
	}
}
