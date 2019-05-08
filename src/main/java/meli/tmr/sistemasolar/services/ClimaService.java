package meli.tmr.sistemasolar.services;

import meli.tmr.sistemasolar.exceptions.AniosException;
import meli.tmr.sistemasolar.modelo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClimaService {

    private static final Integer DIAS_POR_ANIO = 365;
    private Calculator calculator;
    private Posicion posicionDelSol = new Posicion(0,0);

    @Autowired
    public ClimaService(Calculator calculator) {
        this.calculator = calculator;
    }

    public ReporteClima obtenerReporte(SistemaSolar sistemaSolar, Integer cantidadDeAnios) throws Exception {
        if(cantidadDeAnios < 1 || cantidadDeAnios > 10) throw new AniosException("Los años deben variar entre 1 y 10");
        // todo: validator en el controller

        ReporteClima reporte = new ReporteClima();
        Double maxPerimetro = 0.0;

        for (int diaNro = 0; diaNro < getTotalDays(cantidadDeAnios); diaNro++) {

            List<Posicion> posiciones = getPosiciones(sistemaSolar.getPlanetas(), diaNro);
            ClimaEnum clima = obtenerClima(posiciones);

            switch(clima){
                case LLUVIA:
                    reporte.setCantidadDeDiasDeLluvias(reporte.getCantidadDeDiasDeLluvias() + 1);
                    if(calculator.obtenerPerimetro(posiciones) > maxPerimetro){
                        reporte.setDiaPicoMaximoDeLluvia(diaNro);
                    }
                    break;
                case SEQUIA:
                    reporte.setCantidadDeDiasConSequia(reporte.getCantidadDeDiasConSequia() + 1);
                    break;
                case OPTIMO:
                    reporte.setCantidadDeDiasOptimos(reporte.getCantidadDeDiasOptimos() + 1);
                    break;
                case INDETERMINADO:
                    // logger ?
                    break;
            }
        }
        return reporte;
    }

    private int getTotalDays(Integer cantidadDeAnios) {
        return cantidadDeAnios * DIAS_POR_ANIO;
    }

    public ClimaEnum obtenerClima(List<Posicion> posicionesPlanetas) throws Exception {
        if(posicionesPlanetas == null || posicionesPlanetas.size() != 3) throw new Exception("Se necesitan tres posiciones para predecir el clima");
        ClimaEnum clima = ClimaEnum.INDETERMINADO;
        if(calculator.estanAlineados(posicionesPlanetas.get(0), posicionesPlanetas.get(1), posicionesPlanetas.get(2))){
            if(calculator.estanAlineados(posicionesPlanetas.get(0), posicionesPlanetas.get(2), posicionDelSol)){
                clima = ClimaEnum.SEQUIA;
            } else {
                clima = ClimaEnum.OPTIMO;
            }
        } else if(calculator.isInside(posicionesPlanetas, new Posicion(0,0))) {
            clima = ClimaEnum.LLUVIA;
        }
        return clima;
    }

    private List<Posicion> getPosiciones(List<Planeta> planetas, Integer diaNumero){
        List<Posicion> posiciones = new ArrayList<>();
        planetas.forEach(p -> posiciones.add(p.getPosicion(diaNumero)));
        return posiciones;
    }



}
