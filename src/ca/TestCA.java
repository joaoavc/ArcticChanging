package ca;

import processing.IProcessingApp;
import processing.core.PApplet;
import tools.SubPlot;

public class TestCA implements IProcessingApp{
	
	private int nrows = 15;
	private int ncols = 20;
	private int nStates = 4;
	private int radiusNeigh = 5;
	private CellularAutomata ca;
	private SubPlot plt;
	private double[] window = {0, 10, 0, 10};
	private float[] viewport = {0.3f, 0.3f, 0.5f, 0.6f};
	
	@Override
	public void setup(PApplet p) {
		plt = new SubPlot(window, viewport, p.width, p.height);
		ca = new CellularAutomata(p, plt, nrows, ncols, nStates, radiusNeigh);
		ca.initRandom();
	}

	@Override
	public void draw(PApplet p, float dt) {
		ca.display(p);
		
	}

	@Override
	public void keyPressed(PApplet p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(PApplet p) {
		// TODO Auto-generated method stub
		Cell cell = ca.pixel2Cell(p.mouseX, p.mouseY);
		cell.setState(nStates - 1);
		
		Cell[] neigh = cell.getNeighbors();
		for(int i=0; i<neigh.length; i++) {
			neigh[i].setState(nStates-1);
		}
		
	}

	@Override
	public void mouseReleased(PApplet p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(PApplet p) {
		// TODO Auto-generated method stub
		
	}

}
