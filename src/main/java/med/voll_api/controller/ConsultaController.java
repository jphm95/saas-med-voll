package med.voll_api.controller;

import jakarta.validation.Valid;
import med.voll_api.domain.consulta.DatosCancelarConsulta;
import med.voll_api.domain.consulta.DatosReservaConsulta;
import med.voll_api.domain.consulta.ReservaConsultas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private ReservaConsultas reserva;

    @PostMapping
    @Transactional
    public ResponseEntity reservar(@RequestBody @Valid DatosReservaConsulta datos) {


        reserva.reservar(datos);

        return ResponseEntity.ok(new DatosReservaConsulta());
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity cancelar(@RequestBody @Valid DatosCancelarConsulta datos) {
        reserva.cancelar(datos);
        return ResponseEntity.noContent().build();
    }

}
