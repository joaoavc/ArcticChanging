package arctic;

import java.util.ArrayList;

import java.util.List;
import aa.Align;
import aa.Cohesion;
import aa.Eye;
import aa.Separate;
import aa.Wander;
import ecosystem.Animal.Sex;
import ecosystem.AnimalEvade;
import ecosystem.AnimalPursuit;
import ecosystem.Animal;
import ecosystem.Population;
import ecosystem.Terrain;
import physics.Body;
import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

public class ArcticPopulation extends Population {
	
	private List<Body> polarBears, shrimps, planktons, cods, seals;
	private ArrayList<Integer> numAnimals;
	protected PolarBear refPolarBear;
	protected Seal refSeal;
	protected Cod refCod;
	protected Shrimp refShrimp;
	protected Plankton refPlankton;
	private Arctic arctic;

	public ArcticPopulation(PApplet parent, SubPlot plt, Arctic arctic, ArrayList<Integer> numAnimals) {
		super(parent, plt, arctic);
		this.numAnimals = numAnimals;
		this.arctic = arctic;
		this.createArrays();
		this.setRefAnimals(parent, plt);
		this.populationOfPolarBears(parent, plt, refSeal);
		this.populationOfSeals(parent, plt, refCod, refPolarBear);
		this.populationOfCods(parent, plt, refShrimp, refSeal);
		this.populationOfShrimps(parent, plt, refPlankton, refCod);
		this.populationOfPlanktons(parent, plt, refShrimp);
	}
	
	@Override 
	public void update (float dt, Terrain terrain){
		super.update(dt, terrain);	
		this.refreshAnimalsVision();
	}
	
	private void createArrays() {
		this.polarBears = new ArrayList<Body>();
		this.seals = new ArrayList<Body>();
		this.shrimps = new ArrayList<Body>();
		this.cods = new ArrayList<Body>();
		this.planktons = new ArrayList<Body>();
	}
	
	private void setRefSeal(PApplet parent, SubPlot plt) {
		PVector posSeal = randomPos(parent);
		this.refSeal = new Seal (posSeal,  DNASeal.SEAL_MASS, DNASeal.SEAL_SIZE, 0, parent, plt, null, null, Sex.MASCULINE);
	}
	
	private void setRefPolarBear(PApplet parent, SubPlot plt) {
		PVector posPolarBear = randomPos(parent);
		this.refPolarBear = new PolarBear (posPolarBear,  DNAPolarBear.POLAR_BEAR_MASS, DNAPolarBear.POLAR_BEAR_SIZE,
				0, parent, plt, null, Sex.MASCULINE);
	}
	
	private void setRefCod(PApplet parent, SubPlot plt) {
		PVector posCod = randomPos(parent);
		this.refCod = new Cod (posCod,  DNACod.COD_MASS, DNACod.COD_SIZE, 
				0, parent, plt, null, null, Sex.MASCULINE);
	}
	
	private void setRefShrimp(PApplet parent, SubPlot plt) {
		PVector posShrimp = randomPos(parent);
		this.refShrimp = new Shrimp (posShrimp,  DNAShrimp.SHRIMP_MASS, DNAShrimp.SHRIMP_SIZE,
				0, parent, plt, null, null, Sex.MASCULINE);
	}
	
	private void setRefPlankton(PApplet parent, SubPlot plt) {
		PVector posPlankton = new PVector(parent.random((float)getWindow()[0],(float)getWindow()[1]),
				parent.random((float)getWindow()[2],(float)getWindow()[3]));
		this.refPlankton = new Plankton (posPlankton, DNAPlankton.PLANKTON_MASS,  DNAPlankton.PLANKTON_SIZE,
				0, parent, plt, null, Sex.MASCULINE);
	}
	
	private void setRefAnimals(PApplet parent, SubPlot plt) {
		this.setRefPolarBear(parent, plt);
		this.setRefSeal(parent, plt);
		this.setRefCod(parent, plt);
		this.setRefShrimp(parent, plt);
		this.setRefPlankton(parent, plt);
	}
	
	public void addPolarBear(PApplet parent, SubPlot plt, Animal prey) {
		PVector pos = randomPos(parent);
		int color = parent.color(
			DNAPolarBear.POLAR_BEAR_COLOR[0],
			DNAPolarBear.POLAR_BEAR_COLOR[1],
			DNAPolarBear.POLAR_BEAR_COLOR[2]);
		int rndNumber = (int)parent.random(0, 2);
		Sex polarBearSex = (rndNumber>0) ? Sex.MASCULINE : Sex.FEMININE;
		PolarBear polarBear = new PolarBear (pos, DNAPolarBear.POLAR_BEAR_MASS, DNAPolarBear.POLAR_BEAR_MASS,
				color, parent, plt, refSeal, polarBearSex);
		polarBear.setPopulation(this);
		polarBear.addBehavior(new Wander(1));
		polarBear.addBehavior(new AnimalPursuit(5, refSeal));
		polarBear.addBehavior(new AvoidWater(2.5f));
		ArrayList<Body> tracking = new ArrayList<Body>();
		tracking.addAll(seals);
		tracking.addAll(arctic.getWater());
		Eye eye = new Eye(polarBear, tracking);
		polarBear.setEye(eye);
		addAnimal(polarBear);
	}
	
	public void addSeal(PApplet parent, SubPlot plt, Animal prey, Animal predator ) {
		PVector pos = randomPos(parent);
		int color = parent.color(
			DNASeal.SEAL_COLOR[0],
			DNASeal.SEAL_COLOR[1],
			DNASeal.SEAL_COLOR[2]);
		int rndNumber = (int)parent.random(0, 2);
		Sex sealSex = (rndNumber>0) ? Sex.MASCULINE : Sex.FEMININE;
		Seal seal = new Seal (pos, DNASeal.SEAL_MASS, DNASeal.SEAL_SIZE,
				color, parent, plt, refCod, refPolarBear, sealSex);
		seal.setPopulation(this);
		seal.addBehavior(new Wander(1));
		seal.addBehavior(new AnimalPursuit(5, refCod));
		seal.addBehavior(new AnimalEvade(5, refPolarBear));
		seal.addBehavior(new AvoidWater(2.5f));
		ArrayList<Body> tracking = new ArrayList<Body>();
		tracking.addAll(polarBears); 
		tracking.addAll(cods);
		tracking.addAll(arctic.getWater());
		Eye eye = new Eye(seal, tracking);
		seal.setEye(eye);
		addAnimal(seal);
	}
	
	public void addCod(PApplet parent, SubPlot plt, Animal prey, Animal predator ) {
		PVector pos =  randomPos(parent);
		int color = parent.color(
			DNACod.COD_COLOR[0],
			DNACod.COD_COLOR[1],
			DNACod.COD_COLOR[2]);
		int rndNumber = (int)parent.random(0, 2);
		Sex codSex = (rndNumber>0) ? Sex.MASCULINE : Sex.FEMININE;
		Cod cod = new Cod (pos, DNACod.COD_MASS, DNACod.COD_SIZE,
				color, parent, plt, refShrimp, refSeal, codSex);
		cod.setPopulation(this);
		cod.addBehavior(new Wander(1));
		cod.addBehavior(new AnimalPursuit(5, refShrimp));
		cod.addBehavior(new AnimalEvade(5, refSeal));
		cod.addBehavior(new AvoidIce(7));
		ArrayList<Body> tracking = new ArrayList<Body>();
		tracking.addAll(seals); 
		tracking.addAll(shrimps);
		tracking.addAll(arctic.getIce());
		Eye eye = new Eye(cod, tracking);
		cod.setEye(eye);
		addAnimal(cod);
	}
	
	public void addShrimp(PApplet parent, SubPlot plt, Animal prey, Animal predator ) {
		PVector pos =  randomPos(parent);
		int color = parent.color(
			DNAShrimp.SHRIMP_COLOR[0],
			DNAShrimp.SHRIMP_COLOR[1],
			DNAShrimp.SHRIMP_COLOR[2]);
		int rndNumber = (int)parent.random(0, 2);
		Sex shrimpSex = (rndNumber>0) ? Sex.MASCULINE : Sex.FEMININE;
		Shrimp shrimp = new Shrimp (pos, DNAShrimp.SHRIMP_MASS, DNASeal.SEAL_SIZE,
				color, parent, plt, refPlankton, refSeal, shrimpSex);
		shrimp.setPopulation(this);
		shrimp.addBehavior(new AnimalPursuit(5, refPlankton));
		shrimp.addBehavior(new AnimalEvade(5, refCod));
		shrimp.addBehavior(new Align(10));
		shrimp.addBehavior(new Separate(1));
		shrimp.addBehavior(new Cohesion(10));
		shrimp.addBehavior(new AvoidIce(7));
		ArrayList<Body> tracking = new ArrayList<Body>();
		tracking.addAll(cods); 
		tracking.addAll(planktons);
		tracking.addAll(arctic.getIce());
		Eye eye = new Eye(shrimp, tracking);
		shrimp.setEye(eye);
		addAnimal(shrimp);
	}
	
	public void addPlankton(PApplet parent, SubPlot plt, Animal predator ) {
		PVector pos =  randomPos(parent);
		int color = parent.color(
			DNAPlankton.PLANKTON_COLOR[0],
			DNAPlankton.PLANKTON_COLOR[1],
			DNAPlankton.PLANKTON_COLOR[2]);
		int rndNumber = (int)parent.random(0, 2);
		Sex planktonSex = (rndNumber>0) ? Sex.MASCULINE : Sex.FEMININE;
		Plankton plankton = new Plankton (pos, DNAPlankton.PLANKTON_MASS, DNAPlankton.PLANKTON_SIZE,
				color, parent, plt, refShrimp, planktonSex);
		plankton.setPopulation(this);
		plankton.addBehavior(new Wander(1));
		plankton.addBehavior(new AvoidIce(20));
		ArrayList<Body> tracking = new ArrayList<Body>();
		tracking.addAll(planktons);
		tracking.addAll(arctic.getIce());
		Eye eye = new Eye(plankton, tracking);
		plankton.setEye(eye);
		addAnimal(plankton);
	}
	
	public PVector randomPos(PApplet parent) {
		PVector pos = new PVector(parent.random((float)getWindow()[0],(float)getWindow()[1]),
				parent.random((float)getWindow()[2],(float)getWindow()[3]));
		return pos;
	}
		
	
	public void populationOfPolarBears(PApplet parent, SubPlot plt, Animal prey) {
		for(int i=0; i<numAnimals.get(0); i++)
			addPolarBear(parent,plt,prey);
	}
	
	public void populationOfSeals(PApplet parent, SubPlot plt, Animal prey, Animal predator) {
		for(int i=0; i<numAnimals.get(1); i++) 
			addSeal(parent, plt, prey, predator);
	}
	
	public void populationOfCods(PApplet parent, SubPlot plt, Animal prey, Animal predator) {
		for(int i=0; i<numAnimals.get(2); i++) 
			addCod(parent, plt, prey, predator);
	}
	
	public void populationOfShrimps(PApplet parent, SubPlot plt, Animal prey, Animal predator) {
		for(int i=0; i<numAnimals.get(3); i++) 
			addShrimp(parent, plt, prey, predator);
	}
	
	public void populationOfPlanktons(PApplet parent, SubPlot plt, Animal predator) {
		for(int i=0; i<numAnimals.get(4); i++) 
			addPlankton(parent, plt, predator);
	}
		
	public void refreshPolarBearsVision() {
		for(int i=0; i<polarBears.size(); i++) { 
			((PolarBear) polarBears.get(i)).refreshPredatorVision(seals, arctic);
			((PolarBear) polarBears.get(i)).breathing(arctic);
		}
	}
	
	public void refreshSealsVision() {
		for(int i=0; i<seals.size(); i++) { 
			((Seal) seals.get(i)).refreshPreyPredatorVision(cods, polarBears, arctic);
			((Seal) seals.get(i)).breathing(arctic);
		}
	}
	
	public void refreshCodsVision() {
		for(int i=0; i<cods.size(); i++) { 
			((Cod) cods.get(i)).refreshPreyPredatorVision(shrimps, seals, arctic);
		}
	}
	
	public void refreshShrimpsVision() {
		for(int i=0; i<shrimps.size(); i++) { 
			((Shrimp) shrimps.get(i)).refreshPreyPredatorVision(planktons, cods, arctic);	
		}
	}
	
	public void refreshPlanktonsVision() {
		for(int i=0; i<planktons.size(); i++) {
			((Plankton) planktons.get(i)).refreshPreyVision(shrimps);
		}
	}
	
	public void refreshAnimalsVision() {
		refreshPolarBearsVision();
		refreshSealsVision();
		refreshCodsVision();
		refreshShrimpsVision();
		refreshPlanktonsVision();
	}
	
	public int getNumPolarBear() {
		return this.polarBears.size();
	}
	
	public int getNumSeal() {
		return this.seals.size();
	}
	
	public int getNumCod() {
		return this.cods.size();
	}
	
	public int getNumShrimp() {
		return this.shrimps.size();
	}
	
	public int getNumPlankton() {
		return this.planktons.size();
	}
	
	public void addAnimal(Animal a) {
		allAnimals.add(a);
		if(a instanceof PolarBear) polarBears.add(a);
		else if(a instanceof Seal) seals.add(a);
		else if(a instanceof Cod) cods.add(a);
		else if(a instanceof Shrimp) shrimps.add(a);
		else if(a instanceof Plankton) planktons.add(a);
	}
	
	public void removeAnimal(Animal a) {
		allAnimals.remove(a);
		if(a instanceof PolarBear) polarBears.remove(a);
		else if(a instanceof Seal) seals.remove(a);
		else if(a instanceof Cod) cods.remove(a);
		else if(a instanceof Shrimp) shrimps.remove(a);
		else if(a instanceof Plankton) planktons.remove(a);
	}

	public List<Body> getPolarBears() {
		return polarBears;
	}

	public void setPolarBears(List<Body> polarBears) {
		this.polarBears = polarBears;
	}

	public List<Body> getShrimps() {
		return shrimps;
	}

	public void setShrimps(List<Body> shrimps) {
		this.shrimps = shrimps;
	}

	public List<Body> getPlanktons() {
		return planktons;
	}

	public void setPlanktons(List<Body> planktons) {
		this.planktons = planktons;
	}

	public List<Body> getCods() {
		return cods;
	}

	public void setCods(List<Body> cods) {
		this.cods = cods;
	}

	public List<Body> getSeals() {
		return seals;
	}

	public void setSeals(List<Body> seals) {
		this.seals = seals;
	}	
}