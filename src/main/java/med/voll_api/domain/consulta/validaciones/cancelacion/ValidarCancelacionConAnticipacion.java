package med.voll_api.domain.consulta.validaciones.cancelacion;

import med.voll_api.domain.ValidacionExcpetion;
import med.voll_api.domain.consulta.ConsultaRepository;
import med.voll_api.domain.consulta.DatosCancelarConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidarCancelacionConAnticipacion implements ValidadorCancelamientoConsulta {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Override
    public void validar(DatosCancelarConsulta datos) {
        var consulta = consultaRepository.getReferenceById(datos.idConsulta());
        var ahora = LocalDateTime.now();
        var diferenciaHoras = Duration.between(ahora, consulta.getFecha()).toHours();

        if (diferenciaHoras < 24) {
            throw new ValidacionExcpetion("La consulta solo puede ser cancelada a más tardar 24 horas antes");
        }
    }

}
