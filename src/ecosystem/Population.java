package ecosystem;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import tools.SubPlot;

public class Population {
	protected List<Animal> allAnimals;
	private double[] window;
	
	public Population(PApplet parent, SubPlot plt, Terrain terrain) {
		setWindow(plt.getWindow());
		allAnimals = new ArrayList<Animal>();
	}
	
	public void update(float dt, Terrain terrain) {
		move(terrain, dt);
		eat(terrain);
		energyConsumption(dt, terrain);
		//reproduce(terrain);
		die();
	}
	
	private void move(Terrain terrain, float dt) {
		for(Animal a : allAnimals)
			a.applyBehaviors(dt);
	}
	
	private void eat(Terrain terrain) {
		for(Animal a : allAnimals)
			a.eat(terrain);
	}
	
	private void energyConsumption(float dt, Terrain terrain) {
		for(Animal a : allAnimals)
			a.energyConsumption(dt, terrain);
	}
	
	
	private void die() {
		for(int i=allAnimals.size()-1; i>=0; i-- ) {
			Animal a = allAnimals.get(i);
			if(a.die()) {
				allAnimals.remove(a);
			}
		}
	}
	
	private void reproduce(Terrain terrain) {
		for(int i=allAnimals.size()-1; i>=0; i-- ) {
			Animal a = allAnimals.get(i);
			Animal child = a.reproduce(terrain);
			if(child != null)
				allAnimals.add(child);
		}
	}
	
	public List<Animal> getAllAnimals(){
		return this.allAnimals;
	}
	

	public void display(PApplet p, SubPlot plt) {
		for(Animal a : allAnimals)
			a.display(p, plt);
	}
	
	public int getNumAnimals() {
		return allAnimals.size();
	}
	
	public float getMeanMaxSpeed() {
		float sum = 0;
		for(Animal a : allAnimals)
			sum+=a.getDNA().maxSpeed;
		return sum/allAnimals.size();
	}
	
	public float getStdMaxSpeed() {
		float mean = getMeanMaxSpeed();
		float sum = 0;
		for(Animal a:allAnimals)
			sum+=Math.pow(a.getDNA().maxSpeed-mean, 2);
		return (float)Math.sqrt(sum/allAnimals.size());
	}
	
	public float[] getMeanWeights() {
		float[] sums = new float[2];
		for(Animal a : allAnimals) {
			sums[0] += a.getBehaviors().get(0).getWeight();
			sums[1] += a.getBehaviors().get(1).getWeight();
		}
		sums[0] /= allAnimals.size();
		sums[1] /= allAnimals.size();
		return sums;
	}

	public double[] getWindow() {
		return window;
	}

	public void setWindow(double[] window) {
		this.window = window;
	}
}
