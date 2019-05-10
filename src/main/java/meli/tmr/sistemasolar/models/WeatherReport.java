package meli.tmr.sistemasolar.models;

public class WeatherReport {
    private Integer numberOfDroughtDays;
    private Integer numberOfRainyDays;
    private Integer dayOfGreatestRain;
    private Integer numberOfOptimalDays;
    private double maxPerimeterRain;

    public WeatherReport(){
        this.setNumberOfDroughtDays(0);
        this.setNumberOfRainyDays(0);
        this.setNumberOfOptimalDays(0);
        this.setMaxPerimeterRain(0);
    }

    public Integer getNumberOfDroughtDays() {
        return numberOfDroughtDays;
    }

    public void setNumberOfDroughtDays(Integer numberOfDroughtDays) {
        this.numberOfDroughtDays = numberOfDroughtDays;
    }

    public Integer getNumberOfRainyDays() {
        return numberOfRainyDays;
    }

    public void setNumberOfRainyDays(Integer numberOfRainyDays) {
        this.numberOfRainyDays = numberOfRainyDays;
    }

    public Integer getDayOfGreatestRain() {
        return dayOfGreatestRain;
    }

    public void setDayOfGreatestRain(Integer dayOfGreatestRain) {
        this.dayOfGreatestRain = dayOfGreatestRain;
    }

    public Integer getNumberOfOptimalDays() {
        return numberOfOptimalDays;
    }

    public void setNumberOfOptimalDays(Integer numberOfOptimalDays) {
        this.numberOfOptimalDays = numberOfOptimalDays;
    }

    public double getMaxPerimeterRain() {
        return maxPerimeterRain;
    }

    public void setMaxPerimeterRain(double maxPerimeterRain) {
        this.maxPerimeterRain = maxPerimeterRain;
    }
}
