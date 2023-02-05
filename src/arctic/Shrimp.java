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

public class Shrimp extends PreyPredator {

private PImage shrimpImage;
	
	public Shrimp(PVector pos, float mass, float radius, int color, PApplet parent, SubPlot plt, Animal favoritePrey, Animal myPradator, Sex sex) {
		super(pos, mass, radius, color, parent, plt, favoritePrey, myPradator, sex);
		energy = DNAShrimp.SHRIMP_ENERGY;
		super.dna = new DNAShrimp();
		shrimpImage = parent.loadImage("shrimp.png");
		shrimpImage.resize(30,15);
	}

	public Shrimp(Shrimp shrimp, boolean mutate, PApplet parent, SubPlot plt) {
		super(shrimp, mutate, parent, plt);
		energy = DNAShrimp.SHRIMP_ENERGY;
		super.dna = new DNAShrimp(shrimp.getDNA(), mutate);
		shrimpImage = parent.loadImage("shrimp.png");
		shrimpImage.resize(30,15);
	}
	
	@Override 
	public Animal reproduce(Terrain terrain) {
		Patch patch = (Patch)terrain.world2Cell(pos.x, pos.y);
		Random random = new Random();
	    boolean mutate = random.nextBoolean();
		Animal child = null;
		for(Body shrimp : ((ArcticPopulation)population).getShrimps()) {
			if(this.getSex() != ((Shrimp)shrimp).getSex()) {
				Patch otherPatch = (Patch)terrain.world2Cell(((Shrimp)shrimp).pos.x, ((Shrimp)shrimp).pos.y);
				if(patch.equals(otherPatch)) {
					if (this.energy > DNAShrimp.SHRIMP_ENERGY_TO_REPRODUCE && 
							((Shrimp)shrimp).energy > DNAShrimp.SHRIMP_ENERGY_TO_REPRODUCE) {
						this.energy -=  DNAShrimp.SHRIMP_ENERGY_TO_REPRODUCE;
						((Shrimp)shrimp).energy -= DNAShrimp.SHRIMP_ENERGY_TO_REPRODUCE;
						child = new Shrimp(this, mutate, this.parent, this.plt);
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
		p.image(shrimpImage, -10, -3.5f);
		p.fill(0);
		p.circle(0, 0, 1);
		p.popMatrix();
	}	
}
