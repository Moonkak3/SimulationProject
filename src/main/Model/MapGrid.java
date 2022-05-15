package main.Model;

import main.Model.Stats.MapStats;

import java.util.*;

public class MapGrid {

    // true if there is a wall
    private boolean[][] terrain2DArray;
    private final Vector mapSize;
    private Vector screenSize;
    private int lengthX;
    private int lengthY;
    private MapStats mapStats;
    private ArrayList<Vector> walls;
    private ArrayList<Vector> edgesWalls;
    private Random random;
    private int iterationsPassed;

    public MapGrid(Vector mapSize, MapStats mapStats){
        this.mapSize = mapSize;
        this.mapStats = mapStats;
        update();
    }

    public void update(){

        random = new Random(mapStats.getRSeed());
        iterationsPassed = 0;

        lengthX = (int) (mapSize.getX() / mapStats.getGridSquareSize());
        lengthY = (int) (mapSize.getY() / mapStats.getGridSquareSize());
        screenSize = new Vector(lengthX * mapStats.getGridSquareSize(), lengthY * mapStats.getGridSquareSize());
        terrain2DArray = new boolean[lengthX][lengthY];
        for (int x=0; x<lengthX; x++){
            for (int y=0; y<lengthY; y++){
                terrain2DArray[x][y] = false;
            }
        }
        // generate map
        generateMap();
        generateWalls();
        generateWallEdges();
    }

    public void reload(){
        random = new Random(mapStats.getRSeed());
        generatePerimeterWalls();
        generateWalls();
        generateWallEdges();
    }

    public void generatePerimeterWalls(){
        for (int x=0; x<lengthX; x++){
            terrain2DArray[x][0] = true;
            terrain2DArray[x][lengthY-1] = true;
        }
        for (int y=0; y<lengthY; y++){
            terrain2DArray[0][y] = true;
            terrain2DArray[lengthX-1][y] = true;
        }
    }

    public void nextIteration(){
        iterationsPassed++;
        boolean[][] newCopy = new boolean[lengthX][lengthY];
        for (int x=0; x<lengthX; x++){
            for (int y=0; y<lengthY; y++){
                newCopy[x][y] = getNumOfNeighbors(x, y) > mapStats.getThreshold();
                if (x==0 || y==0 || x==lengthX-1 || y==lengthY-1){
                    newCopy[x][y] = true;
                }
            }
        }
        terrain2DArray = newCopy;
        generateWalls();
        generateWallEdges();
    }

    public int getNumOfNeighbors(int x, int y){
        int count = 0;
        for (int dX=-1; dX<=1; dX++){
            for (int dY=-1; dY<=1; dY++){
                try {
                    if (dX == 0 && dY == 0){
                        continue;
                    }
                    if (terrain2DArray[x+dX][y+dY]){
                        count++;
                    }
                } catch (ArrayIndexOutOfBoundsException ignored){
                }
            }
        }
        return count;
    }

    public void generateMap(){
        // finish this part
        for (int x=0; x<lengthX; x++){
            for (int y=0; y<lengthY; y++){
                if (x==0 || y==0 || x==lengthX-1 || y==lengthY-1){
                    terrain2DArray[x][y] = true;
                } else{
                    terrain2DArray[x][y] = random.nextDouble() < mapStats.getSpawnChance();
                }
            }
        }
    }

    public void generateWalls(){
        walls = new ArrayList<>();
        for (int x=0; x<lengthX; x++){
            for (int y=0; y<lengthY; y++){
                if (terrain2DArray[x][y]){
                    walls.add(new Vector(x * mapStats.getGridSquareSize(), y * mapStats.getGridSquareSize()));
                }
            }
        }
    }

    public void generateWallEdges(){
        edgesWalls = new ArrayList<>();
        int[] directions = {-1, 0, 1, 0, -1};
        for (int x=0; x<lengthX; x++){
            for (int y=0; y<lengthY; y++){
                if (!terrain2DArray[x][y]){
                    continue;
                }
                for (int i=0; i<4; i++){
                    try{
                        if (!terrain2DArray[x+directions[i]][y+directions[i+1]]){
                            edgesWalls.add(new Vector((x + 0.5) * mapStats.getGridSquareSize(), (y + 0.5) * mapStats.getGridSquareSize()));
                            break;
                        }
                    } catch (IndexOutOfBoundsException ignored){
                    }
                }
            }
        }
    }

    public void floodFillInit(double pixelX, double pixelY, boolean bool){
        Queue<Vector> queue = new LinkedList<>();
        queue.add(new Vector((int) (pixelX / mapStats.getGridSquareSize()), (int) (pixelY / mapStats.getGridSquareSize())));
        while (true){
            Vector pos;
            try{
                pos = queue.remove();
            } catch (NoSuchElementException e){
                break;
            }
            int x = (int) pos.getX();
            int y = (int) pos.getY();
            if (x < 0 || x >= lengthX || y < 0 || y >= lengthY || terrain2DArray[x][y] == bool){
                continue;
            }
            terrain2DArray[x][y] = bool;
            queue.add(new Vector(x+1, y));
            queue.add(new Vector(x-1, y));
            queue.add(new Vector(x, y+1));
            queue.add(new Vector(x, y-1));
        }
        reload();
    }

    public void setBool(Vector pos, boolean bool){
        terrain2DArray[(int) (pos.getX() / mapStats.getGridSquareSize())][(int) (pos.getY() / mapStats.getGridSquareSize())] = bool;
        reload();
    }

    public boolean getBool(Vector pos){
        try{
            return terrain2DArray[(int) (pos.getX() / mapStats.getGridSquareSize())][(int) (pos.getY() / mapStats.getGridSquareSize())];
        } catch (ArrayIndexOutOfBoundsException e){
            return true;
        }
    }

    public Vector getScreenSize(){
        return screenSize;
    }


    public ArrayList<Vector> getWalls() {
        return walls;
    }

    public ArrayList<Vector> getEdgesWalls() {
        return edgesWalls;
    }

    public int getIterationsPassed() {
        return iterationsPassed;
    }

    public void setIterationsPassed(int iterationsPassed) {
        this.iterationsPassed = iterationsPassed;
    }

    public boolean[][] getTerrain2DArray() {
        return terrain2DArray;
    }

    public void setTerrain2DArray(boolean[][] terrain2DArray) {
        this.terrain2DArray = terrain2DArray;
    }

    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();
        for (int x=0; x<lengthX; x++){
            content.append(Arrays.toString(terrain2DArray[x])).append("\n");
        }
        return content.toString();
    }
}
