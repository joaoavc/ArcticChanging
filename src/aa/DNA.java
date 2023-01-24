package aa;

public class DNA {
	public float maxSpeed;
	public float maxForce;
	public float visionDistance;
	public float visionSafeDistance;
	public float visionAngle;
	public float deltaPersuit;
	public float radiusArrive;
	public float deltaTWander;
	public float radiusWander;
	public float deltaPhiWander;
	
	public DNA() {
		
		//Physics
		this.maxSpeed = random(3, 5);
		this.maxForce = random(4, 7);
		
		//Vision
		this.visionDistance = random(2, 4);
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
	
	public DNA(DNA dna, boolean mutate) {
		maxSpeed = dna.maxSpeed;
		maxForce= dna.maxForce;
		
		visionDistance = dna.visionDistance;
		visionSafeDistance = dna.visionSafeDistance;
		visionAngle = dna.visionAngle;
		
		deltaPersuit = dna.deltaPersuit;
		radiusArrive = dna.radiusArrive;
		
		deltaTWander = dna.deltaTWander;
		deltaPhiWander = dna.deltaPhiWander;
		radiusWander = dna.radiusWander;
		if(mutate) mutate();
	}
	
	private void mutate() {
		maxSpeed+= random(-0.2f, 0.2f);
		maxSpeed = Math.max(0, maxSpeed);
	}
	
	public static float random(float min, float max) {
		return (float)(min + (max - min)*Math.random());
	}
}