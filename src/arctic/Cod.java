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

public class Cod extends PreyPredator {

private PImage codImage;
	
	public Cod(PVector pos, float mass, float radius, int color, PApplet parent, SubPlot plt, Animal favoritePrey, Animal myPradator, Sex sex) {
		super(pos, mass, radius, color, parent, plt, favoritePrey, myPradator, sex);
		energy = DNACod.COD_ENERGY;
		super.dna = new DNACod();
		codImage = parent.loadImage("fish.png");
		codImage.resize(30,15);
	}

	public Cod(Cod cod, boolean mutate, PApplet parent, SubPlot plt) {
		super(cod, mutate, parent, plt);
		energy = DNACod.COD_ENERGY;
		super.dna = new DNACod(cod.getDNA(), mutate);
		codImage = parent.loadImage("fish.png");
		codImage.resize(30,15);
	}
	
	@Override 
	public Animal reproduce(Terrain terrain) {
		Patch patch = (Patch)terrain.world2Cell(pos.x, pos.y);
		Random random = new Random();
	    boolean mutate = random.nextBoolean();
		Animal child = null;
		for(Body cod : ((ArcticPopulation)population).getCods()) {
			if(this.getSex() != ((Cod)cod).getSex()) {
				Patch otherPatch = (Patch)terrain.world2Cell(((Cod)cod).pos.x, ((Cod)cod).pos.y);
				if(patch.equals(otherPatch)) {
					if (this.energy > DNACod.COD_ENERGY_TO_REPRODUCE &&
							((Cod)cod).energy > DNACod.COD_ENERGY_TO_REPRODUCE) {
						this.energy -=  DNACod.COD_ENERGY_TO_REPRODUCE;
						((Cod)cod).energy -= DNACod.COD_ENERGY_TO_REPRODUCE;
						child = new Cod(this, mutate, this.parent, this.plt);
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
		p.image(codImage, -10, -3.5f);
		p.fill(0);
		p.circle(0, 0, 1);
		p.popMatrix();
	}
		
}
