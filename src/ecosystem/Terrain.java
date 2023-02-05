package ecosystem;

import ca.MajorityCA;
import processing.core.PApplet;
import tools.SubPlot;

public abstract class Terrain extends MajorityCA {

	public Terrain(PApplet p, SubPlot plt, int nrows, int ncols, int nstates, int i) {
		super(p, plt, nrows, ncols, nstates, i);
	}

	@Override
	protected abstract void createCells();
	
}