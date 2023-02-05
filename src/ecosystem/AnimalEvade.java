package ecosystem;

import aa.Boid;
import aa.Evade;
import physics.Body;
import processing.core.PVector;

public class AnimalEvade extends Evade {

	private Animal predator;
	public AnimalEvade(float weight, Animal predator) {
		super(weight);
		this.predator = predator;
	}

	
	@Override
	public PVector getDesiredVelocity(Boid me) {
		Body bodyTarget = me.eye.getTarget();
		if(bodyTarget != null) {
			if (predator.getClass().equals(bodyTarget.getClass())) 		
				return super.getDesiredVelocity(me);
		}
		return new PVector();
	}


}
