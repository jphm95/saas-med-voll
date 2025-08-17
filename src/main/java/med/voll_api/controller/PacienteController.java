package med.voll_api.controller;

import jakarta.validation.Valid;
import med.voll_api.paciente.DatosActualizacionPaciente;
import med.voll_api.paciente.DatosRegistroPaciente;
import med.voll_api.paciente.Paciente;
import med.voll_api.paciente.PacienteRepository;
import med.voll_api.paciente.datosListaPaciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Transactional
    @PostMapping
    public void registrarPaciente(@RequestBody @Valid DatosRegistroPaciente datos) {
        pacienteRepository.save(new Paciente(datos));

    }

    @GetMapping
    public Page<datosListaPaciente> listaPacientes(@PageableDefault(size = 10, sort = {"nombre"}) Pageable pageable) {
        return pacienteRepository.findByActivoTrue(pageable).map(datosListaPaciente::new);
    }

    @Transactional
    @PutMapping
    public void actualizarPaciente(@RequestBody @Valid DatosActualizacionPaciente datos) {
        var paciente = pacienteRepository.getReferenceById(datos.id());
        paciente.actualizarDatos(datos);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public void eliminarPaciente(@PathVariable Long id) {
        var paciente = pacienteRepository.getReferenceById(id);
        paciente.eliminar();

    }
}
