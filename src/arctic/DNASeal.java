package arctic;

import aa.DNA;

public class DNASeal extends DNA{
	
	public final static float SEAL_SIZE = 1.8f;
	public final static float SEAL_MASS= 15f;
	public final static float SEAL_ENERGY = 1500f;
	public final static float SEAL_ENERGY_TO_REPRODUCE = SEAL_ENERGY*0.25f;
	public final static int[] SEAL_COLOR = {125,125,125};
	
	public DNASeal() {
		//Physics
		this.maxSpeed = random(5,9);
		this.maxForce = random(4,8);
		//Vision
		this.visionDistance = random(4, 12);
		this.visionSafeDistance = 0.25f * visionDistance;
		this.visionAngle = (float)Math.PI/3;
		//Persuit
		this.deltaPersuit = random(0.5f, 1f);
		//Arrive
		this.radiusArrive = random(3, 5);
		//Wander
		deltaTWander = random(0.2f, 1.2f);
		radiusWander = random(2, 3);
		deltaPhiWander = (float)Math.PI/4;
	}
	
	public DNASeal(DNA dna, boolean mutate) {
		super(dna, mutate);
	}

}
