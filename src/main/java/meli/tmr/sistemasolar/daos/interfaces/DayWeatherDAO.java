package meli.tmr.sistemasolar.daos.interfaces;

import meli.tmr.sistemasolar.models.DayWeather;

public interface DayWeatherDAO {
    DayWeather getByDay(Integer day);
    void save(DayWeather dayWeather);
}
