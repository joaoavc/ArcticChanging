package arctic;

import java.util.ArrayList;

import processing.IProcessingApp;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import tools.SubPlot;

public class ArcticApp implements IProcessingApp {
	
	private float[] viewport2 = {0f, 0f, .8f, 1f};
	private static final int NUM_ANIMALS = 5; 
	private static final int ITERATIONS_YEAR = 50; 
	private SubPlot plt2;	
	private Arctic arctic;
	private ArcticPopulation population;
	private PImage startScreen, homeScreen, inputValuesScreen, helpScreen, finalScreen;
	private int appState = 0;
	private ArrayList<String> numPopulation, animalsNames;
	private ArrayList<Boolean> selectedPopulation;
	private ArrayList<Integer> start, intNumPopulation;
	private int year=2020;
	private int cont = 0;
	private float temperature = -14f;
	private int numCellsIce = 0;
	private int numCellsWater = 0;
	private PImage sealImage, polarBearImage, codImage, shrimpImage, planktonImage;

	

	@Override
	public void setup(PApplet p) {
		plt2 = new SubPlot(ArcticConstants.WINDOW, viewport2, p.width, p.height);
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
		  case 5:
			this.resultsScreen(p);
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
		if(appState == 4) {
			if ((p.key == 'p' || p.key == 'P') ) this.population.addPolarBear(p, plt2, population.refSeal);
			if ((p.key == 's' || p.key == 'S') ) this.population.addSeal(p, plt2, population.refCod, population.refPolarBear);
			if ((p.key == 'c' || p.key == 'C') ) this.population.addCod(p, plt2, population.refShrimp, population.refCod);
			if ((p.key == 'r' || p.key == 'R') ) this.population.addShrimp(p, plt2, population.refShrimp, population.refCod);
			if ((p.key == 'k' || p.key == 'K') ) this.population.addPlankton(p, plt2, population.refShrimp);
			if ((p.key == 'e' || p.key == 'E') ) appState = 5;

		}
	}

	@Override
	public void mousePressed(PApplet p) {
		if(overRect(p, p.width/2-100,p.height-300,200,50) && appState == 0) { 
			appState = 1;
		}
		else if(overRect(p, p.width/2-300,p.height-300,200,50) && appState == 1) { 
			for(int i=0;i<NUM_ANIMALS;i++) {
				numPopulation.add("");
				selectedPopulation.add(false);
			}
			appState = 3;
		}
		else if(overRect(p, p.width/2, p.height-300,200,50) && appState == 1) { 
			appState = 2;
		}
		else if(overRect(p, p.width/2-100,p.height-100,200,50) && appState == 2) { 
			appState = 1;
		}
		else if(overRect(p, p.width/2-100,p.height-100,200,50) && appState == 5) { 
			appState = 1;
		}
		else if(overRect(p, p.width/2-100,p.height-100,200,50) && appState == 3) { 
			if(numPopulation.get(0).length()>0 && numPopulation.get(1).length()>0 &&
			numPopulation.get(2).length()>0 && numPopulation.get(3).length()>0 && numPopulation.get(4).length()>0) {
				getValuesPopulation();
				this.startPopulation(p);
				appState = 4;
			}
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
		p.background(helpScreen);
		p.textSize(75);
		p.fill(255);
		p.text("Arctic Changing", 300, 150);
		p.textSize(45);
		p.text("Help", 500, 200);
		p.textSize(25);
		p.text("This is a simulation that aims to show and measure the effects of climate change in the Arctic.", 25, 270);
		p.text("The simulation shows the evolution of the terrain (ice caps) and how the changes in the terrain \n affect the existing fauna." , 25, 320);
		p.textSize(20);
		p.image(polarBearImage, 50, 400);
		p.text("POLAR BEAR: Predator that persuits seals.", 125, 430);
		p.image(sealImage, 50, 470);
		p.text("SEAL: Prey/Predator that persuits cods and evades from polar bears.", 125, 500);
		p.image(codImage, 50, 540);
		p.text("COD: Prey/Predator that persuits shrimps and evades from seals", 125, 570);
		p.image(shrimpImage, 50, 610);
		p.text("SHRIMP: Prey/Predator that persuits planktons and evades from cods", 125, 640);
		p.image(planktonImage, 50, 680);
		p.text("PLANKTON: Prey that is eaten by shrimps.", 125, 710);
		p.fill(160, 250, 740);
		p.textSize(25);
		drawButton(p,p.width/2-40, p.height-64,p.width/2-100,p.height-100,200,50, "Home");

	}
	
	public void simulationScreen(PApplet p, float dt) {
		p.background(0);
		p.textSize(30);
		p.fill(100, 195, 219);
		p.text("Arctic Changing", 965, 40);
		p.textSize(20);
		p.fill(160, 250, 230);
		p.textSize(25);
		p.text("Environment", 1015, 100);
		p.textSize(20);
		p.fill(200, 245, 255);
		p.text("Year:", 965, 130);
		p.fill(255);
		p.text(year+"", 1100, 130);
		p.fill(200, 245, 255);
		p.text("Temperature:", 965, 155);
		p.fill(255);
		float scale = (float) Math.pow(10, 2);
		this.temperature = Math.round(this.temperature * scale)/scale;
		p.text(this.temperature+"ºC", 1100, 155);
		p.fill(200, 245, 255);
		p.text("Ice cells:", 965, 180);
		p.fill(255);
		p.text(this.numCellsIce, 1100, 180);
		p.fill(200, 245, 255);
		p.text("Water Cells:", 965, 205);
		p.fill(255);
		p.text(this.numCellsWater, 1100, 205);
		p.textSize(25);
		p.fill(160, 250, 230);
		p.text("Number of Animals", 960, 265);
		p.textSize(20);
		p.image(polarBearImage, 1000, 295);
		p.fill(255);
		p.text(this.population.getNumPolarBear()+"", 1100, 325);
		p.image(sealImage, 1000, 365);
		p.fill(255);
		p.text(this.population.getNumSeal()+"", 1100, 395);
		p.image(codImage, 1000, 435);
		p.fill(255);
		p.text(this.population.getNumCod()+"", 1100, 465);
		p.image(shrimpImage, 1000, 505);
		p.fill(255);
		p.text(this.population.getNumShrimp()+"", 1100, 535);
		p.image(planktonImage, 1000, 575);
		p.fill(255);
		p.text(this.population.getNumPlankton()+"", 1100, 605);
		p.fill(160, 250, 230);
		p.textSize(25);
		p.text("Add animals", 1000, 685);
		p.fill(200, 245, 255);
		p.textSize(15);
		p.text("Click 'P' to add an Polar Bear", 965, 715);
		p.text("Click 'S' to add an Seal", 965, 740);
		p.text("Click 'C' to add an Cod", 965, 765);
		p.text("Click 'R' to add an Shrimp", 965, 790);
		p.text("Click 'K' to add an Plankton", 965, 815);
		p.fill(255, 200, 200);
		p.textSize(20);
		p.text("Click 'E' to end Sim.", 985, 875);
		population.update(dt, arctic);
		arctic.display(p);
		population.display(p, plt2);
		if(cont<ITERATIONS_YEAR) cont++;
		else {
			cont=0;
			year++;
			this.temperature += p.random(0f, 0.75f);
			this.arctic.iceCapState(p, this.temperature);
			this.numCellsIce = arctic.getIce().size();
			this.numCellsWater = arctic.getWater().size();
		}
	}
	
	public void startPopulation(PApplet p){
		arctic = new Arctic(p, plt2);
		arctic.setStateColors(getColors(p));
		arctic.initRandomCustom(ArcticConstants.PATCH_TYPE_PROB);
		arctic.majorityRule();
		this.numCellsIce = arctic.getIce().size();
		this.numCellsWater = arctic.getWater().size();
		population = new ArcticPopulation(p, plt2, arctic, intNumPopulation);
		year=2020;
		cont = 0;
		temperature = -14f;
		numCellsIce = 0;
		numCellsWater = 0;
		saveStartValues();
	}
	
	public void resultsScreen(PApplet p) {
		p.background(finalScreen);
		p.textSize(75);
		p.fill(255);
		p.text("Arctic Changing", 300, 150);
		p.textSize(45);
		p.text("Results", 500, 200);
		p.textSize(30);
		p.fill(200, 245, 255);
		p.text("YEAR", 50, 300);
		p.fill(255);
		p.text(2020+"", 500, 300);
		p.text(this.year+"", 850, 300);
		p.text(-14.0+"ºC", 500, 350);
		p.text(this.temperature+"ºC", 850, 350);
		p.fill(200, 245, 255);
		p.text("TEMPERATURE", 50, 350);
		p.fill(200, 245, 255);
		p.text("POLAR BEARS", 50, 400);
		p.fill(255);
		p.text(this.start.get(0), 525, 400);
		p.text(this.population.getNumPolarBear(), 875, 400);
		p.fill(200, 245, 255);
		p.text("SEALS", 50, 450);
		p.fill(255);
		p.text(this.start.get(1), 525, 450);
		p.text(this.population.getNumSeal(), 875, 450);
		p.fill(200, 245, 255);
		p.text("CODS", 50, 500);
		p.fill(255);
		p.text(this.start.get(2), 525, 500);
		p.text(this.population.getNumCod(), 875, 500);
		p.fill(200, 245, 255);
		p.text("SHRIMPS", 50, 550);
		p.fill(255);
		p.text(this.start.get(3), 525, 550);
		p.text(this.population.getNumShrimp(), 875, 550);
		p.fill(200, 245, 255);
		p.text("PLANKTONS", 50, 600);
		p.fill(255);
		p.text(this.start.get(4), 525, 600);
		p.text(this.population.getNumPlankton(), 875, 600);
		p.fill(200, 245, 255);
		p.text("ICE CELLS", 50, 650);
		p.fill(255);
		p.text(this.start.get(5), 500, 650);
		p.text(this.arctic.getIce().size(), 850, 650);
		p.fill(200, 245, 255);
		p.text("WATER CELLS", 50, 700);
		p.fill(255);
		p.text(this.start.get(6), 500, 700);
		p.text(this.arctic.getWater().size(), 850, 700);
		drawButton(p,p.width/2-40, p.height-64,p.width/2-100,p.height-100,200,50, "Home");
	}
	
	public void saveStartValues() {
		start = new ArrayList<Integer>();
		start.add(this.population.getNumPolarBear());
		start.add(this.population.getNumSeal());
		start.add(this.population.getNumCod());
		start.add(this.population.getNumShrimp());
		start.add(this.population.getNumPlankton());
		start.add(this.arctic.getIce().size());
		start.add(this.arctic.getWater().size());
	}
	
	public void startGUI(PApplet p) {
		startScreen = p.loadImage("arctic_1.jpg");
		startScreen.resize(1200,900);
		homeScreen = p.loadImage("arctic_2.jpg");
		homeScreen.resize(1200,900);
		inputValuesScreen = p.loadImage("arctic_3.jpg");
		inputValuesScreen.resize(1200,900);
		helpScreen = p.loadImage("arctic_5.jpg");
		helpScreen.resize(1200,900);
		finalScreen = p.loadImage("arctic_6.jpg");
		finalScreen.resize(1200,900);
		selectedPopulation = new ArrayList<Boolean>();
		numPopulation = new ArrayList<String>();
		for(int i=0;i<NUM_ANIMALS;i++) {
			numPopulation.add("");
			selectedPopulation.add(false);
		}
		animalsNames = new ArrayList<String>();
		animalsNames.add( "Bears");
		animalsNames.add("Seals");
		animalsNames.add("Cods");
		animalsNames.add("Shrimps");
		animalsNames.add("Planktons");
		sealImage = p.loadImage("seal.png");
		sealImage.resize(70, 60);
		polarBearImage = p.loadImage("bear.png");
		polarBearImage.resize(70, 60);
		codImage = p.loadImage("fish.png");
		codImage.resize(70, 60);
		shrimpImage = p.loadImage("shrimp.png");
		shrimpImage.resize(70, 60);
		planktonImage = p.loadImage("plankton.png");
		planktonImage.resize(70, 60);

	}
	
	public void getValuesPopulation() {
		intNumPopulation = new ArrayList<Integer>();
		intNumPopulation.add(Integer.parseInt(numPopulation.get(0)));
		intNumPopulation.add(Integer.parseInt(numPopulation.get(1)));
		intNumPopulation.add(Integer.parseInt(numPopulation.get(2)));
		intNumPopulation.add(Integer.parseInt(numPopulation.get(3)));
		intNumPopulation.add(Integer.parseInt(numPopulation.get(4)));
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