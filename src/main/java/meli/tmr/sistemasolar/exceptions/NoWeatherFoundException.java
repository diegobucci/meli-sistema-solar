package meli.tmr.sistemasolar.exceptions;

public class NoWeatherFoundException extends RuntimeException {
    public NoWeatherFoundException(String msg){
        super(msg);
    }
}
