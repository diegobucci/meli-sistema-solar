package meli.tmr.sistemasolar.controller;

import meli.tmr.sistemasolar.modelo.Planeta;
import meli.tmr.sistemasolar.modelo.Posicion;
import meli.tmr.sistemasolar.modelo.Reporte;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClimaController {
    public static final String LLUVIA = "lluvia";
    public static final String SEQUIA = "sequia";
    public static final String OPTIMO = "optimo";
    private static final Integer DIAS_POR_ANIO = 365;

    public Reporte obtenerReporte(List<Planeta> planetas, Posicion posicionDelSol, Integer cantidadDeAnios) throws Exception {
        Reporte reporte = new Reporte();
        for (int diaNro = 0; diaNro < cantidadDeAnios * DIAS_POR_ANIO; diaNro++) {
            List<Posicion> posiciones = getPosiciones(planetas, diaNro);
            String clima = obtenerClima(posiciones);
            if(clima == LLUVIA){
                reporte.setCantidadDeDiasDeLluvias(reporte.getCantidadDeDiasDeLluvias() + 1);
                Double perimetro = obtenerPerimetro(posiciones);
                if(reporte.getDiaPicoMaximoDeLluvia() == null || perimetro > reporte.getDiaPicoMaximoDeLluvia()){
                    reporte.setDiaPicoMaximoDeLluvia(diaNro);
                }
            } else if (clima == OPTIMO){
                reporte.setCantidadDeDiasOptimos(reporte.getCantidadDeDiasOptimos() + 1);
            } else if( clima == SEQUIA) {
                reporte.setCantidadDeDiasConSequia(reporte.getCantidadDeDiasConSequia() + 1);
            }
        }
        return reporte;
    }

    public String obtenerClima(List<Posicion> posiciones) throws Exception {
        if(posiciones == null || posiciones.size() != 3) throw new Exception("Se necesitan tres posiciones para predecir el clima");
        return "";
    }

    public Double obtenerPerimetro(List<Posicion> posiciones) throws Exception {
        if(posiciones == null || posiciones.size() != 3) throw new Exception("Se necesitan tres posiciones para obtener el perimetro");
        return distanciaEntre(posiciones.get(0), posiciones.get(1))
                + distanciaEntre(posiciones.get(1), posiciones.get(2))
                + distanciaEntre(posiciones.get(2), posiciones.get(0));
    }

    private Double distanciaEntre(Posicion posicion1, Posicion posicion2){
        double diferenciaEnX = Math.abs(posicion1.getX() - posicion2.getX());
        double diferenciaEnY = Math.abs(posicion1.getY() - posicion2.getY());
        return Math.sqrt((diferenciaEnX) * (diferenciaEnX) + (diferenciaEnY) * (diferenciaEnY));
    }

    private List<Posicion> getPosiciones(List<Planeta> planetas, Integer diaNumero){
        List<Posicion> posiciones = new ArrayList<>();
        planetas.forEach(p -> posiciones.add(new Posicion(p.getPosicionEnX(diaNumero), p.getPosicionEnY(diaNumero))));
        return posiciones;
    }
}
