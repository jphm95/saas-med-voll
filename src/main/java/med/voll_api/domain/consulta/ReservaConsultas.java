package med.voll_api.domain.consulta;

import med.voll_api.domain.ValidacionExcpetion;
import med.voll_api.domain.consulta.validaciones.cancelacion.ValidadorCancelamientoConsulta;
import med.voll_api.domain.consulta.validaciones.reserva.ValidadorDeConsultas;
import med.voll_api.domain.medico.Medico;
import med.voll_api.domain.medico.MedicoRepository;
import med.voll_api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaConsultas {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private List<ValidadorCancelamientoConsulta> validadorCancelamiento;

    @Autowired
    List<ValidadorDeConsultas> validadores;

    public DatosDetalleConsulta reservar(DatosReservaConsulta datos) {

        if (!pacienteRepository.existsById(datos.idPaciente())) {
            throw new ValidacionExcpetion("No existe un paciente con ese id");
        }

        if (datos.idMedico() != null && !medicoRepository.existsById(datos.idMedico())) {
            throw new ValidacionExcpetion("No existe un Medico con ese id");
        }

        //Validadores
        validadores.forEach(v -> v.validar(datos));


        var medico = elegirMedico(datos);
        if (medico == null) {
            throw new ValidacionExcpetion("No existe un Medico disponible con ese horario");
        }
        var paciente = pacienteRepository.findById(datos.idPaciente()).get();
        var consulta = new Consulta(null, medico, paciente, datos.fecha(), null);
        consultaRepository.save(consulta);
        return new DatosDetalleConsulta(consulta);

    }

    private Medico elegirMedico(DatosReservaConsulta datos) {
        if (datos.idMedico() != null) {
            return medicoRepository.getReferenceById(datos.idMedico());
        }
        if (datos.especialidad() == null) {
            throw new ValidacionExcpetion("Debe seleccionar una especialidad si no has elegido un médico");
        }
        return medicoRepository.elegirMedicoAleatorioDisponibleEnLaFecha(datos.especialidad(), datos.fecha());
    }

    public void cancelar(DatosCancelarConsulta datos) {
        if (!consultaRepository.existsById(datos.idConsulta())) {
            throw new ValidacionExcpetion("No existe una consulta con ese id");
        }

        validadorCancelamiento.forEach(v -> v.validar(datos));

        var consulta = consultaRepository.getReferenceById(datos.idConsulta());
        consulta.cancelar(datos.motivo());
    }
}
