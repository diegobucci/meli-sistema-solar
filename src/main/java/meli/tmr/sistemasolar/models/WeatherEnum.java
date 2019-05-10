package meli.tmr.sistemasolar.models;

public enum WeatherEnum {
    RAIN("lluvia"),
    DROUGHT("sequía"),
    OPTIMUM("óptimo"),
    UNDEFINED("indefinido");

    private final String weather;

    WeatherEnum(String weather){
        this.weather = weather;
    }

    public String getWeather() {
        return this.weather;
    }
}
