package ecosystem;

import java.util.ArrayList;
import java.util.List;

import aa.Behavior;
import aa.Evade;
import aa.Eye;
import aa.Persuit;
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
	
	public void evade() {
		ArrayList<Behavior> behaviorsToRemove;
		behaviorsToRemove = new ArrayList<Behavior>();
		for(Behavior behavior : this.getBehaviors()) {
			if(behavior instanceof Evade) behaviorsToRemove.add(behavior);
		}
		this.getBehaviors().removeAll(behaviorsToRemove);
		this.getEye().look();
		if(this.getEye().getNearSight().size() > 0) {
			Body body = this.getEye().getNearSight().get(0);
			if (body.getClass().equals(myPredator.getClass())) {
				this.getEye().setTarget(body);
				this.addBehavior(new Evade(5));
			}	
		}
	}
	
	public void pursuit() {
		ArrayList<Behavior> behaviorsToRemove;
		behaviorsToRemove = new ArrayList<Behavior>();
		for(Behavior behavior : this.getBehaviors()) {
			if(behavior instanceof Persuit) behaviorsToRemove.add(behavior);
		}
		this.getBehaviors().removeAll(behaviorsToRemove);
		this.getEye().look();
		if(this.getEye().getNearSight().size() > 0) {
			Body body = this.getEye().getNearSight().get(0);
			if (body.getClass().equals(favoritePrey.getClass())) {
				this.getEye().setTarget(body);
				this.addBehavior(new Persuit(5));
			}	
		}
	}
	
	public void refreshPreyPredatorVision(List<Body> cods, List<Body> polarBears) {
		ArrayList<Body> trackingAnimals = new ArrayList<Body>();
		trackingAnimals.addAll(cods); 
		trackingAnimals.addAll(polarBears); 
		Eye eye = new Eye(this, trackingAnimals);
		this.setEye(eye);
	}

}
