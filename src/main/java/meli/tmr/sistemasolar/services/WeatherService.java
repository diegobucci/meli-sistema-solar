package meli.tmr.sistemasolar.services;

import meli.tmr.sistemasolar.exceptions.AniosException;
import meli.tmr.sistemasolar.exceptions.SistemaSolarException;
import meli.tmr.sistemasolar.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class WeatherService {

    private CalculatorUtil calculatorUtil;
    public static final Position SUN_POSITION = new Position(0.0, 0.0);
    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherService.class);
    public static final Integer DAYS_PER_YEAR = 365;

    @Autowired
    public WeatherService(CalculatorUtil calculatorUtil) {
        this.calculatorUtil = calculatorUtil;
    }

    public WeatherReport obtenerReporte(SolarSystem solarSystem, Integer years) {
        LOGGER.info("Obtener reporte para los próximos " + years + " años");
        if(years < 1 || years > 10) throw new AniosException("Los años deben variar entre 1 y 10");
        if(solarSystem.getPlanets() == null || solarSystem.getPlanets().size() != 3) throw new SistemaSolarException("Solo es posible predecir el clima de sistemas con 3 planetas");
        // todo: validator en el controller

        WeatherReport report = new WeatherReport();
        double maxPerimeter = 0;

        for (int dayNumber = 0; dayNumber < getDays(years); dayNumber++) {
            LOGGER.info("Día numero: " + dayNumber);
            solarSystem.getPlanets().forEach(p -> p.moveOneDay());
            WeatherEnum clima = obtenerClima(solarSystem.getPlanets());

            switch(clima){
                case RAIN:
                    report.setNumberOfRainyDays(report.getNumberOfRainyDays() + 1);
                    if(getPerimeter(solarSystem.getPlanets()) > maxPerimeter){
                        report.setDayOfGreatestRain(dayNumber);
                    }
                    break;
                case DROUGHT:
                    report.setNumberOfDroughtDays(report.getNumberOfDroughtDays() + 1);
                    break;
                case OPTIMUM:
                    report.setNumberOfOptimalDays(report.getNumberOfOptimalDays() + 1);
                    break;
            }
        }
        return report;
    }

    public WeatherEnum obtenerClima(List<Planet> planets){
        // Previamente se validó el tamaño del array así que no debería lanzarse la excepción IndexOutOfBounds
        Position position1 = planets.get(0).getPosition();
        Position position2 = planets.get(1).getPosition();
        Position position3 = planets.get(2).getPosition();

        WeatherEnum clima = WeatherEnum.UNDEFINED;
        if(calculatorUtil.areInline(position1, position2, position3)){
            // Los planetas se encuentran alineados entre sí
            clima = WeatherEnum.OPTIMUM;
            if(calculatorUtil.areInline(position1, position3, SUN_POSITION)){
                clima = WeatherEnum.DROUGHT; // Si además están alineados con el sol el clima es SEQUIA
            }
        } else if(calculatorUtil.isInside(position1, position2, position3, SUN_POSITION)) {
            // Si no están alineados y forman un triangulo que contiene al sol entonces llueve
            clima = WeatherEnum.RAIN;
        }
        return clima;
    }

    private double getPerimeter(List<Planet> planets) {
        return this.calculatorUtil.getPerimeter(planets.get(0).getPosition(), planets.get(1).getPosition(), planets.get(2).getPosition());
    }

    private Integer getDays(int years){
        return years * DAYS_PER_YEAR;
    }

}
