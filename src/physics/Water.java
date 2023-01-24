package physics;

import tools.SubPlot;

import processing.core.PApplet;

public class Water extends Fluid {
	
	private float waterHeight;
	private int color;

	protected Water(float waterHeight, int color) {
		super(1000f);
		this.waterHeight = waterHeight;
		this.color = color;
	}
	
	public boolean isInside(Mover m) {
		
		return (m.getPos().y  <  waterHeight);
		
	}
	
	public void display(PApplet p, SubPlot plt) {
		p.pushStyle();
		float [] bb = plt.getBox(0, 0,plt.getWindow()[1], waterHeight);
		p.noStroke();
		p.fill(color);
		p.rect(bb[0], bb[1], bb[2], bb[3]);
		p.popStyle();
	}
	
	

}
