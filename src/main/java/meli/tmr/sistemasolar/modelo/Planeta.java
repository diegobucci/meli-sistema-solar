package meli.tmr.sistemasolar.modelo;

public abstract class Planeta {

    private Integer velocidadAngularPorDia; // expresado en grados/dia
    private Integer distanciaALaTierraEnKm;
    private Boolean movimientoEnSentidoHorario;
    private Posicion posicionDelSol;

    public Integer getVelocidadAngularPorDia() {
        return velocidadAngularPorDia;
    }

    public void setVelocidadAngularPorDia(Integer velocidadAngularPorDia) {
        this.velocidadAngularPorDia = velocidadAngularPorDia;
    }

    public Integer getDistanciaALaTierraEnKm() {
        return distanciaALaTierraEnKm;
    }

    public void setDistanciaALaTierraEnKm(Integer distanciaALaTierraEnKm) {
        this.distanciaALaTierraEnKm = distanciaALaTierraEnKm;
    }

    public Boolean getMovimientoEnSentidoHorario() {
        return movimientoEnSentidoHorario;
    }

    public void setMovimientoEnSentidoHorario(Boolean movimientoEnSentidoHorario) {
        this.movimientoEnSentidoHorario = movimientoEnSentidoHorario;
    }

    private double getPosicionEnX(Integer diaNumero) throws Exception {
        if(this.getPosicionDelSol() == null) throw new Exception();
        double ecuation = this.getDistanciaALaTierraEnKm() * Math.cos((Math.PI/2) + Math.toRadians(this.getVelocidadAngularPorDia() * diaNumero));
        // si el sentido de movimiento es antihorario deberia moverse para la izquierda, es decir, el seno deberia dar negativo
        ecuation = this.getMovimientoEnSentidoHorario() ? ecuation : ecuation * (-1);
        return this.getPosicionDelSol().getX() + ecuation;
    }

    private double getPosicionEnY(Integer diaNumero) throws Exception {
        if(this.getPosicionDelSol() == null) throw new Exception();
        return this.getPosicionDelSol().getY() + this.getDistanciaALaTierraEnKm() * Math.sin((Math.PI/2) + Math.toRadians(this.getVelocidadAngularPorDia() * diaNumero));
    }

    public Posicion getPosicion(Integer diaNumero) {
        try {
            return new Posicion(getPosicionEnX(diaNumero), getPosicionEnY(diaNumero));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Posicion getPosicionDelSol() {
        return posicionDelSol;
    }

    public void setPosicionDelSol(Posicion posicionDelSol) {
        this.posicionDelSol = posicionDelSol;
    }
}
