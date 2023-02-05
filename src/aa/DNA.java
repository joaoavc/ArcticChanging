package aa;

public abstract class DNA {
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
	
	public DNA() {}
	
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
	
	protected void mutate() {
		maxSpeed+= random(-0.5f, 0.5f);
		maxSpeed = Math.max(0, maxSpeed);
		maxForce += random(-0.5f, 0.5f);
		maxForce = Math.max(0, maxForce);
		visionDistance +=  random(-0.5f, 0.5f);
		visionDistance = Math.max(0, visionDistance);
	}
	
	public static float random(float min, float max) {
		return (float)(min + (max - min)*Math.random());
	}
}