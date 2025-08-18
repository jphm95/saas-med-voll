package med.voll_api.domain.consulta.validaciones;

import med.voll_api.domain.ValidacionExcpetion;
import med.voll_api.domain.consulta.DatosReservaConsulta;
import med.voll_api.domain.medico.MedicoRepository;

public class ValidarMedicoActivo implements ValidadorDeConsultas {

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
