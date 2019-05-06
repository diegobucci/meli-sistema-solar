package meli.tmr.sistemasolar.modelo;

public abstract class Civilizacion {

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

    public abstract Float getPosicionEnX(Integer diaNumero);

    public abstract Float getPosicionEnY(Integer diaNumero);

}
