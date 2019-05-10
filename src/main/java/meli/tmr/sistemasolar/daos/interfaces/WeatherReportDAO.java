package meli.tmr.sistemasolar.daos.interfaces;

import meli.tmr.sistemasolar.models.WeatherReport;

public interface WeatherReportDAO {
    WeatherReport get();
    void save(WeatherReport weatherReport);
}
