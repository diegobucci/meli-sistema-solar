package meli.tmr.sistemasolar.modelo;

public abstract class Planeta {

    private Integer velocidadAngularPorDia;
    private Integer distanciaALaTierraEnKm;
    private Boolean movimientoEnSentidoHorario;

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
        // Todos los planetas tienen X inicial = 0
        return 0 + this.getDistanciaALaTierraEnKm() * Math.cos(90 + this.getVelocidadAngularPorDia() * diaNumero * 24);
    }

    private double getPosicionEnY(Integer diaNumero){
        // Todos los planetas tienen Y inicial = su distancia al sol
        double ecuation = this.getDistanciaALaTierraEnKm() * Math.sin(90 + this.getVelocidadAngularPorDia() * diaNumero * 24);
        ecuation = this.getMovimientoEnSentidoHorario() ? ecuation * (-1) : ecuation;
        return this.getDistanciaALaTierraEnKm() + ecuation;
    }

    public Posicion getPosicion(Integer diaNumero){
        return new Posicion(getPosicionEnX(diaNumero), getPosicionEnY(diaNumero));
    }

}
