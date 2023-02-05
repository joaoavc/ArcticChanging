package arctic;

import aa.AvoidObstacle;
import aa.Boid;
import physics.Body;
import processing.core.PVector;

public class AvoidWater extends AvoidObstacle {

	public AvoidWater(float weight) {
		super(weight);
	}
	
	@Override
	public PVector getDesiredVelocity(Boid me) {
		float s = hasObstacle(me);
		if(s == 0) return me.getVel().copy();
		PVector vd = new PVector(me.getVel().y, -me.getVel().x);
		if (s > 0) vd.mult(-1);
		return vd;
	}
	
	@Override
	protected float hasObstacle (Boid me) {
		for(Body body : me.eye.getFarSight()) {
			if(body instanceof WaterBody) {
				PVector r = PVector.sub(body.getPos(), me.getPos());
				PVector vd = new PVector(me.getVel().y, -me.getVel().x);
				return PVector.dot(vd, r);
			}
		}
		return 0;
	}

}
