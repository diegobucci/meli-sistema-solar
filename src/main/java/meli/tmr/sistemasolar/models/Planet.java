package meli.tmr.sistemasolar.models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Planet {

    // Las coordenadas del sol son (0,0) y los planetas giran alrrededor de ella.
    private Integer angularVelocityInGradesPerDay;
    private Integer sunDistanceInKm;
    private Boolean movementClockwise;
    private String civilizationName;
    private static final Logger LOGGER = LoggerFactory.getLogger(Planet.class);

    /*********
     * Previamente había pensado en calcular la posición a partir del día pero de esa forma se volvía
     * más complejo el cálculo para obtener la posición. Ya que debía siempre indicar la fase inicial
     * en la que se encontraba el planeta y además calcular los grados que se movió dependiendo del número del día.
     * Además de que dependiendo de si el movimiento era en sentido horario o no, debía multiplicar
     * al resultado del seno por (-1) para forzar el movimiento en sentido horario.
     *
     * Para simplificar este escenario, agregué un atributo posición que se va actualizando a medida que pasan los días.
     * También tuve que agregar un atributo 'grado actual' para poder llevar a cabo la obteción de la posición.
     */
    private Position position;
    private int actualGrade;


    public Planet(String civilizationName, Integer angularVelocityInGradesPerDay, Integer sunDistanceInKm, Boolean movementClockwise) {
        setCivilizationName(civilizationName);
        setAngularVelocityInGradesPerDay(angularVelocityInGradesPerDay);
        setSunDistanceInKm(sunDistanceInKm);
        setMovementClockwise(movementClockwise);
        init();
    }

    private void init(){
        // Inicialmente se encuentran con una fase de 90 grados con respecto al sol
        setPosition(new Position(0, getSunDistanceInKm()));
        setActualGrade(90);
        LOGGER.info(getCivilizationName() + " inicializado girando en órbita");
    }

    public void moveOneDay(){
        LOGGER.warn("Se produce movimiento de " + getCivilizationName() + " en sentido " + (getMovementClockwise() ? "horario" : "anti horario"));
        if(getMovementClockwise()) rotateClockwise();
        else rotateAntiClockwise();
        updatePosition();
        LOGGER.warn("Posición de " + getCivilizationName() + " actualizada");
        LOGGER.warn("Grado actual de " + getCivilizationName() + ": " + getActualGrade());
    }

    private void rotateClockwise(){
        int actualGradeUpdated = ((getActualGrade() - getAngularVelocityInGradesPerDay()) < 0)
                ? (getActualGrade() - getAngularVelocityInGradesPerDay() + 360)
                : (getActualGrade() - getAngularVelocityInGradesPerDay());
        setActualGrade(actualGradeUpdated);
    }

    private void rotateAntiClockwise(){
        int actualGradeUpdated = ((getActualGrade() + getAngularVelocityInGradesPerDay()) > 360)
                ? (getActualGrade() + getAngularVelocityInGradesPerDay() - 360)
                : (getActualGrade() + getAngularVelocityInGradesPerDay());
        setActualGrade(actualGradeUpdated);
    }

    /**********
     * X = centroDeGiroEnX + radio * cos( posicionActualEnGradosPasadosARadianes )
     * Y = centroDeGiroEnY + radio * sin( posicionActualEnGradosPasadosARadiane )
     * El centro de giro es el sol que tiene posición (0,0)
     ***********/
    private void updatePosition(){
        getPosition().setX(getXCoordinate());
        getPosition().setY(getYCoordinate());
    }

    private double getXCoordinate() {
        return getSunDistanceInKm() * Math.cos(Math.toRadians(getActualGrade()));
    }

    private double getYCoordinate() {
        return getSunDistanceInKm() * Math.sin(Math.toRadians(getActualGrade()));
    }

    /******* GETTERS AND SETTERS *********/
    public Integer getAngularVelocityInGradesPerDay() {
        return angularVelocityInGradesPerDay;
    }

    public void setAngularVelocityInGradesPerDay(Integer angularVelocityInGradesPerDay) {
        this.angularVelocityInGradesPerDay = angularVelocityInGradesPerDay;
    }

    public Integer getSunDistanceInKm() {
        return sunDistanceInKm;
    }

    public void setSunDistanceInKm(Integer sunDistanceInKm) {
        this.sunDistanceInKm = sunDistanceInKm;
    }

    public Boolean getMovementClockwise() {
        return movementClockwise;
    }

    public void setMovementClockwise(Boolean movementClockwise) {
        this.movementClockwise = movementClockwise;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getActualGrade() {
        return actualGrade;
    }

    public void setActualGrade(int actualGrade) {
        this.actualGrade = actualGrade == 360 ? 0 : actualGrade;
    }

    public String getCivilizationName() {
        return civilizationName;
    }

    public void setCivilizationName(String civilizationName) {
        this.civilizationName = civilizationName;
    }
}
