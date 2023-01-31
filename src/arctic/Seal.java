package arctic;

import ecosystem.Animal;
import ecosystem.PreyPredator;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import tools.SubPlot;

public class Seal extends PreyPredator {

private PImage sealImage;
	
	public Seal(PVector pos, float mass, float radius, int color, PApplet parent, SubPlot plt, Animal favoritePrey, 
			Animal myPradator, Sex sex) {
		super(pos, mass, radius, color, parent, plt, favoritePrey, myPradator, sex);
		energy = DNASeal.SEAL_ENERGY;
		super.dna = new DNASeal();
		sealImage = parent.loadImage("seal.png");
		sealImage.resize(30,15);
	}

	public Seal(Seal seal, boolean mutate, PApplet parent, SubPlot plt) {
		super(seal, mutate, parent, plt);
		energy = DNASeal.SEAL_ENERGY;
		super.dna = new DNASeal(seal.getDNA(), mutate);
		sealImage = parent.loadImage("seal.png");
		sealImage.resize(30,15);
	}

	@Override
	public void display(PApplet p, SubPlot plt) {
		p.pushMatrix();
		float[] pp = plt.getPixelCoord(pos.x, pos.y);
		p.translate(pp[0], pp[1]);
		p.rotate(-vel.heading());
		p.image(sealImage, -10, -3.5f);
		p.fill(0);
		p.circle(0, 0, 1);
		p.popMatrix();
	}
		
}
