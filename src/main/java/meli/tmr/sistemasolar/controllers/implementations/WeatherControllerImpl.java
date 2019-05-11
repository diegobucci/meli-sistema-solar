package meli.tmr.sistemasolar.controllers.implementations;

import meli.tmr.sistemasolar.controllers.interfaces.WeatherController;
import meli.tmr.sistemasolar.daos.interfaces.DayWeatherDAO;

import meli.tmr.sistemasolar.daos.interfaces.WeatherReportDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MissingServletRequestParameterException;


@Controller
public class WeatherControllerImpl implements WeatherController {

    private static final String NUMERO_POSITIVO_ERROR = "El día debe ser un número positivo";
    private static final String EL_FORMATO_ES_INCORRECTO = "El día debe estar expresado como un número, el formato es incorrecto";
    private static final String DIA_PARA_OBTENER_EL_CLIMA_ERROR = "Es necesario indicar el número del día para obtener el clima";

    @Autowired
    private DayWeatherDAO dayWeatherDAO;

    @Autowired
    private WeatherReportDAO weatherReportDAO;

    @Override
    public ResponseEntity getWeather(String day) {
        ResponseEntity responseError = validateErrorsGetWeather(day);
        if(responseError != null) return responseError;
        return ResponseEntity.ok().body(dayWeatherDAO.getByDay(Integer.parseInt(day)));
    }

    private ResponseEntity validateErrorsGetWeather(String day){

        //TODO: validar dia para mayores a 10 anios
        if(day == null || StringUtils.isEmpty(day)) return errorNoDayRequest();
        try {
            int dayNumber = Integer.parseInt(day);
            if(dayNumber < 1) return ResponseEntity.badRequest().body(NUMERO_POSITIVO_ERROR);
            return null;
        } catch (NumberFormatException e){
            return ResponseEntity.badRequest().body(EL_FORMATO_ES_INCORRECTO);
        }
    }

    private ResponseEntity errorNoDayRequest(){
        return ResponseEntity.badRequest().body(DIA_PARA_OBTENER_EL_CLIMA_ERROR);
    }

    @Override
    public ResponseEntity handleMissingParams(MissingServletRequestParameterException ex) {
        return errorNoDayRequest();
    }

    @Override
    public ResponseEntity getWeatherReportForTenYears() {
        return ResponseEntity.ok().body(weatherReportDAO.get());
    }

}
