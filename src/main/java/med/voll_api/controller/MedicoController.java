package med.voll_api.controller;

import jakarta.validation.Valid;
import med.voll_api.domain.medico.DatosActualizacionMedico;
import med.voll_api.domain.medico.DatosDetalleMedico;
import med.voll_api.domain.medico.DatosListaMedico;
import med.voll_api.domain.medico.DatosRegistroMedico;
import med.voll_api.domain.medico.Medico;
import med.voll_api.domain.medico.MedicoRepository;
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
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;

    @Transactional
    @PostMapping
    public ResponseEntity registrarMedico(@RequestBody @Valid DatosRegistroMedico datos,
                                          UriComponentsBuilder uriComponentsBuilder) {
        var medico = new Medico(datos);
        medicoRepository.save(medico);

        var uri = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DatosDetalleMedico(medico));
    }

    @GetMapping
    public ResponseEntity<Page<DatosListaMedico>> listarMedicos(@PageableDefault(size = 10, sort = {"nombre"}) Pageable paginacion) {
        var page = medicoRepository.findAllByActivoTrue(paginacion).map(DatosListaMedico::new);

        return ResponseEntity.ok(page);
    }

    @Transactional
    @PutMapping
    public ResponseEntity actualizarMedico(@RequestBody @Valid DatosActualizacionMedico datos) {
        var medico = medicoRepository.getReferenceById(datos.id());
        medico.actualizarDatos(datos);

        return ResponseEntity.ok(new DatosDetalleMedico(medico));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity eliminarMedico(@PathVariable Long id) {
        var medico = medicoRepository.getReferenceById(id);
        medico.eliminar();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detalleMedico(@PathVariable Long id) {
        var medico = medicoRepository.getReferenceById(id);

        return ResponseEntity.ok(new DatosDetalleMedico(medico));
    }


}
