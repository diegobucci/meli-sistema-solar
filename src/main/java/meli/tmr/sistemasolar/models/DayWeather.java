package meli.tmr.sistemasolar.models;

public class DayWeather {
    private Integer dia;
    private String clima;

    public DayWeather(){}

    public DayWeather(Integer dia, String clima){
        setDia(dia);
        setClima(clima);
    }

    public Integer getDia() {
        return dia;
    }

    public void setDia(Integer dia) {
        this.dia = dia;
    }

    public String getClima() {
        return clima;
    }

    public void setClima(String clima) {
        this.clima = clima;
    }

}
