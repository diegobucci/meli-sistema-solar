package meli.tmr.sistemasolar.modelo;

public class Vulcano extends Planeta {

    public Vulcano(){
        this.setDistanciaALaTierraEnKm(new Float(1000));
        this.setVelocidadAngularPorDia(new Float(5));
        this.setMovimientoEnSentidoHorario(false);
    }
}
