package main.Model;

import main.Model.Organisms.Bird;
import main.Model.Stats.BirdStats;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Boids {

    private final MapGrid mapGrid;
    private final ArrayList<Bird> birds;
    private final Random random;
    private final BirdStats birdStats;

    public Boids(int numBirds, MapGrid mapGrid, Random random, BirdStats birdStats){

        this.mapGrid = mapGrid;
        this.birdStats = birdStats;

        this.random = random;
        birds = new ArrayList<>();
        for (int i=0; i<numBirds; i++){
            addBird();
        }
    }

    public void nextFrame(){
        for (Bird b: birds){
            ArrayList<Bird> inVisionConeBirds = b.getBirdsInVisionCone(birds);
            Vector steering = new Vector();
            steering.addVector(Vector.multiply(b.getAlignment(inVisionConeBirds),
                    birdStats.getAlignmentFactor() * birdStats.getMaxSpeed()));
            steering.addVector(Vector.multiply(b.getCoherence(inVisionConeBirds),
                    birdStats.getCoherenceFactor() * birdStats.getMaxSpeed()));
            steering.addVector(Vector.multiply(b.getSeparation(inVisionConeBirds),
                    birdStats.getSeparationFactor() * birdStats.getMaxSpeed()));
            steering.addVector(Vector.multiply(b.getAvoidCollision(mapGrid.getEdgesWalls(), birdStats.getBoundaryMargin()),
                    (birdStats.getAlignmentFactor() + birdStats.getCoherenceFactor() + birdStats.getSeparationFactor()) * birdStats.getMaxSpeed()));
            b.steer(steering);
        }

        Iterator<Bird> b = birds.iterator();
        Bird bird;
        while (b.hasNext()){
            bird = b.next();
            bird.move();
            if (bird.isDead(mapGrid)){
                b.remove();
            }
        }
    }

    public ArrayList<Bird> getBirds(){
        return birds;
    }

    public void addBird(){
        birds.add(new Bird(new Vector(mapGrid.getScreenSize().getX() * (0.1 + random.nextDouble() * 0.8),
                mapGrid.getScreenSize().getY() * (0.1 + random.nextDouble() * 0.8)),
                new Vector(random.nextDouble()-0.5, random.nextDouble()-0.5, 1),
                new Vector(),
                birdStats));
    }

    public void removeBird(){
        if (!birds.isEmpty()){
            birds.remove(0);
        }
    }
}
