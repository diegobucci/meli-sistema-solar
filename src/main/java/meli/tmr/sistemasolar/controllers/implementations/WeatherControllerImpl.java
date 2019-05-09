package meli.tmr.sistemasolar.controllers.implementations;

import meli.tmr.sistemasolar.controllers.interfaces.WeatherController;
import meli.tmr.sistemasolar.daos.interfaces.DayWeatherDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MissingServletRequestParameterException;


@Controller
public class WeatherControllerImpl implements WeatherController {

    @Autowired
    private DayWeatherDAO dayWeatherDAO;

    @Override
    public ResponseEntity getWeather(String day) {
        if(day == null || StringUtils.isEmpty(day)) return errorNoDayRequest();
        try {
            int dayNumber = Integer.parseInt(day);
            if(dayNumber < 1) return ResponseEntity.badRequest().body("El día debe ser un número positivo");
            return ResponseEntity.ok().body(dayWeatherDAO.getByDay(dayNumber));
        } catch (NumberFormatException e){
            return ResponseEntity.badRequest().body("El día debe estar expresado como un número positivo");
        }
    }

    @Override
    public ResponseEntity handleMissingParams(MissingServletRequestParameterException ex) {
        return errorNoDayRequest();
    }

    private ResponseEntity errorNoDayRequest(){
        return ResponseEntity.badRequest().body("Es necesario indicar el número del día para obtener el clima");
    }

}
