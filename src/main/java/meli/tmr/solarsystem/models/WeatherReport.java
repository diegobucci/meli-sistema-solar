package meli.tmr.solarsystem.models;

public class WeatherReport {
    // están en español porque es la salida ante el request /reporteProximosDiezAnios
    private Integer diasDeSequia;
    private Integer diasDeLluvia;
    private Integer diaDeMayorLluvia;
    private Integer diasOptimos;
    private double maximoPerimetroDiaDeLluvia;

    public WeatherReport(){
        this.setDiasDeSequia(0);
        this.setDiasDeLluvia(0);
        this.setDiasOptimos(0);
        this.setMaximoPerimetroDiaDeLluvia(0);
    }

    public Integer getDiasDeSequia() {
        return diasDeSequia;
    }

    public void setDiasDeSequia(Integer diasDeSequia) {
        this.diasDeSequia = diasDeSequia;
    }

    public Integer getDiasDeLluvia() {
        return diasDeLluvia;
    }

    public void setDiasDeLluvia(Integer diasDeLluvia) {
        this.diasDeLluvia = diasDeLluvia;
    }

    public Integer getDiaDeMayorLluvia() {
        return diaDeMayorLluvia;
    }

    public void setDiaDeMayorLluvia(Integer diaDeMayorLluvia) {
        this.diaDeMayorLluvia = diaDeMayorLluvia;
    }

    public Integer getDiasOptimos() {
        return diasOptimos;
    }

    public void setDiasOptimos(Integer diasOptimos) {
        this.diasOptimos = diasOptimos;
    }

    public double getMaximoPerimetroDiaDeLluvia() {
        return maximoPerimetroDiaDeLluvia;
    }

    public void setMaximoPerimetroDiaDeLluvia(double maximoPerimetroDiaDeLluvia) {
        this.maximoPerimetroDiaDeLluvia = maximoPerimetroDiaDeLluvia;
    }
}
