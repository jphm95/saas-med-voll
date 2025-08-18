package med.voll_api.domain.consulta.validaciones.reserva;

import med.voll_api.domain.ValidacionExcpetion;
import med.voll_api.domain.consulta.DatosReservaConsulta;
import med.voll_api.domain.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarMedicoActivo implements ValidadorDeConsultas {

    @Autowired
    private MedicoRepository medicoRepository;

    public void validar(DatosReservaConsulta datos) {
        if (datos.idMedico() == null) {
            return;
        }
        var medicoEstaActivo = medicoRepository.findActivoById(datos.idMedico());
        if (!medicoEstaActivo) {
            throw new ValidacionExcpetion("Consulta no puede ser reservada con medico excluido");
        }
    }
}
