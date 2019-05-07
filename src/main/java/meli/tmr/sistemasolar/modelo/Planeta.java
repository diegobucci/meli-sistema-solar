package meli.tmr.sistemasolar.modelo;

import meli.tmr.sistemasolar.utils.DoubleUtil;

public abstract class Planeta {

    private String civilizacion;
    private Integer velocidadAngularPorDia; // expresado en grados/dia
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

    private double getPosicionEnX(Integer diaNumero) {
        double ecuation = this.getDistanciaALaTierraEnKm() * Math.cos((Math.PI/2) + Math.toRadians(this.getVelocidadAngularPorDia() * diaNumero));
        ecuation = this.getMovimientoEnSentidoHorario() ? ecuation * (-1) : ecuation;
        return DoubleUtil.round(ecuation);
    }

    private double getPosicionEnY(Integer diaNumero) {
        return DoubleUtil.round(  this.getDistanciaALaTierraEnKm() * Math.sin((Math.PI/2) + Math.toRadians(this.getVelocidadAngularPorDia() * diaNumero)));
    }


    /**** Obtener coordenadas polares dependiendo del día *****
     *
     * X = centroDeGiroEnX + radio * cos( fase inicial + velocidad angular * numeroDedia )
     * Y = centroDeGiroEnY + radio * sin( fase inicial + velocidad angular * numeroDedia )
     *
     * El centro de giro será (0,0). Es la posición del sol.
     * Si el planeta gira en sentido horario, el resultado del seno (en Y) debe ser negativo.
     *
     * @param diaNumero
     * @return Posicion(X,Y)
     */
    public Posicion getPosicion(Integer diaNumero) {
        try {
            return new Posicion(getPosicionEnX(diaNumero), getPosicionEnY(diaNumero));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public void setCivilizacion(String civilizacion) {
        this.civilizacion = civilizacion;
    }
}
