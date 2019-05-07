package meli.tmr.sistemasolar.modelo;

public class Betasoide extends Planeta {

    public Betasoide(){
        this.setDistanciaALaTierraEnKm(new Float(2000));
        this.setVelocidadAngularPorDia(new Float(3));
        this.setMovimientoEnSentidoHorario(true);
    }

}
