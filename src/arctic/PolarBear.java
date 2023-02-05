package arctic;

import java.util.Random;
import ecosystem.Animal;
import ecosystem.Patch;
import ecosystem.Predator;
import ecosystem.Terrain;
import physics.Body;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import tools.SubPlot;

public class PolarBear extends Predator {
	
	private PImage polarBearImage;
	private int breath = 100;
	
	public PolarBear(PVector pos, float mass, float radius, int color, PApplet parent, SubPlot plt, Animal favoritePrey, Sex sex) {
		super(pos, mass, radius, color, parent, plt, favoritePrey, sex);
		energy = DNAPolarBear.POLAR_BEAR_ENERGY;
		super.dna = new DNAPolarBear();
		polarBearImage = parent.loadImage("bear.png");
		polarBearImage.resize(30,20);
	}

	public PolarBear(PolarBear polarBear, boolean mutate, PApplet parent, SubPlot plt) {
		super(polarBear, mutate, parent, plt);
		energy = DNAPolarBear.POLAR_BEAR_ENERGY;
		super.dna = new DNAPolarBear(polarBear.getDNA(), mutate);
		polarBearImage = parent.loadImage("bear.png");
		polarBearImage.resize(30,20);
	}
	
	@Override 
	public Animal reproduce(Terrain terrain) {
		Patch patch = (Patch)terrain.world2Cell(pos.x, pos.y);
		Random random = new Random();
	    boolean mutate = random.nextBoolean();
		Animal child = null;
		for(Body bear : ((ArcticPopulation)population).getPolarBears()) {
			if(this.getSex() != ((PolarBear)bear).getSex()) {
				Patch otherPatch = (Patch)terrain.world2Cell(((PolarBear)bear).pos.x, ((PolarBear)bear).pos.y);
				if(patch.equals(otherPatch)) {
					if (this.energy > DNAPolarBear.POLAR_BEAR_ENERGY_TO_REPRODUCE && 
							((PolarBear)bear).energy > DNAPolarBear.POLAR_BEAR_ENERGY_TO_REPRODUCE) {
						this.energy -=  DNAPolarBear.POLAR_BEAR_ENERGY_TO_REPRODUCE;
						((PolarBear)bear).energy -= DNAPolarBear.POLAR_BEAR_ENERGY_TO_REPRODUCE;
						child = new PolarBear(this, mutate, this.parent, this.plt);
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
		p.image(polarBearImage, -10, -3.5f);
		p.fill(0);
		p.circle(0, 0, 1);
		p.popMatrix();
	}
	
	public void breathing(Arctic arctic) {
		IceCap iceCap = (IceCap) arctic.world2Cell(pos.x, pos.y);
		if(iceCap.getState()== ArcticConstants.PatchType.ICE.ordinal())
			breath--;
		else breath = 100;
		if(breath<0) this.energy = -1;;
	}
}
