package arctic;

import aa.DNA;

public class DNAShrimp extends DNA {
	
	public final static float SHRIMP_SIZE = .2f;
	public final static float SHRIMP_MASS= 1f;
	public final static float SHRIMP_ENERGY = 750f;
	public final static float SHRIMP_ENERGY_TO_REPRODUCE = SHRIMP_ENERGY*.25f;
	public final static int[] SHRIMP_COLOR = {255,0,0};

	
	public DNAShrimp() {
		//Physics
		this.maxSpeed = random(2,5);
		this.maxForce = random(2,4);
		//Vision
		this.visionDistance = random(4, 8);
		this.visionSafeDistance = 0.25f * visionDistance;
		this.visionAngle = (float)Math.PI/4;
		//Persuit
		this.deltaPersuit = random(0.5f, 1f);
		//Arrive
		this.radiusArrive = random(3, 5);
		//Wander
		deltaTWander = random(0.2f, 1.2f);
		radiusWander = random(2, 3);
		deltaPhiWander = (float)Math.PI/4;
	}
	
	public DNAShrimp(DNA dna, boolean mutate) {
		super(dna, mutate);
	}

}
