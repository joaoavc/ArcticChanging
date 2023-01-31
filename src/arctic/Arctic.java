package arctic;

import java.util.ArrayList;
import java.util.List;
import ecosystem.Patch;
import ecosystem.Terrain;
import physics.Body;
import processing.core.PApplet;
import tools.SubPlot;

public class Arctic extends Terrain {

	public Arctic(PApplet p, SubPlot plt) {
		super(p, plt, ArcticConstants.NROWS, ArcticConstants.NCOLS, ArcticConstants.NSTATES, 1);
	}
	
	@Override
	protected void createCells() {
		int minRT = (int) (ArcticConstants.REGENERATION_TIME[0]*1000);
		int maxRT = (int) (ArcticConstants.REGENERATION_TIME[1]*1000);
		for (int i=0; i<nrows; i++) {
			for(int j=0; j<ncols; j++) {
				int timeToGrow = (int) (minRT+(maxRT-minRT)*Math.random());
				cells[i][j] = new Patch(this, i, j, timeToGrow);
			}
		}
		setMooreNeighbors();
	}
	
	public List<Body>getIce(){
		List<Body>bodies = new ArrayList<Body>();
		for(int i=0;i<nrows;i++) {
			for(int j=0;j<ncols;j++) {
				if(cells[i][j].getState() == ArcticConstants.PatchType.ICE.ordinal()) {
					Body b = new Body(this.getCenterCell(i,j));
					bodies.add(b);
				}
			}
		}
		return bodies;
	}

	
	public List<Body>getWater(){
		List<Body>bodies = new ArrayList<Body>();
		for(int i=0;i<nrows;i++) {
			for(int j=0;j<ncols;j++) {
				if(cells[i][j].getState() == ArcticConstants.PatchType.WATER.ordinal()) {
					Body b = new Body(this.getCenterCell(i,j));
					bodies.add(b);
				}
			}
		}
		return bodies;
	}

}
