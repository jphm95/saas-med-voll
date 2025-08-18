package med.voll_api.domain.consulta.validaciones;

import med.voll_api.domain.ValidacionExcpetion;
import med.voll_api.domain.consulta.ConsultaRepository;
import med.voll_api.domain.consulta.DatosReservaConsulta;

public class ValidarMedicoConOtraConsulta implements ValidadorDeConsultas {

    private ConsultaRepository consultaRepository;

    public void validar(DatosReservaConsulta datos) {
        var medicoTieneOtraCosultaEnMismoHorario = consultaRepository.existsByMedicoIdAndFecha(datos.idMedico(),
                datos.fecha());
        if (medicoTieneOtraCosultaEnMismoHorario) {
            throw new ValidacionExcpetion("El medico ya tiene una consulta en este horario");
        }
    }
}
