package meli.tmr.sistemasolar.modelo;

public class Ferengi extends Planeta {

    public Ferengi(){
        super();
        this.setDistanciaALaTierraEnKm(500);
        this.setVelocidadAngularPorDia(1);
        this.setMovimientoEnSentidoHorario(true);
        this.setCivilizacion("Ferengi");
    }

}
