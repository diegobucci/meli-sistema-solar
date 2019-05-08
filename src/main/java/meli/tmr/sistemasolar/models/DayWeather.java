package meli.tmr.sistemasolar.models;

public class DayWeather {
    private String weather;
    private Integer day;

    public DayWeather(){}

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }
}
