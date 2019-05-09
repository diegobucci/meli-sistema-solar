package meli.tmr.sistemasolar.daos.implementations;

import meli.tmr.sistemasolar.daos.interfaces.DayWeatherDAO;
import meli.tmr.sistemasolar.models.DayWeather;
import org.springframework.stereotype.Service;

@Service
public class DayWeatherDAOImpl implements DayWeatherDAO  {

    @Override
    public DayWeather getByDay(Integer day) {
        return new DayWeather(day,"lluvia");
    }

    @Override
    public void save(DayWeather dayWeather) {

    }
}
