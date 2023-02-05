package ecosystem;

import java.util.ArrayList;
import java.util.List;

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
		energy+=1;
	}
	
	public void refreshPreyVision(List<Body> shrimps) {
		ArrayList<Body> trackingAnimals = new ArrayList<Body>();
		trackingAnimals.addAll(shrimps); 
		Eye eye = new Eye(this, trackingAnimals);
		this.setEye(eye);
	}
}