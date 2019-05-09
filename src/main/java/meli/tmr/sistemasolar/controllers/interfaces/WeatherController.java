package meli.tmr.sistemasolar.controllers.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/clima")
public interface WeatherController {

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET, produces = "application/json")
    ResponseEntity getWeather(@RequestParam(value = "dia") String day);
}
