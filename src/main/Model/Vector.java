package main.Model;

public class Vector implements Comparable<Vector>{

    private double x;
    private double y;

    public Vector(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Vector(double x, double y, double magnitude){
        this(x, y);
        scalarDivide(getMagnitude());
        scalarMultiply(magnitude);
    }

    public Vector(){
        this(0, 0);
    }

    public static Vector subtract(Vector v1, Vector v2){
        return new Vector(v1.x - v2.x, v1.y - v2.y);
    }

    public static Vector add(Vector v1, Vector v2){
        return new Vector(v1.x + v2.x, v1.y + v2.y);
    }

    public static Vector divide(Vector v, double divisor){
        return new Vector(v.x / divisor, v.y / divisor);
    }

    public static Vector multiply(Vector v, double multiplier){
        return new Vector(v.x * multiplier, v.y * multiplier);
    }

    public void addVector(Vector other){
        x += other.x;
        y += other.y;
    }

    public void subtractVector(Vector other){
        x -= other.x;
        y -= other.y;
    }

    public void scalarDivide(double divisor){
        if (divisor == 0){
            return;
        }
        x /= divisor;
        y /= divisor;
    }

    public void scalarMultiply(double multiplier){
        x *= multiplier;
        y *= multiplier;
    }

    public double getAngle(){
        return Math.toDegrees(Math.atan2(y, x));
    }

    public double getMagnitude(){
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    @Override
    public String toString(){
        return String.format("(%.3f, %.3f)", x, y);
    }

    @Override
    public int compareTo(Vector other) {
        return (int) (getMagnitude() - other.getMagnitude());
    }

    public boolean isInsideRect(Vector topLeft, Vector botRight){
        return x >= topLeft.x && y >= topLeft.y && x <= botRight.x && y <= botRight.y;
    }
}
