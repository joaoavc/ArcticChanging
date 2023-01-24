package artic;

import aa.DNA;

public class DNAPolarBear extends DNA {
	
	public final static float POLAR_BEAR_SIZE = .2f;
	public final static float POLAR_BEAR_MASS= 7f;
	public final static float POLAR_BEAR_ENERGY = 100f;
	public final static float POLAR_BEAR_ENERGY_TO_REPRODUCE = 50f;
	
	public DNAPolarBear() {
		//Physics
		this.maxSpeed = random(4,8);
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
	
	public DNAPolarBear(DNA dna, boolean mutate) {
		super(dna, mutate);
	}

}
