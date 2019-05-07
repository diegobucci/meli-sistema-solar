package meli.tmr.sistemasolar.modelo;

public class Ferengi extends Planeta {

    public Ferengi(){
        this.setDistanciaALaTierraEnKm(new Float(500));
        this.setVelocidadAngularPorDia(new Float(1));
        this.setMovimientoEnSentidoHorario(true);
    }

}
