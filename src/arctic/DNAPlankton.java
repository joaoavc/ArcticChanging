package arctic;

import aa.DNA;

public class DNAPlankton extends DNA {
	
	public final static float PLANKTON_SIZE = .1f;
	public final static float PLANKTON_MASS= 7f;
	public final static float PLANKTON_ENERGY = 100f;
	public final static float PLANKTON_ENERGY_TO_REPRODUCE = 50f;
	public final static int[] PLANKTON_COLOR = {255, 0, 255};

	
	public DNAPlankton() {
		//Physics
		this.maxSpeed = random(2,3);
		this.maxForce = random(1,3);
		//Vision
		this.visionDistance = random(2, 3);
		this.visionSafeDistance = 0.25f * visionDistance;
		this.visionAngle = (float)Math.PI/8;
		//Persuit
		this.deltaPersuit = random(0.5f, 1f);
		//Arrive
		this.radiusArrive = random(3, 5);
		//Wander
		deltaTWander = random(0.2f, 1.2f);
		radiusWander = random(2, 3);
		deltaPhiWander = (float)Math.PI/4;
	}
	
	public DNAPlankton(DNA dna, boolean mutate) {
		super(dna, mutate);
	}

}
