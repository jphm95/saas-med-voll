package med.voll_api.controller;

import med.voll_api.domain.consulta.DatosDetalleConsulta;
import med.voll_api.domain.consulta.DatosReservaConsulta;
import med.voll_api.domain.consulta.ReservaConsultas;
import med.voll_api.domain.medico.Especialidad;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<DatosReservaConsulta> datosReservaConsultaJson;

    @Autowired
    private JacksonTester<DatosDetalleConsulta> datosDetalleConsultaJson;

    @MockBean
    private ReservaConsultas reservaConsultas;

    @Test
    @DisplayName("Deberia devolver método http 400, cuando la request no tenga datos")
    @WithMockUser
    void reservar_escenario1() throws Exception {
        var response = mockMvc.perform(post("/consultas"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deberia devolver método http 200, cuando la request reciba un json válido")
    @WithMockUser
    void reservar_escenario2() throws Exception {

        var fecha = LocalDateTime.now().plusHours(7);
        var especialidad = Especialidad.CARDIOLOGIA;
        var datosDetalle = new DatosDetalleConsulta(null, 2l, 5l, fecha);
        when(reservaConsultas.reservar(any())).thenReturn(datosDetalle);

        var response = mockMvc.perform(post("/consultas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(datosReservaConsultaJson.write(
                                        new DatosReservaConsulta(2l, 5l, fecha, especialidad)
                                ).getJson()
                        )
                )
                .andReturn().getResponse();

        var expectedJson = datosDetalleConsultaJson.write(
                datosDetalle
        ).getJson();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(expectedJson);
    }
}
