package meli.tmr.solarsystem.daos.interfaces;

import meli.tmr.solarsystem.models.WeatherReport;

public interface WeatherReportDAO {
    WeatherReport get();
    void save(WeatherReport weatherReport);
}
