package ecosystem;

import java.util.ArrayList;
import java.util.List;

import aa.AvoidObstacle;
import aa.Eye;
import aa.Wander;
import ecosystem.Animal.Sex;
import physics.Body;
import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

public class Population {
	private List<Animal> allAnimals;
	private double[] window;
	private boolean mutate;
	
	public Population(PApplet parent, SubPlot plt, Terrain terrain) {
		window = plt.getWindow();
		allAnimals = new ArrayList<Animal>();
		List<Body> obstacles = terrain.getObstacles();
		for(int i=0;i<WorldConstants.INI_PREY_POPULATION_WITHOUT_MASK;i++) {
			PVector pos = new PVector(parent.random((float)window[0],(float)window[1]),
					parent.random((float)window[2],(float)window[3]));
			int color = parent.color(
				WorldConstants.PREY_COLOR[0],
				WorldConstants.PREY_COLOR[1],
				WorldConstants.PREY_COLOR[2]);
			Sex sex;
			if(parent.random(2)>0) sex = Sex.MASCULINE;
			else sex = Sex.FEMININE;
			Animal a = new Prey(pos, WorldConstants.PREY_MASS, WorldConstants.PREY_SIZE, color, parent, plt, sex);
			a.addBehavior(new Wander(1));
			a.addBehavior(new AvoidObstacle(30));
			Eye eye = new Eye(a, obstacles);
			a.setEye(eye);
			allAnimals.add(a);
		}
	}
	
	public void update(float dt, Terrain terrain) {
		move(terrain, dt);
		eat(terrain);
		energyConsumption(dt, terrain);
		reproduce(mutate);
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
	
	private void reproduce(boolean mutate) {
		for(int i=allAnimals.size()-1; i>=0; i-- ) {
			Animal a = allAnimals.get(i);
			Animal child = a.reproduce(mutate);
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
}
