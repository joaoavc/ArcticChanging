package aa;

import java.util.ArrayList;
import java.util.List;

import physics.Body;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PShape;
import processing.core.PVector;
import tools.SubPlot;

public class Boid extends Body {
	
	protected DNA dna;
	protected SubPlot plt;
	private PShape shape;
	protected List<Behavior> behaviors;
	private double[] window;
	private float sumWeights;
	protected PApplet parent;
	protected float phiWander;
	protected Eye eye;

	
	protected Boid(PVector pos, float mass, float radius, int color, PApplet parent, SubPlot plt ) {
		super(pos, new PVector(0, 0), mass, radius, color);
		behaviors = new ArrayList<Behavior>();
		this.dna = new DNA();
		this.plt = plt;
		window = this.plt.getWindow();
		this.parent = parent;
		setShape(this.parent, plt);
	}
	
	public List<Behavior>getBehaviors(){
		return behaviors;
	}
	
	public void mutateBehaviors() {
		for(Behavior behavior : behaviors) {
			if(behavior instanceof AvoidObstacle) {
				behavior.weight += DNA.random(-0.5f, 0.5f);
				behavior.weight += Math.max(0, behavior.weight);
			}
		}
		updateSumWeights();
	}
	
	public DNA getDNA() {
		return this.dna;
	}
	
	public void setEye(Eye eye) {
		this.eye = eye;
	}
	
	
	public void setShape(PApplet p, SubPlot plt, float radius, int color){
		this.radius = radius;
		this.color = color;
		setShape(p,plt);
	}
	
	
	public void setShape(PApplet p, SubPlot plt){
		float[] rr = plt.getVectorCoord(radius, radius);
		shape = p.createShape();
		shape.beginShape();
		shape.noStroke();
		shape.fill(color);
		shape.vertex(-rr[0],rr[0]/2);
		shape.vertex(rr[0],0);
		shape.vertex(-rr[0],-rr[0]/2);
		shape.vertex(-rr[0]/2,0);
		shape.endShape(PConstants.CLOSE);
	}
	
	
	private void updateSumWeights() {
		sumWeights = 0;
		for(Behavior beh:behaviors)
			sumWeights += beh.getWeight();
	}
	
	
	public void addBehavior(Behavior behavior) {
		behaviors.add(behavior);
		this.updateSumWeights();

	}
	
	
	public void removeBehavior(Behavior behavior) {
		if(behaviors.contains(behavior)) {
			behaviors.remove(behavior);
		}
		this.updateSumWeights();
	}
	
	
	public void applyBehavior(int i, float dt) {
		if(eye != null ) eye.look();
		Behavior behavior = behaviors.get(i);
		PVector vd = behavior.getDesiredVelocity(this);
		move(dt, vd);
	}
	
	
	public void applyBehaviors(float dt) {
		if(eye != null ) eye.look();
		PVector vd = new PVector();
		for(Behavior behavior : behaviors) {
			PVector vdd = behavior.getDesiredVelocity(this);
			vdd.mult(behavior.getWeight()/this.sumWeights);
			vd.add(vdd);
		}
		move(dt, vd);
	}
	
	private void move(float dt, PVector vd) {
		vd.normalize().mult(dna.maxSpeed);
		PVector fs = PVector.sub(vd,  vel);
		applyForce(fs.limit(dna.maxForce));
		super.move(dt);
		if(pos.x < window[0]) pos.x += window[1] - window[0];
		if(pos.y < window[2]) pos.y += window[3] - window[2];
		if(pos.x > window[1]) pos.x -= window[1] - window[0];
		if(pos.y > window[3]) pos.y -= window[3] - window[2];
	}
	
	public void display(PApplet p, SubPlot plt) {
		p.pushMatrix();
		float[] pp = plt.getPixelCoord(pos.x, pos.y);
		p.translate(pp[0], pp[1]);
		p.rotate(-vel.heading());
		p.shape(shape);
		p.popMatrix();
	}
}
