package ecosystem;

import aa.Boid;
import aa.Pursuit;
import physics.Body;
import processing.core.PVector;

public class AnimalPursuit extends Pursuit {

	private Animal prey;
	public AnimalPursuit(float weight, Animal prey) {
		super(weight);
		this.prey = prey;
	}
	
	@Override
	public PVector getDesiredVelocity(Boid me) {
		Body bodyTarget = me.eye.getTarget();
		if(bodyTarget != null) {
			if (prey.getClass().equals(bodyTarget.getClass())) 		
				return super.getDesiredVelocity(me);
		}
		return new PVector();
	}
}