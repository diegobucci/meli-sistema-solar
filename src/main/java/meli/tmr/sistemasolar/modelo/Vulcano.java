package meli.tmr.sistemasolar.modelo;

public class Vulcano extends Planeta {

    public Vulcano(){
        this.setDistanciaALaTierraEnKm(1000);
        this.setVelocidadAngularPorDia(5);
        this.setMovimientoEnSentidoHorario(false);
    }
}
