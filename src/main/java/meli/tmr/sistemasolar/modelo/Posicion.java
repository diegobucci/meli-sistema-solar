package meli.tmr.sistemasolar.modelo;

public class Posicion {

    private Float X;
    private Float Y;

    public Posicion(Float X, Float Y){
        this.setX(X);
        this.setY(Y);
    }

    public Float getX() {
        return X;
    }

    public void setX(Float x) {
        X = x;
    }

    public Float getY() {
        return Y;
    }

    public void setY(Float y) {
        Y = y;
    }
}
