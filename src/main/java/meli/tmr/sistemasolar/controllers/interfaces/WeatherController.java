package meli.tmr.sistemasolar.controllers.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/clima")
public interface WeatherController {

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET, produces = "application/json")
    ResponseEntity getWeather(@RequestParam(value = "dia") String day);

    @ExceptionHandler(MissingServletRequestParameterException.class)
    ResponseEntity handleMissingParams(MissingServletRequestParameterException ex);
}
