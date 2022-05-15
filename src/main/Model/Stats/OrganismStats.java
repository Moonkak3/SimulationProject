package main.Model.Stats;

public class OrganismStats {

    // maximum speed
    protected double maxSpeed;
    // maximum acceleration
    protected double maxAcceleration;
    // vision or perception radius
    protected int visionRadius;
    // vision or perception angle in degrees
    protected int visionAngle;

    public OrganismStats(double maxSpeed, double maxAcceleration, int visionRadius, int visionAngle) {
        this.maxSpeed = maxSpeed;
        this.maxAcceleration = maxAcceleration;
        this.visionRadius = visionRadius;
        this.visionAngle = visionAngle;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public double getMaxAcceleration() {
        return maxAcceleration;
    }

    public void setMaxAcceleration(double maxAcceleration) {
        this.maxAcceleration = maxAcceleration;
    }

    public int getVisionRadius() {
        return visionRadius;
    }

    public void setVisionRadius(int visionRadius) {
        this.visionRadius = visionRadius;
    }

    public int getVisionAngle() {
        return visionAngle;
    }

    public void setVisionAngle(int visionAngle) {
        this.visionAngle = visionAngle;
    }
}
