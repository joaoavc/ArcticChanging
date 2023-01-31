package ecosystem;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;
import aa.Behavior;
import aa.Boid;
import aa.DNA;
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
		if(a.eye != null) eye = new Eye(this, a.eye);
		dna = new DNA(a.dna, mutate);
		int rndNumber = (int)parent.random(0, 2);
		this.sex = (rndNumber>0) ? Sex.MASCULINE : Sex.FEMININE;
	}
	
	@Override
	public void energyConsumption(float dt, Terrain terrain) {	
		energy -= dt;
		energy  -= mass*Math.pow(vel.mag(), 2)*dt;
		Patch patch = (Patch) terrain.world2Cell(pos.x, pos.y);
		if(patch.getState() == WorldConstants.PatchType.OBSTACLE.ordinal()) {
			energy-=50*dt;
		}
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
	
	@Override 
	public Animal reproduce(Terrain terrain) {
		Patch patch = (Patch)terrain.world2Cell(pos.x, pos.y);
		Random random = new Random();
	    boolean mutate = random.nextBoolean();
		Animal child = null;
		for(Animal animal : population.getAllAnimals()) {
			if (this.getClass().equals(animal.getClass())) {
				if(this.getSex() != animal.getSex()) {
					Patch otherPatch = (Patch)terrain.world2Cell(animal.pos.x, animal.pos.y);
					if(patch.equals(otherPatch)) {
						if (this.energy > 5 && animal.energy > 5) {
							try {
								child = this.getClass().getConstructor().newInstance();
							} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
									| InvocationTargetException | NoSuchMethodException | SecurityException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
		if(mutate) child.mutateBehaviors();
		return child;
	}
	
	public Sex getSex() {
		return this.sex;
	}
			
			
}