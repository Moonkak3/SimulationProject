package main.Model.Stats;

public class BirdStats extends OrganismStats {

    private double alignmentFactor;
    private double coherenceFactor;
    private double separationFactor;
    private double boundaryMargin;

    public BirdStats(double maxSpeed, double maxAcceleration, int visionRadius, int visionAngle,
                     double alignmentFactor, double coherenceFactor, double separationFactor,
                     double boundaryMargin) {
        super(maxSpeed, maxAcceleration, visionRadius, visionAngle);
        this.alignmentFactor = alignmentFactor;
        this.coherenceFactor = coherenceFactor;
        this.separationFactor = separationFactor;
        this.boundaryMargin = boundaryMargin;
    }

    public double getAlignmentFactor() {
        return alignmentFactor;
    }

    public void setAlignmentFactor(double alignmentFactor) {
        this.alignmentFactor = alignmentFactor;
    }

    public double getCoherenceFactor() {
        return coherenceFactor;
    }

    public void setCoherenceFactor(double coherenceFactor) {
        this.coherenceFactor = coherenceFactor;
    }

    public double getSeparationFactor() {
        return separationFactor;
    }

    public void setSeparationFactor(double separationFactor) {
        this.separationFactor = separationFactor;
    }

    public double getBoundaryMargin() {
        return boundaryMargin;
    }

    public void setBoundaryMargin(double boundaryMargin) {
        this.boundaryMargin = boundaryMargin;
    }

}
