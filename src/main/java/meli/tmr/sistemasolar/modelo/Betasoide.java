package meli.tmr.sistemasolar.modelo;

public class Betasoide extends Planeta {

    public Betasoide(){
        super();
        this.setDistanciaALaTierraEnKm(2000);
        this.setVelocidadAngularPorDia(3);
        this.setMovimientoEnSentidoHorario(true);
        this.setCivilizacion("Batasoide");
    }

}
