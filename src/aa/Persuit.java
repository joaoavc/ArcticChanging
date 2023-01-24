package aa;

import physics.Body;
import processing.core.PVector;

public class Persuit extends Behavior {

	public Persuit(float weight) {
		super(weight);
		// TODO Auto-generated constructor stub
	}

	@Override
	public PVector getDesiredVelocity(Boid me) {
		Body bodyTarget = me.eye.target;
		PVector d = bodyTarget.getVel().mult(me.dna.deltaPersuit);
		PVector target = PVector.add(bodyTarget.getPos(), d);
		return PVector.sub(target, me.getPos());
	}

}
