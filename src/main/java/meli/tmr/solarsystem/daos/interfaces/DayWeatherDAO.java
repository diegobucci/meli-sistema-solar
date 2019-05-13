package meli.tmr.solarsystem.daos.interfaces;

import meli.tmr.solarsystem.models.DayWeather;

public interface DayWeatherDAO {
    DayWeather getByDay(Integer day);
    void save(DayWeather dayWeather);
}
