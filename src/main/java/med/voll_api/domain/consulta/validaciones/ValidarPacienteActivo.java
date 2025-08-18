package med.voll_api.domain.consulta.validaciones;

import med.voll_api.domain.ValidacionExcpetion;
import med.voll_api.domain.consulta.DatosReservaConsulta;
import med.voll_api.domain.paciente.PacienteRepository;

public class ValidarPacienteActivo implements ValidadorDeConsultas {

    private PacienteRepository pacienteRepository;

    public void validar(DatosReservaConsulta datos) {
        var pacienteActivo = pacienteRepository.findActivoById(datos.idPaciente());
        if (!pacienteActivo) {
            throw new ValidacionExcpetion("Consulta no puede ser reservada por paciente excluido");
        }
    }
}
