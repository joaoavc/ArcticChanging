package arctic;

import java.util.ArrayList;
import java.util.List;
import ecosystem.Terrain;
import physics.Body;
import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

public class Arctic extends Terrain {

	private PApplet papp;
	public Arctic(PApplet p, SubPlot plt) {
		super(p, plt, ArcticConstants.NROWS, ArcticConstants.NCOLS, ArcticConstants.NSTATES, 1);
		papp = p;
	}
	
	@Override
	protected void createCells() {
		for (int i=0; i<nrows; i++) {
			for(int j=0; j<ncols; j++) {
				cells[i][j] = new IceCap(this, i, j, papp);
			}
		}
		setMooreNeighbors();
	}
	
	public void iceCapState(PApplet p, float temperature) {
		for (int i=0; i<nrows; i++) {
			for(int j=0; j<ncols; j++) {
				((IceCap) cells[i][j]).changeWaterState(p, temperature);
			}
		}
	}
	
	public List<Body>getIce(){
		List<Body>iceBodies = new ArrayList<Body>();
		for(int i=0;i<nrows;i++) {
			for(int j=0;j<ncols;j++) {
				if(cells[i][j].getState() == ArcticConstants.PatchType.ICE.ordinal()) {
					IceBody b = new IceBody(this.getCenterCell(i,j));
					iceBodies.add(b);
				}
			}
		}
		return iceBodies;
	}
	
	public boolean inIce(PVector position){
		IceCap cell = (IceCap) this.pixel2Cell(position.x, position.y);
		if(cell.getState() == ArcticConstants.PatchType.ICE.ordinal())
			return true;
		return false;
	}
	
	public boolean inWater(PVector position){
		IceCap cell = (IceCap) this.pixel2Cell(position.x, position.y);
		if(cell.getState() == ArcticConstants.PatchType.WATER.ordinal())
			return true;
		return false;
	}
	
	public List<Body>getWater(){
		List<Body>waterBodies = new ArrayList<Body>();
		for(int i=0;i<nrows;i++) {
			for(int j=0;j<ncols;j++) {
				if(cells[i][j].getState() == ArcticConstants.PatchType.WATER.ordinal()) {
					WaterBody b = new WaterBody(this.getCenterCell(i,j));
					waterBodies.add(b);
				}
			}
		}
		return waterBodies;
	}
}