package meli.tmr.solarsystem.models;

public class Position {

    private double X;
    private double Y;

    public Position(double X, double Y){
        this.setX(X);
        this.setY(Y);
    }

    public double getX() {
        return X;
    }

    public void setX(double x) {
        X = x;
    }

    public double getY() {
        return Y;
    }

    public void setY(double y) {
        Y = y;
    }

    public static String getPositionAsString(Position position){
        return "Position (" + position.getX() + ", " + position.getY() + ")";
    }
}
