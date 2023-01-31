package ecosystem;

import ca.MajorityCA;
import processing.core.PApplet;
import tools.SubPlot;

public class Terrain extends MajorityCA {

	public Terrain(PApplet p, SubPlot plt, int nrows, int ncols, int nstates, int i) {
		super(p, plt, nrows, ncols, nstates, i);
	}

	@Override
	protected void createCells() {
		int minRT = (int) (WorldConstants.REGENERATION_TIME[0]*1000);
		int maxRT = (int) (WorldConstants.REGENERATION_TIME[1]*1000);
		for (int i=0; i<nrows; i++) {
			for(int j=0; j<ncols; j++) {
				int timeToGrow = (int) (minRT+(maxRT-minRT)*Math.random());
				cells[i][j] = new Patch(this, i, j, timeToGrow);
			}
		}
		setMooreNeighbors();
	}
	
	
	/*
	public void regenerate() {
		for(int i= 0; i<nrows; i++) {
			for(int j = 0; j<ncols; j++) {
				((Patch)cells[i][j]).regenerate();
			}
		}
	}
	*/
}