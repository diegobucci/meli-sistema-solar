package meli.tmr.sistemasolar.modelo;

public class Vulcano extends Civilizacion {

    public Vulcano(){
        this.setDistanciaALaTierraEnKm(new Float(1000));
        this.setVelocidadAngularPorDia(new Float(5));
        this.setMovimientoEnSentidoHorario(false);
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
