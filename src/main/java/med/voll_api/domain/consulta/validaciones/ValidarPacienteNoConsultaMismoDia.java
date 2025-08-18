package med.voll_api.domain.consulta.validaciones;

import med.voll_api.domain.ValidacionExcpetion;
import med.voll_api.domain.consulta.ConsultaRepository;
import med.voll_api.domain.consulta.DatosReservaConsulta;

public class ValidarPacienteNoConsultaMismoDia implements ValidadorDeConsultas {

    private ConsultaRepository consultaRepository;

    public void validar(DatosReservaConsulta datos) {
        var primerHorario = datos.fecha().withHour(7);
        var ultimoHorario = datos.fecha().withHour(18);
        var pacienteTieneOtraConsultaEnDia = consultaRepository.existsByPacienteeIdAndFechaBetween(
                datos.idPaciente(), primerHorario, ultimoHorario);
        if (pacienteTieneOtraConsultaEnDia) {
            throw new ValidacionExcpetion("El paciente ya tiene una consulta en este dia");
        }
    }
}
