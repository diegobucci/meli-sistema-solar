package meli.tmr.sistemasolar.model;

import meli.tmr.sistemasolar.exceptions.SistemaSolarException;

import java.util.ArrayList;
import java.util.List;

public class SolarSystem {

    private List<Planet> planets;

    public SolarSystem(){
        setPlanets(new ArrayList<>());
    }

    public SolarSystem(List<Planet> planets) throws SistemaSolarException {
        if(planets == null || planets.size() != 3) throw new SistemaSolarException("Se esperan tres planetas");
        setPlanets(planets);
    }

    public List<Planet> getPlanets() {
        return planets;
    }

    public void setPlanets(List<Planet> planets) {
        this.planets = planets;
    }

    public void addPlanet(Planet planet){
        if(getPlanets() != null && getPlanets().size() == 3) throw new SistemaSolarException("El sistema solar ya contiene tres planetas");
        getPlanets().add(planet);
    }


}
