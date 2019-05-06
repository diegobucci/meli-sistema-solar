package meli.tmr.sistemasolar.modelo;

public class Betasoide extends Civilizacion {

    public Betasoide(){
        this.setDistanciaALaTierraEnKm(new Float(2000));
        this.setVelocidadAngularPorDia(new Float(3));
        this.setMovimientoEnSentidoHorario(true);
    }

    @Override
    public Float getPosicionEnX(Integer diaNumero) {
        return null;
    }

    @Override
    public Float getPosicionEnY(Integer diaNumero) {
        return null;
    }
}
