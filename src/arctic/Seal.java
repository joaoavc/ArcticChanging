package arctic;

import java.util.Random;
import ecosystem.Animal;
import ecosystem.Patch;
import ecosystem.PreyPredator;
import ecosystem.Terrain;
import physics.Body;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import tools.SubPlot;

public class Seal extends PreyPredator {

private PImage sealImage;
private int breath = 100;

	
	public Seal(PVector pos, float mass, float radius, int color, PApplet parent, SubPlot plt, Animal favoritePrey, 
			Animal myPradator, Sex sex) {
		super(pos, mass, radius, color, parent, plt, favoritePrey, myPradator, sex);
		energy = DNASeal.SEAL_ENERGY;
		super.dna = new DNASeal();
		sealImage = parent.loadImage("seal.png");
		sealImage.resize(30,20);
	}

	public Seal(Seal seal, boolean mutate, PApplet parent, SubPlot plt) {
		super(seal, mutate, parent, plt);
		super.dna = new DNASeal(seal.getDNA(), mutate);
		energy = DNASeal.SEAL_ENERGY;
		sealImage = parent.loadImage("seal.png");
		sealImage.resize(30,20);
	}
	
	@Override 
	public Animal reproduce(Terrain terrain) {
		Patch patch = (Patch)terrain.world2Cell(pos.x, pos.y);
		Random random = new Random();
	    boolean mutate = random.nextBoolean();
		Seal child = null;
		for(Body seal : ((ArcticPopulation)population).getSeals()) {
			if(this.getSex() != ((Seal)seal).getSex()) {
				Patch otherPatch = (Patch)terrain.world2Cell(((Seal)seal).pos.x, ((Seal)seal).pos.y);
				if(patch.equals(otherPatch)) {
					if (this.energy > DNASeal.SEAL_ENERGY_TO_REPRODUCE && ((Seal)seal).energy > DNASeal.SEAL_ENERGY_TO_REPRODUCE) {
						this.energy -=  DNASeal.SEAL_ENERGY_TO_REPRODUCE ;
						((Seal)seal).energy -= DNASeal.SEAL_ENERGY_TO_REPRODUCE;
						child = new Seal(this, mutate, this.parent, this.plt);
						child.setPos(((ArcticPopulation) this.population).randomPos(parent));
						if(mutate) child.mutateBehaviors();
					}
				}
			}
		}
		return child;
	}

	@Override
	public void display(PApplet p, SubPlot plt) {
		p.pushMatrix();
		float[] pp = plt.getPixelCoord(pos.x, pos.y);
		p.translate(pp[0], pp[1]);
		//p.rotate(-vel.heading());
		p.image(sealImage, -10, -3.5f);
		p.fill(0);
		p.circle(0, 0, 1);
		p.popMatrix();
	}
	
	public void breathing(Arctic arctic) {
		IceCap iceCap = (IceCap) arctic.world2Cell(pos.x, pos.y);
		if(iceCap.getState()== ArcticConstants.PatchType.WATER.ordinal())
			breath--;
		else breath = 100;
		if(breath<0) this.energy = -1;
	}
}
