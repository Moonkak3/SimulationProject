package main.Model.Organisms;

import main.Model.Stats.OrganismStats;
import main.Model.Vector;

public class Organism {

    // position
    protected Vector pos;
    // velocity
    protected Vector v;
    // acceleration
    protected Vector a;
    // stats
    protected OrganismStats stats;

    public Organism(Vector pos, Vector v, Vector a, OrganismStats stats){
        this.pos = pos;
        this.v = v;
        this.a = a;
        this.stats = stats;
    }

    public void move(){
        pos.addVector(v);
        v.addVector(a);
        double magnitude;
        if ((magnitude = v.getMagnitude()) > stats.getMaxSpeed()){
            v.scalarDivide(magnitude);
            v.scalarMultiply(stats.getMaxSpeed());
        }
        a = new Vector();
    }

    public double getAngleTo(Vector other){
        double cartesianAngle = Math.toDegrees(Math.atan2(other.getY() - pos.getY(), other.getX() - pos.getX()));
        double angle = cartesianAngle - v.getAngle();
        if (angle < -180){
            angle += 360;
        } else if (angle > 180){
            angle -= 360;
        }
        return angle;
    }

    public double getDistanceTo(Vector other){
        return Math.sqrt(Math.pow(other.getX() - pos.getX(), 2) + Math.pow(other.getY() - pos.getY(), 2));
    }

    public boolean inVisionCone(Vector other){
        return getDistanceTo(other) < stats.getVisionRadius() && Math.abs(getAngleTo(other)) < stats.getVisionAngle() / 2.0;
    }

    public boolean inVisionCone(Vector other, double boundaryMargin){
        return getDistanceTo(other) < boundaryMargin;
    }

    public Vector getPos(){
        return pos;
    }

    public double getX() {
        return pos.getX();
    }

    public double getY() {
        return pos.getY();
    }

    public Vector getV(){
        return v;
    }
}
