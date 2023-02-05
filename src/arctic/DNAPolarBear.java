package arctic;

import aa.DNA;

public class DNAPolarBear extends DNA {
	
	public final static float POLAR_BEAR_SIZE = 2.2f;
	public final static float POLAR_BEAR_MASS= 20f;
	public final static float POLAR_BEAR_ENERGY = 2000f;
	public final static float POLAR_BEAR_ENERGY_TO_REPRODUCE = POLAR_BEAR_ENERGY*0.25f;
	public final static int[] POLAR_BEAR_COLOR = {255,255,255};

	public DNAPolarBear() {
		//Physics
		this.maxSpeed = random(6,8);
		this.maxForce = random(4,8);
		//Vision
		this.visionDistance = random(7, 12);
		this.visionSafeDistance = 0.25f * visionDistance;
		this.visionAngle = (float)Math.PI/2;
		//Persuit
		this.deltaPersuit = random(0.5f, 1.5f);
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
