package meli.tmr.solarsystem.controllers.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

@RestController
public interface WeatherController {

    @GetMapping(value = {"", "/clima"}, produces = "application/json")
    ResponseEntity getWeather(@RequestParam(value = "dia") String day);

    @ExceptionHandler(MissingServletRequestParameterException.class)
    ResponseEntity handleMissingParams(MissingServletRequestParameterException ex);

    @GetMapping(value = {"/reporteProximosDiezAnios"}, produces = "application/json")
    ResponseEntity getWeatherReportForTenYears();
}
