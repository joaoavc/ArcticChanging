package aa;

import java.util.ArrayList;
import java.util.List;
import physics.Body;
import processing.core.PVector;

public class Eye {
	private List<Body> allTrackingBodies;
	private List<Body> farSight;
	private List<Body> nearSight;
	private Boid me;
	private Body target;
	
	public Eye(Boid me, List<Body> allTrackingBodies) {
		this.me = me;
		this.allTrackingBodies = allTrackingBodies;
		if(allTrackingBodies.size() > 0)
			setTarget(allTrackingBodies.get(0));
	}
	
	public Eye(Boid me, Eye eye) {
		this.allTrackingBodies = eye.allTrackingBodies;
		this.me = me;
		this.target = eye.target;
		this.look();
	}
	
	public Eye(Boid me) {
		this.me = me;
		this.allTrackingBodies = new ArrayList<Body>();
	}
	
	public List<Body> getFarSight(){
		return this.farSight;
	}
	
	public List<Body> getNearSight(){
		return this.nearSight;
	}
	
	public void look() {
		this.farSight = new ArrayList<Body>();
		this.nearSight = new ArrayList<Body>();
		for( Body b :  allTrackingBodies) {
			if(farSight(b.getPos())) {
				farSight.add(b);
			}
			if(nearSight(b.getPos())) {
				nearSight.add(b);
			}
		}
	}
	
	private boolean inSight(PVector t, float maxDistance, float maxAngle) {
		PVector r = PVector.sub(t, me.getPos());
		float d = r.mag();
		float angle = PVector.angleBetween(r, me.getVel());
		return((d > 0) && (d < maxDistance) && (angle < maxAngle));
	}
	
	private boolean farSight(PVector t) {
		return inSight(t, me.dna.visionDistance, me.dna.visionAngle);
	}
	
	private boolean nearSight(PVector t) {
		return inSight(t, me.dna.visionDistance, (float)Math.PI);
	}
	
	public void setTarget(Body body) {
		this.target = body;
	}

	public Body getTarget() {
		return target;
	}

}
