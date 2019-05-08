package meli.tmr.sistemasolar.controllers.implementations;

import meli.tmr.sistemasolar.controllers.interfaces.WeatherController;
import meli.tmr.sistemasolar.daos.interfaces.DayWeatherDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.core.Response;

@Controller
public class WeatherControllerImpl implements WeatherController {

    @Autowired
    private DayWeatherDAO dayWeatherDAO;

    @Override
    public Response getWeather(String day) {
        if(day == null) return Response.status(403).entity("Es necesario un valor para obtener el clima").build();
        try {
            int dayNumber = Integer.parseInt(day);
            if(dayNumber < 1) return Response.status(403).entity("El día debe ser un número positivo").build();
            return Response.status(200).entity(dayWeatherDAO.getByDay(dayNumber)).build();
        } catch (NumberFormatException e){
            return Response.status(403).entity("El día debe estar expresado como un número").build();
        }
    }

}
