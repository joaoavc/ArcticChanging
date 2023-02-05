package ecosystem;


import aa.Behavior;
import aa.Boid;
import aa.Eye;
import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

public abstract class Animal extends Boid implements IAnimal {
	
	public enum Sex {
		MASCULINE, 
		FEMININE
	}
	
	protected float energy;
	protected Sex sex;
	protected Population population;

	protected Animal(PVector pos, float mass, float radius, int color, PApplet parent, SubPlot plt, Sex sex) {
		super(pos, mass, radius, color, parent, plt);
		this.sex = sex;
	}
	
	protected Animal(Animal a, boolean mutate, PApplet parent, SubPlot plt) {
		super(a.pos, a.mass, a.radius, a.color, parent, plt);
		for(Behavior b : a.behaviors) this.addBehavior(b);
		eye = new Eye(this);
		int rndNumber = (int)parent.random(0, 2);
		this.sex = (rndNumber>0) ? Sex.MASCULINE : Sex.FEMININE;
	}
	
	@Override
	public void energyConsumption(float dt, Terrain terrain) {	
		energy -= 0.5;
		energy  -= mass*Math.pow(vel.mag(), 2)*dt;
	}

	@Override
	public boolean die() {
		return(energy < 0);
	}
	
	public void dead() {
		energy = -1;
	}
	
	public void setPopulation(Population population) {
		this.population = population;
	}
	
	public Patch getLocation(Terrain terrain) {
		Patch patch = (Patch)terrain.world2Cell(pos.x, pos.y);
		return patch;
	}
	
	public Sex getSex() {
		return this.sex;
	}
			
			
}