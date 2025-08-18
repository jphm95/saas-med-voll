package med.voll_api.domain.consulta.validaciones.reserva;

import med.voll_api.domain.ValidacionExcpetion;
import med.voll_api.domain.consulta.ConsultaRepository;
import med.voll_api.domain.consulta.DatosReservaConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarMedicoConOtraConsulta implements ValidadorDeConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;

    public void validar(DatosReservaConsulta datos) {
        var medicoTieneOtraCosultaEnMismoHorario = consultaRepository.existsByMedicoIdAndFechaAndMotivoCancelacionIsNull(datos.idMedico(),
                datos.fecha());
        if (medicoTieneOtraCosultaEnMismoHorario) {
            throw new ValidacionExcpetion("El medico ya tiene una consulta en este horario");
        }
    }
}
