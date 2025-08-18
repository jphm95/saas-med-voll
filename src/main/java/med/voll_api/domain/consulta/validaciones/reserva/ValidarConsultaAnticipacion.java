package med.voll_api.domain.consulta.validaciones.reserva;

import med.voll_api.domain.ValidacionExcpetion;
import med.voll_api.domain.consulta.DatosReservaConsulta;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidarConsultaAnticipacion implements ValidadorDeConsultas {

    public void validar(DatosReservaConsulta datos) {
        var fechaConsulta = datos.fecha();
        var ahora = LocalDateTime.now();
        var diferenciaMinutos = Duration.between(ahora, fechaConsulta).toMinutes();
        if (diferenciaMinutos < 30) {
            throw new ValidacionExcpetion("Debes seleccionar un horario con mínimo 30 minutos de anticipacion");
        }
    }

}
