package meli.tmr.solarsystem;

import meli.tmr.solarsystem.controllers.implementations.WeatherControllerImpl;
import meli.tmr.solarsystem.daos.implementations.DayWeatherDAOImpl;
import meli.tmr.solarsystem.daos.implementations.WeatherReportDAOImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@AutoConfigureMockMvc
@ContextConfiguration(classes = {WeatherControllerImpl.class, DayWeatherDAOImpl.class, WeatherReportDAOImpl.class})
@WebMvcTest
public class TestRequest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testErrorRequestNegativeNumber() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/clima?dia=-10")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        Assertions.assertEquals(WeatherControllerImpl.NUMERO_POSITIVO_ERROR, result.getResponse().getContentAsString());
    }

    @Test
    void testErrorRequestWrongFormat() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/clima?dia=string")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        Assertions.assertEquals(WeatherControllerImpl.EL_FORMATO_ES_INCORRECTO, result.getResponse().getContentAsString());
    }

    @Test
    void testErrorRequestWithoutDay() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/clima?dia=")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        Assertions.assertEquals(WeatherControllerImpl.DIA_PARA_OBTENER_EL_CLIMA_ERROR, result.getResponse().getContentAsString());
    }

    @Test
    void testErrorRequestNoParams() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/clima")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        Assertions.assertEquals(WeatherControllerImpl.DIA_PARA_OBTENER_EL_CLIMA_ERROR, result.getResponse().getContentAsString());
    }

}
