package meli.tmr.sistemasolar.models;

import com.google.api.client.util.Lists;
import meli.tmr.sistemasolar.exceptions.SolarSystemException;

import java.util.ArrayList;
import java.util.List;

public class SolarSystem {

    private List<Planet> planets;

    public SolarSystem(){
        setPlanets(new ArrayList<>());
    }

    public SolarSystem(List<Planet> planets) throws SolarSystemException {
        if(planets == null || planets.size() != 3) throw new SolarSystemException("Se esperan tres planetas");
        setPlanets(planets);
    }

    public List<Planet> getPlanets() {
        return planets;
    }

    public void setPlanets(List<Planet> planets) {
        this.planets = planets;
    }

    public void addPlanet(Planet planet){
        if(getPlanets() != null && getPlanets().size() == 3) throw new SolarSystemException("El sistema solar ya contiene tres planetas");
        getPlanets().add(planet);
    }


    public void advanceOneDay() {
        getPlanets().forEach(p -> p.advanceOneDay());
    }
}
