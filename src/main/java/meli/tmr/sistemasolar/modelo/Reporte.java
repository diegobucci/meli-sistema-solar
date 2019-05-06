package meli.tmr.sistemasolar.modelo;

public class Reporte {
    private Integer cantidadDeDiasConSequia;
    private Integer cantidadDeDiasDeLluvias;
    private Integer diaPicoMaximoDeLluvia;
    private Integer cantidadDeDiasOptimos;

    public Reporte(){
        this.setCantidadDeDiasConSequia(0);
        this.setCantidadDeDiasDeLluvias(0);
        this.setCantidadDeDiasOptimos(0);
    }

    public Integer getCantidadDeDiasConSequia() {
        return cantidadDeDiasConSequia;
    }

    public void setCantidadDeDiasConSequia(Integer cantidadDeDiasConSequia) {
        this.cantidadDeDiasConSequia = cantidadDeDiasConSequia;
    }

    public Integer getCantidadDeDiasDeLluvias() {
        return cantidadDeDiasDeLluvias;
    }

    public void setCantidadDeDiasDeLluvias(Integer cantidadDeDiasDeLluvias) {
        this.cantidadDeDiasDeLluvias = cantidadDeDiasDeLluvias;
    }

    public Integer getDiaPicoMaximoDeLluvia() {
        return diaPicoMaximoDeLluvia;
    }

    public void setDiaPicoMaximoDeLluvia(Integer diaPicoMaximoDeLluvia) {
        this.diaPicoMaximoDeLluvia = diaPicoMaximoDeLluvia;
    }

    public Integer getCantidadDeDiasOptimos() {
        return cantidadDeDiasOptimos;
    }

    public void setCantidadDeDiasOptimos(Integer cantidadDeDiasOptimos) {
        this.cantidadDeDiasOptimos = cantidadDeDiasOptimos;
    }
}
