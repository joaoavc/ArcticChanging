package arctic;

import java.util.ArrayList;

import ecosystem.WorldConstants;
import processing.IProcessingApp;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import tools.SubPlot;

public class ArcticApp implements IProcessingApp {
	
	private float[] viewport = {0f, 0f, 1f, 1f};
	private static final int NUM_ANIMALS = 5; 
	private SubPlot plt;	
	private Arctic arctic;
	private ArcticPopulation population;
	private PImage startScreen, homeScreen, inputValuesScreen, finalScreen;
	private int appState = 0;
	private int numPolarBears, numSeals, numCods, numShrimps, numPlanktons = 0;
	private ArrayList<String> numPopulation, animalsNames;
	private ArrayList<Boolean> selectedPopulation;
	private ArrayList<Integer> finalResults, intNumPopulation;

	@Override
	public void setup(PApplet p) {
		plt = new SubPlot(ArcticConstants.WINDOW, viewport, p.width, p.height);
		startGUI(p);
	}

	@Override
	public void draw(PApplet p, float dt) {
		switch(appState) {
		  case 0:
			this.startScreen(p);
		    break;
		  case 1:
		    this.homeScreen(p);
		    break;
		  case 2:
			this.helpScreen(p);
			break;
		  case 3:
			this.inputValuesScreen(p);
			break;
		  case 4:
			this.simulationScreen(p, dt);
			break;
		}
	}

	@Override
	public void keyPressed(PApplet p) {
		String num;
		if(appState == 3){
			for(int i=0; i<NUM_ANIMALS; i++) {
				if(selectedPopulation.get(i)) {
					num = numPopulation.get(i);
					int key = Character.getNumericValue(p.key);
					if(key<10 && key>=0) 
						numPopulation.set(i, numPopulation.get(i)+key); 
					if(p.key == PConstants.BACKSPACE && num.length()>0) 
						 numPopulation.set(i, num.substring(0, num.length()-1)); 
				}
			}
		}
	}

	@Override
	public void mousePressed(PApplet p) {
		if(overRect(p, p.width/2-100,p.height-300,200,50) && appState == 0) { 
			appState = 1;
		}
		else if(overRect(p, p.width/2-300,p.height-300,200,50) && appState == 1) { 
			appState = 3;
			defaultValues();
		}
		else if(overRect(p, p.width/2-100,p.height-100,200,50) && appState == 3) { 
			this.startPopulation(p);
			appState = 4;
		}
		for(int i=0;i<NUM_ANIMALS;i++) {
			selectedPopulation.set(i, false);
			if(overRect(p, 80+200*i,p.height/2,100,50)) {
				selectedPopulation.set(i, true);
			}
		}
	}

	@Override
	public void mouseReleased(PApplet p) {}

	@Override
	public void mouseDragged(PApplet p) {}
	
	public void drawButton(PApplet p,float textx,float texty,float x, float y, float width, float height, String text) {
		p.strokeWeight(1);
		if(overRect(p, x, y, width, height)) {
			p.fill(255,120,255,100);
			p.stroke(0);
		}
		else {
			p.fill(255,255,255,200);
			p.stroke(0);
		}
		p.rect(x,y, width, height);
		p.fill(0);
		p.textSize(30);
		p.text(text, textx, texty);
	}
	
	public boolean overRect(PApplet p,float x, float y, float width, float height)  {
		if (p.mouseX >= x && p.mouseX <= x+width && 
			p.mouseY >= y && p.mouseY <= y+height) return true;
		else return false;  
	}
	
	public void startScreen(PApplet p) {
		p.background(startScreen);
		p.textSize(100);
		p.fill(255);
		p.text("Arctic Changing", 150, 275);
		drawButton(p, p.width/2-32, p.height-264, p.width/2-100,p.height-300,200,50, "Start");
	}
	
	public void inputValuesScreen(PApplet p) {
		p.background(inputValuesScreen);
		p.textSize(75);
		p.fill(255);
		p.text("Arctic Changing", 150, 150);
		p.fill(255);
		p.textSize(30);
		p.text("Insert population values",250,225);
		for(int i=0;i<NUM_ANIMALS;i++) {
			p.fill(255,255,255,150);
			p.strokeWeight(2);
			if(selectedPopulation.get(i)) 
				p.stroke(150,0,0);
			else 
				p.stroke(255);
			p.rect(80+200*i,p.height/2,100,50);	
			if(selectedPopulation.get(i)) 
				p.fill(150,0,0);
			else 
				p.fill(255);
			p.textSize(20);
			p.text(animalsNames.get(i),90+200*i,p.height/2-10);
			p.fill(0);
			p.textSize(25);
			p.text(numPopulation.get(i),90+200*i,p.height/2+36);
		}
		drawButton(p,p.width/2-40, p.height-64,p.width/2-100,p.height-100,200,50, "Start");
	}
	
	
	public void homeScreen(PApplet p) {
		p.background(homeScreen);
		p.textSize(75);
		p.fill(0);
		p.text("Arctic Changing", 250, 150);
		p.textSize(45);
		p.text("Home Screen", 325, 200);
		drawButton(p, p.width/2+70, p.height-264, p.width/2,p.height-300,200,50, "Help");
		drawButton(p, p.width/2-275, p.height-264, p.width/2-300,p.height-300,200,50, "Simulation");
	}
	
	public void helpScreen(PApplet p) {
		
	}
	
	public void simulationScreen(PApplet p, float dt) {
		//arctic.regenerate();
		population.update(dt, arctic);
		arctic.display(p);
		population.display(p, plt);	
	}
	
	public void startPopulation(PApplet p){
		arctic = new Arctic(p, plt);
		arctic.setStateColors(getColors(p));
		arctic.initRandomCustom(ArcticConstants.PATCH_TYPE_PROB);
		for(int i=0; i<2; i++) arctic.majorityRule();
		population = new ArcticPopulation(p, plt, arctic, intNumPopulation);
	}
	
	public void resultsScreen(PApplet p) {
		
	}
	
	public void startGUI(PApplet p) {
		startScreen = p.loadImage("arctic_1.jpg");
		startScreen.resize(1060, 640);
		homeScreen = p.loadImage("arctic_2.jpg");
		homeScreen.resize(1060, 640);
		inputValuesScreen = p.loadImage("arctic_3.jpg");
		inputValuesScreen.resize(1060, 640);
		selectedPopulation = new ArrayList<Boolean>();
		numPopulation = new ArrayList<String>();
		for(int i=0;i<NUM_ANIMALS;i++) {
			numPopulation.add("");
			selectedPopulation.add(false);
		}
		animalsNames = new ArrayList<String>();
		animalsNames.add( "Polar Bears");
		animalsNames.add("Seals");
		animalsNames.add("Cods");
		animalsNames.add("Shrimps");
		animalsNames.add("Planktons");
		intNumPopulation = new ArrayList<Integer>();

	}
	
	public void setValuesPopulation() {
		numPopulation.set(0, String.valueOf(ArcticConstants.POLAR_BEAR_POPULATION));
		numPopulation.set(1, String.valueOf(ArcticConstants.SEAL_POPULATION));
		numPopulation.set(2, String.valueOf(ArcticConstants.COD_POPULATION));
		numPopulation.set(3, String.valueOf(ArcticConstants.SHRIMP_POPULATION));
		numPopulation.set(4, String.valueOf(ArcticConstants.PLANKTON_POPULATION));
	}
	
	public void getValuesPopulation() {
		intNumPopulation.set(0, Integer.parseInt(numPopulation.get(0)));
		intNumPopulation.set(1, Integer.parseInt(numPopulation.get(0)));
		intNumPopulation.set(2, Integer.parseInt(numPopulation.get(0)));
		intNumPopulation.set(3, Integer.parseInt(numPopulation.get(0)));
		intNumPopulation.set(4, Integer.parseInt(numPopulation.get(0)));
	}
	
	public void defaultValues() {
		intNumPopulation.add(ArcticConstants.POLAR_BEAR_POPULATION);
		intNumPopulation.add(ArcticConstants.SEAL_POPULATION);
		intNumPopulation.add(ArcticConstants.COD_POPULATION);
		intNumPopulation.add(ArcticConstants.SHRIMP_POPULATION);
		intNumPopulation.add(ArcticConstants.PLANKTON_POPULATION);
	}
	
	private int[] getColors(PApplet p) {
		int[] colors = new int[ArcticConstants.NSTATES];
		for(int i=0; i<ArcticConstants.NSTATES; i++) {
			colors[i] = p.color(ArcticConstants.TERRAIN_COLORS[i][0],
					ArcticConstants.TERRAIN_COLORS[i][1],
					ArcticConstants.TERRAIN_COLORS[i][2]);
		}
		return colors;
	}
}