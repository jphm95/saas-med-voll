package med.voll_api.domain.consulta.validaciones.reserva;

import med.voll_api.domain.ValidacionExcpetion;
import med.voll_api.domain.consulta.DatosReservaConsulta;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class ValidacionFueraHorario implements ValidadorDeConsultas {

    public void validar(DatosReservaConsulta datos) {
        var fechaConsulta = datos.fecha();
        var domingo = fechaConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var horarioAntesApertura = fechaConsulta.getHour() < 7;
        var horarioDespuesCierre = fechaConsulta.getHour() > 18;
        if (domingo || horarioAntesApertura || horarioDespuesCierre) {
            throw new ValidacionExcpetion("La consulta no puede ser realizada fuera del horario de consultas");
        }
    }
}
