package med.voll_api.domain.consulta.validaciones.reserva;

import med.voll_api.domain.ValidacionExcpetion;
import med.voll_api.domain.consulta.ConsultaRepository;
import med.voll_api.domain.consulta.DatosReservaConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarPacienteNoConsultaMismoDia implements ValidadorDeConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;

    public void validar(DatosReservaConsulta datos) {
        var primerHorario = datos.fecha().withHour(7);
        var ultimoHorario = datos.fecha().withHour(18);
        var pacienteTieneOtraConsultaEnDia = consultaRepository.existsByPacienteIdAndFechaBetween(
                datos.idPaciente(), primerHorario, ultimoHorario);
        if (pacienteTieneOtraConsultaEnDia) {
            throw new ValidacionExcpetion("El paciente ya tiene una consulta en este dia");
        }
    }
}
