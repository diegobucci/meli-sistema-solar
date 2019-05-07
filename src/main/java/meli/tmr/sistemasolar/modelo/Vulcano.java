package meli.tmr.sistemasolar.modelo;

public class Vulcano extends Planeta {

    public Vulcano(){
        super();
        this.setDistanciaALaTierraEnKm(1000);
        this.setVelocidadAngularPorDia(5);
        this.setMovimientoEnSentidoHorario(false);
        this.setCivilizacion("Vulcano");
    }
}
