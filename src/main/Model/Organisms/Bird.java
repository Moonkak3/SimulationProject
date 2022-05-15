package main.Model.Organisms;

import main.Model.Stats.BirdStats;
import main.Model.MapGrid;
import main.Model.Vector;

import java.util.ArrayList;

public class Bird extends Organism{

    private BirdStats birdStats;

    public Bird(Vector pos, Vector v, Vector a, BirdStats birdStats) {
        super(pos, v, a, birdStats);
        this.birdStats = birdStats;
    }


    public ArrayList<Bird> getBirdsInVisionCone(ArrayList<Bird> birds){
        ArrayList<Bird> inVisionConeBirds = new ArrayList<>();
        for (Bird b: birds){
            if (inVisionCone(b.pos)){
                inVisionConeBirds.add(b);
            }
        }
        return inVisionConeBirds;
    }

    // returns unit vector of alignment
    public Vector getAlignment(ArrayList<Bird> inVisionConeBirds){
        if (inVisionConeBirds.isEmpty()){
            return new Vector();
        }
        Vector alignment = new Vector();
        for (Bird b: inVisionConeBirds){
            alignment.addVector(b.getV());
        }
        alignment.scalarDivide(inVisionConeBirds.size());
        alignment.scalarDivide(alignment.getMagnitude());
        return alignment;
    }

    // returns unit vector of coherence
    public Vector getCoherence(ArrayList<Bird> inVisionConeBirds){
        if (inVisionConeBirds.isEmpty()){
            return new Vector();
        }
        Vector coherence = new Vector();
        for (Bird b: inVisionConeBirds){
            coherence.addVector(new Vector(b.getX(), b.getY()));
        }
        coherence.scalarDivide(inVisionConeBirds.size());
        coherence.subtractVector(pos);
        coherence.scalarDivide(coherence.getMagnitude());
        return coherence;
    }

    // returns unit vector of separation
    public Vector getSeparation(ArrayList<Bird> inVisionConeBirds){
        if (inVisionConeBirds.isEmpty()){
            return new Vector();
        }
        Vector separation = new Vector();
        for (Bird b: inVisionConeBirds){
            Vector diff = Vector.subtract(pos, b.getPos());
            diff.scalarDivide(Math.pow(diff.getMagnitude(), 2));
            separation.addVector(diff);
        }
        separation.scalarDivide(inVisionConeBirds.size());
        separation.scalarDivide(separation.getMagnitude());
        return separation;
    }

    // wallPos is arraylist of only the wall positions (not index) surrounding the empty space,
    // inclusive of invisible walls on the edges of the map.
    // returns unit vector of avoiding collision
    public Vector getAvoidCollision(ArrayList<Vector> wallPositions, double boundaryMargin){
        ArrayList<Vector> collidable = new ArrayList<>();
        for (Vector currWallPos: wallPositions){
            if (inVisionCone(currWallPos, boundaryMargin)) {
                collidable.add(currWallPos);
            }
        }
        if (collidable.isEmpty()){
            return new Vector();
        }
        Vector separation = new Vector();

        for (Vector v: collidable){
            Vector diff = Vector.subtract(pos, v);
            diff.scalarDivide(Math.pow(diff.getMagnitude(), 2));
            separation.addVector(diff);
        }
        separation.scalarDivide(collidable.size());
        separation.scalarDivide(separation.getMagnitude());
        return separation;
    }

    // size is the distance from the center of the triangle to the points
    public double[][] getTriangle(double size, double theta){
        double angle = v.getAngle();

        // 2 arrays, one of x points one of y points
        double[][] points = new double[2][3];

        points[0][0] = pos.getX() + Math.cos(Math.toRadians(angle)) * size;
        points[1][0] = pos.getY() + Math.sin(Math.toRadians(angle)) * size;

        points[0][1] = pos.getX() + Math.cos(Math.toRadians(angle+180-theta)) * size;
        points[1][1] = pos.getY() + Math.sin(Math.toRadians(angle+180-theta)) * size;

        points[0][2] = pos.getX() + Math.cos(Math.toRadians(angle+180+theta)) * size;
        points[1][2] = pos.getY() + Math.sin(Math.toRadians(angle+180+theta)) * size;

        return points;
    }

    public void steer(Vector steering){
        a.addVector(steering);
        if (a.getMagnitude() > stats.getMaxAcceleration()){
            a.scalarDivide(a.getMagnitude());
            a.scalarMultiply(stats.getMaxAcceleration());
        }
    }

    public boolean isDead(MapGrid mapGrid){
//        return !pos.isInsideRect(new Vector(), mapGrid.getMapSize()) || mapGrid.getBool(pos);
        return mapGrid.getBool(pos);
    }

    @Override
    public String toString() {
        return "Bird{" +
                "pos=" + pos +
                ", v=" + v +
                ", a=" + a +
                '}';
    }
}
