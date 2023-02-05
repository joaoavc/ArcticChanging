package arctic;

import java.util.Random;


import ecosystem.Animal;
import ecosystem.Prey;
import ecosystem.Terrain;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import tools.SubPlot;

public class Plankton extends Prey {
	

	private PImage planktonImage;
	public Plankton(PVector pos, float mass, float radius, int color, PApplet parent, SubPlot plt, Animal myPredator, Sex sex) {
		super(pos, mass, radius, color, parent, plt, myPredator, sex);
		energy = DNAPlankton.PLANKTON_ENERGY;
		super.dna = new DNAPlankton();
		planktonImage = parent.loadImage("plankton.png");
		planktonImage.resize(5,10);
	}

	public Plankton(Plankton plankton, boolean mutate, PApplet parent, SubPlot plt) {
		super(plankton, mutate, parent, plt);
		energy = DNAPlankton.PLANKTON_ENERGY;
		super.dna = new DNAPlankton(plankton.getDNA(), mutate);
		planktonImage = parent.loadImage("plankton.png");
		planktonImage.resize(5,10);
	}

	@Override
	public Animal reproduce(Terrain terrain) {
		Animal child = null;
		Random random = new Random();
	    boolean mutate = random.nextBoolean();
	    float fertile = parent.random(0, 1);
	    if(fertile > 0.99f) {
			if(energy > DNAPlankton.PLANKTON_ENERGY_TO_REPRODUCE){
				energy -= DNAPlankton.PLANKTON_ENERGY_TO_REPRODUCE;
				child = new Plankton(this, mutate, parent, this.plt);
				if(mutate) child.mutateBehaviors();
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
		p.image(planktonImage, -10, -3.5f);
		p.fill(0);
		p.circle(0, 0, 1);
		p.popMatrix();
	}
}
