package processing;

import arctic.ArcticApp;
import processing.core.PApplet;

@SuppressWarnings("unused")
public class ProcessingSetup extends PApplet {
	
	private static  IProcessingApp app;
	private int lastUpdate;
	
	@Override
	public void settings() {
		  size(1060, 640); 
	 }
	
	@Override
	public void setup() {
		app.setup(this);
		lastUpdate = millis();
	}
	
	@Override
	public void draw() {
		int now = millis ();
		float dt = (now-lastUpdate)/1000f;
		lastUpdate = now;
		app.draw(this,  dt);
	}
	
	public void keyPressed() {
		app.keyPressed(this);
	}
	
	public void mousePressed() {
		app.mousePressed(this);
	}
	
	public void mouseReleased() {
		app.mouseReleased(this);
	}
	
	public void mouseDragged() {
		app.mouseDragged(this);
	}
	 
	 public static void main(String[] args) {
		 app = new ArcticApp();
		 PApplet.main("processing.ProcessingSetup"); 
	 }	 
}