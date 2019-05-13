package meli.tmr.solarsystem;

import meli.tmr.solarsystem.controllers.implementations.WeatherControllerImpl;
import meli.tmr.solarsystem.controllers.interfaces.WeatherController;
import meli.tmr.solarsystem.daos.implementations.DayWeatherDAOImpl;
import meli.tmr.solarsystem.daos.implementations.WeatherReportDAOImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ContextConfiguration(classes = {WeatherControllerImpl.class, DayWeatherDAOImpl.class, WeatherReportDAOImpl.class})
@WebMvcTest
public class TestRequest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testErrorRequestNegativeNumber() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/clima?dia=-10")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
        MockHttpServletResponse response = result.getResponse();
        response.getStatus()
        Assertions.assertEquals(WeatherControllerImpl.NUMERO_POSITIVO_ERROR, result.getResponse());
    }
/*
    @Test
    void testErrorRequestWrongFormat() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(
                new URL("http://localhost:" + port + "/clima?dia=asd").toString(), String.class);
        Assertions.assertEquals(WeatherControllerImpl.EL_FORMATO_ES_INCORRECTO, response.getBody());
    }

    @Test
    void testErrorRequestNoParams() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(
                new URL("http://localhost:" + port + "/clima").toString(), String.class);
        Assertions.assertEquals(WeatherControllerImpl.DIA_PARA_OBTENER_EL_CLIMA_ERROR, response.getBody());
    }*/
}
