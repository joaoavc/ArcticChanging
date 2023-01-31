package arctic;

import ecosystem.Animal;
import ecosystem.PreyPredator;
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
	public void display(PApplet p, SubPlot plt) {
		p.pushMatrix();
		float[] pp = plt.getPixelCoord(pos.x, pos.y);
		p.translate(pp[0], pp[1]);
		p.rotate(-vel.heading());
		p.image(shrimpImage, -10, -3.5f);
		p.fill(0);
		p.circle(0, 0, 1);
		p.popMatrix();
	}
		
}
