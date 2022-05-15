package main.Model.Stats;

public class MapStats {

    // size of each grid square
    private double gridSquareSize;
    // chance of each square to be spawned as a wall
    private double spawnChance;
    // min number of neighbors to spawn
    private int threshold;
    // random
    private long rSeed;

    public MapStats(double gridSquareSize, double spawnChance, int threshold, long rSeed){
        this.gridSquareSize = gridSquareSize;
        this.spawnChance = spawnChance;
        this.threshold = threshold;
        this.rSeed = rSeed;
    }

    public double getGridSquareSize() {
        return gridSquareSize;
    }

    public void setGridSquareSize(double gridSquareSize) {
        this.gridSquareSize = gridSquareSize;
    }

    public double getSpawnChance() {
        return spawnChance;
    }

    public void setSpawnChance(double spawnChance) {
        this.spawnChance = spawnChance;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public long getRSeed() {
        return rSeed;
    }

    public void setRSeed(long rSeed) {
        this.rSeed = rSeed;
    }
}
