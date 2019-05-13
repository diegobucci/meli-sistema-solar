package meli.tmr.solarsystem.controllers.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;


@RestController
public interface WeatherController {

    @RequestMapping(value = {"", "/clima"}, method = RequestMethod.GET, produces = "application/json")
    ResponseEntity getWeather(@RequestParam(value = "dia") String day);

    @ExceptionHandler(MissingServletRequestParameterException.class)
    ResponseEntity handleMissingParams(MissingServletRequestParameterException ex);

    @RequestMapping(value = {"/reporteProximosDiezAnios"}, method = RequestMethod.GET, produces = "application/json")
    ResponseEntity getWeatherReportForTenYears();
}
