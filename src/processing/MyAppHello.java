package processing;

import processing.core.PApplet;

public class MyAppHello implements IProcessingApp {
	
	@Override
	public void setup(PApplet p) {
		
		
	}
	
	private int sw = 1;
	
	
    @Override
	public void draw(PApplet p, float dt) {
		p.background(255);
		p.fill(255, 203, 219);
		p.stroke(255, 0, 0);
		p.strokeWeight(sw);
		p.circle(p.mouseX, p.mouseY, 100);
		p.fill(0, 0, 255, 64);
		p.rect(400, 300, 100, 100);
		System.out.println(1/dt);
		
		
		
	}
	@Override
	public void keyPressed(PApplet p) {
		
	}
	@Override
	public void mousePressed(PApplet p) {
		sw ++;
		
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
