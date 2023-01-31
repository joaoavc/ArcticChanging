package ecosystem;

import java.util.ArrayList;
import java.util.List;

import aa.Behavior;
import aa.Evade;
import aa.Eye;
import physics.Body;
import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

public abstract class Prey extends Animal{
	
	protected Animal myPredator;
	
	public Prey(PVector pos, float mass, float radius, int color, PApplet parent, SubPlot plt, Animal myPredator, Sex sex) {
		super(pos, mass, radius, color, parent, plt, sex);
		this.myPredator = myPredator;
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
	
	public void refreshPreyVision(List<Body> shrimps) {
		ArrayList<Body> trackingAnimals = new ArrayList<Body>();
		trackingAnimals.addAll(shrimps); 
		Eye eye = new Eye(this, trackingAnimals);
		this.setEye(eye);
	}
}