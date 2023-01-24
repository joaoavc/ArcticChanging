package artic;

import ecosystem.Animal;
import ecosystem.Predator;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import tools.SubPlot;

public class PolarBear extends Predator {
	
	private PImage polarBearImage;
	
	public PolarBear(PVector pos, float mass, float radius, int color, PApplet parent, SubPlot plt, Animal favoritePrey, Sex sex) {
		super(pos, mass, radius, color, parent, plt, favoritePrey, sex);
		energy = DNAPolarBear.POLAR_BEAR_ENERGY;
		super.dna = new DNAPolarBear();
		polarBearImage = parent.loadImage("bear.png");
		polarBearImage.resize(30,15);
	}

	public PolarBear(PolarBear polarBear, boolean mutate, PApplet parent, SubPlot plt) {
		super(polarBear, mutate, parent, plt);
		energy = DNAPolarBear.POLAR_BEAR_ENERGY;
		super.dna = new DNAPolarBear(polarBear.getDNA(), mutate);
		polarBearImage = parent.loadImage("bear.png");
		polarBearImage.resize(30,15);
	}

	@Override
	public void display(PApplet p, SubPlot plt) {
		p.pushMatrix();
		float[] pp = plt.getPixelCoord(pos.x, pos.y);
		p.translate(pp[0], pp[1]);
		p.rotate(-vel.heading());
		p.image(polarBearImage, -10, -3.5f);
		p.fill(0);
		p.circle(0, 0, 1);
		p.popMatrix();
	}
}
