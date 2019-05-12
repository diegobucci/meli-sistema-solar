package meli.tmr.sistemasolar.models;

import meli.tmr.sistemasolar.services.CalculatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Planet {
    private static final Logger LOGGER = LoggerFactory.getLogger(Planet.class);

    private Integer angularVelocityInGradesPerDay;
    private Integer sunDistanceInKm;
    private Boolean movementClockwise;
    private String civilizationName;
    private Position position;
    private int actualGrade;


    public Planet(String civilizationName, Integer angularVelocityInGradesPerDay, Integer sunDistanceInKm, Boolean movementClockwise) {
        setCivilizationName(civilizationName);
        setAngularVelocityInGradesPerDay(angularVelocityInGradesPerDay);
        setSunDistanceInKm(sunDistanceInKm);
        setMovementClockwise(movementClockwise);
        init();
    }

    private void init() {
        // Inicialmente se encuentran con una fase de 90 grados con respecto al sol
        setPosition(new Position(0, getSunDistanceInKm()));
        setActualGrade(90);
        LOGGER.info(getCivilizationName() + " inicializado girando en órbita");
    }

    protected void advanceOneDay() {
        LOGGER.info("Se produce movimiento de " + getCivilizationName() + " en sentido " + (getMovementClockwise() ? "horario" : "anti horario"));
        rotate();
        updatePosition();
        LOGGER.info("Posición de " + getCivilizationName() + " actualizada");
    }

    private void rotate() {
        if (getMovementClockwise()) rotateClockwise();
        else rotateAntiClockwise();
    }

    private void rotateClockwise() {
        int actualGradeUpdated = getActualGrade() - getAngularVelocityInGradesPerDay();
        if (actualGradeUpdated < 0) actualGradeUpdated += 360;
        setActualGrade(actualGradeUpdated);
    }

    private void rotateAntiClockwise() {
        int actualGradeUpdated = getActualGrade() + getAngularVelocityInGradesPerDay();
        if (actualGradeUpdated >= 360) actualGradeUpdated -= 360;
        setActualGrade(actualGradeUpdated);
    }

    /**********
     * X = centroDeGiroEnX + radio * cos( posicionActualEnGradosPasadosARadianes )
     * Y = centroDeGiroEnY + radio * sin( posicionActualEnGradosPasadosARadiane )
     * El centro de giro es el sol que tiene posición (0,0)
     ***********/
    private void updatePosition () {
        getPosition().setX(getXCoordinate());
        getPosition().setY(getYCoordinate());
    }

    private double getXCoordinate () {
        return getSunDistanceInKm() * CalculatorUtil.getCos(getActualGrade());
    }

    private double getYCoordinate () {
        return getSunDistanceInKm() * CalculatorUtil.getSin(getActualGrade());
    }

    /******* GETTERS AND SETTERS *********/
    public Integer getAngularVelocityInGradesPerDay () {
        return angularVelocityInGradesPerDay;
    }

    public void setAngularVelocityInGradesPerDay (Integer angularVelocityInGradesPerDay){
        this.angularVelocityInGradesPerDay = angularVelocityInGradesPerDay;
    }

    public Integer getSunDistanceInKm () {
        return sunDistanceInKm;
    }

    public void setSunDistanceInKm (Integer sunDistanceInKm){
        this.sunDistanceInKm = sunDistanceInKm;
    }

    public Boolean getMovementClockwise () {
        return movementClockwise;
    }

    public void setMovementClockwise (Boolean movementClockwise){
        this.movementClockwise = movementClockwise;
    }

    public Position getPosition () {
        return position;
    }

    public void setPosition (Position position){
        this.position = position;
    }

    public int getActualGrade () {
        return actualGrade;
    }

    public void setActualGrade ( int actualGrade){
        this.actualGrade = actualGrade;
    }

    public String getCivilizationName () {
        return civilizationName;
    }

    public void setCivilizationName (String civilizationName){
        this.civilizationName = civilizationName;
    }
}
