package meli.tmr.sistemasolar.modelo;

public abstract class Planeta {

    private Float velocidadAngularPorDia;
    private Float distanciaALaTierraEnKm;
    private Boolean movimientoEnSentidoHorario;

    public Float getVelocidadAngularPorDia() {
        return velocidadAngularPorDia;
    }

    public void setVelocidadAngularPorDia(Float velocidadAngularPorDia) {
        this.velocidadAngularPorDia = velocidadAngularPorDia;
    }

    public Float getDistanciaALaTierraEnKm() {
        return distanciaALaTierraEnKm;
    }

    public void setDistanciaALaTierraEnKm(Float distanciaALaTierraEnKm) {
        this.distanciaALaTierraEnKm = distanciaALaTierraEnKm;
    }

    public Boolean getMovimientoEnSentidoHorario() {
        return movimientoEnSentidoHorario;
    }

    public void setMovimientoEnSentidoHorario(Boolean movimientoEnSentidoHorario) {
        this.movimientoEnSentidoHorario = movimientoEnSentidoHorario;
    }

    public double getPosicionEnX(Integer diaNumero){
        // Todos los planetas tienen X inicial = 0
        return 0 + this.getDistanciaALaTierraEnKm() * Math.cos(90 + this.getVelocidadAngularPorDia() * diaNumero * 24);
    }

    public double getPosicionEnY(Integer diaNumero){
        // Todos los planetas tienen Y inicial = su distancia al sol
        double ecuation = this.getDistanciaALaTierraEnKm() * Math.sin(90 + this.getVelocidadAngularPorDia() * diaNumero * 24);
        ecuation = this.getMovimientoEnSentidoHorario() ? ecuation * (-1) : ecuation;
        return this.getDistanciaALaTierraEnKm() + ecuation;
    }

}
