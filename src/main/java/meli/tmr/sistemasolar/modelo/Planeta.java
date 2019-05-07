package meli.tmr.sistemasolar.modelo;

public abstract class Planeta {

    private Integer velocidadAngularPorDia;
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

    private double getPosicionEnX(Integer diaNumero){
        // Todos los planetas tienen X inicial = posicionDelSolEnX
        double ecuation = this.getDistanciaALaTierraEnKm() * Math.cos((Math.PI/2) + this.getVelocidadAngularPorDia() * diaNumero * 24);
        // si el sentido de movimiento es antihorario deberia moverse para la izquierda, es decir, el seno deberia dar negativo
        ecuation = this.getMovimientoEnSentidoHorario() ? ecuation : ecuation * (-1);
        return this.getPosicionDelSol().getX() + ecuation;
    }

    private double getPosicionEnY(Integer diaNumero){
        // Todos los planetas tienen Y inicial = su distancia al sol * + posicionDelSolEnY
        double  posicionInicialEnY = this.getPosicionDelSol().getY() + this.getDistanciaALaTierraEnKm();
        return posicionInicialEnY + this.getDistanciaALaTierraEnKm() * Math.sin((Math.PI/2) + this.getVelocidadAngularPorDia() * diaNumero * 24);
    }

    public Posicion getPosicion(Integer diaNumero){
        return new Posicion(getPosicionEnX(diaNumero), getPosicionEnY(diaNumero));
    }

    public Posicion getPosicionDelSol() {
        return posicionDelSol;
    }

    public void setPosicionDelSol(Posicion posicionDelSol) {
        this.posicionDelSol = posicionDelSol;
    }
}
