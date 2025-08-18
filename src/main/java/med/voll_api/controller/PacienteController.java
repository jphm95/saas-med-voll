package med.voll_api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll_api.domain.paciente.DatosActualizacionPaciente;
import med.voll_api.domain.paciente.DatosDetallePaciente;
import med.voll_api.domain.paciente.DatosListaPaciente;
import med.voll_api.domain.paciente.DatosRegistroPaciente;
import med.voll_api.domain.paciente.Paciente;
import med.voll_api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/pacientes")
@SecurityRequirement(name = "bearer-key")
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Transactional
    @PostMapping
    public ResponseEntity registrarPaciente(@RequestBody @Valid DatosRegistroPaciente datos,
                                            UriComponentsBuilder uriComponentsBuilder) {
        var paciente = new Paciente(datos);
        pacienteRepository.save(paciente);

        var uri = uriComponentsBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();

        return ResponseEntity.created(uri).body(new DatosDetallePaciente(paciente));

    }

    @GetMapping
    public ResponseEntity<Page<DatosListaPaciente>> listaPacientes(@PageableDefault(size = 10, sort = {"nombre"}) Pageable pageable) {
        var page = pacienteRepository.findByActivoTrue(pageable).map(DatosListaPaciente::new);
        return ResponseEntity.ok(page);
    }

    @Transactional
    @PutMapping
    public ResponseEntity actualizarPaciente(@RequestBody @Valid DatosActualizacionPaciente datos) {
        var paciente = pacienteRepository.getReferenceById(datos.id());
        paciente.actualizarDatos(datos);

        return ResponseEntity.ok(new DatosListaPaciente(paciente));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity eliminarPaciente(@PathVariable Long id) {
        var paciente = pacienteRepository.getReferenceById(id);
        paciente.eliminar();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detallePaciente(@PathVariable Long id) {
        var paciente = pacienteRepository.getReferenceById(id);

        return ResponseEntity.ok(new DatosDetallePaciente(paciente));

    }
}
