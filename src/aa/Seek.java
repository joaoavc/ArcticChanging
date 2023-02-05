package aa;

import physics.Body;
import processing.core.PVector;

public class Seek extends Behavior {

	public Seek(float weight) {
		super(weight);
	}
	
	@Override
	public PVector getDesiredVelocity(Boid me) {
		Body target = me.eye.getTarget();
		return  PVector.sub(target.getPos(), me.getPos());
		
		
	}

}
