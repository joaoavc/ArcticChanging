package artic;

import ecosystem.Animal;
import ecosystem.PreyPredator;
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
		codImage = parent.loadImage("cod.png");
		codImage.resize(30,15);
	}

	public Cod(Cod cod, boolean mutate, PApplet parent, SubPlot plt) {
		super(cod, mutate, parent, plt);
		energy = DNACod.COD_ENERGY;
		super.dna = new DNACod(cod.getDNA(), mutate);
		codImage = parent.loadImage("cod.png");
		codImage.resize(30,15);
	}

	@Override
	public void display(PApplet p, SubPlot plt) {
		p.pushMatrix();
		float[] pp = plt.getPixelCoord(pos.x, pos.y);
		p.translate(pp[0], pp[1]);
		p.rotate(-vel.heading());
		p.image(codImage, -10, -3.5f);
		p.fill(0);
		p.circle(0, 0, 1);
		p.popMatrix();
	}
		
}
