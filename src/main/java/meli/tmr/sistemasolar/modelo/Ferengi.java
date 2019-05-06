package meli.tmr.sistemasolar.modelo;

public class Ferengi extends Civilizacion {

    public Ferengi(){
        this.setDistanciaALaTierraEnKm(new Float(500));
        this.setVelocidadAngularPorDia(new Float(1));
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
