package aa;

import physics.Body;
import processing.core.PVector;

public class Seek extends Behavior {

	public Seek(float weight) {
		super(weight);
	}
	
	@Override
	public PVector getDesiredVelocity(Boid me) {
		Body target = me.eye.target;
		return  PVector.sub(target.getPos(), me.getPos());
		
		
	}

}
