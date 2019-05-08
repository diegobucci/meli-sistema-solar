package meli.tmr.sistemasolar.controllers.interfaces;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/clima")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface WeatherController {

    @GET
    Response getWeather(@QueryParam("dia") String day);
}
