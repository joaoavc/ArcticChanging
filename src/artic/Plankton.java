package artic;

import ecosystem.Prey;
import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

public class Plankton extends Prey {

	public Plankton(PVector pos, float mass, float radius, int color, PApplet parent, SubPlot plt, Sex sex) {
		super(pos, mass, radius, color, parent, plt, sex);
		energy = DNAPlankton.PLANKTON_ENERGY;
		super.dna = new DNAPlankton();
	}

	public Plankton(Plankton plankton, boolean mutate, PApplet parent, SubPlot plt) {
		super(plankton, mutate, parent, plt);
		energy = DNAPlankton.PLANKTON_ENERGY;
		super.dna = new DNAPlankton(plankton.getDNA(), mutate);
	}
}
