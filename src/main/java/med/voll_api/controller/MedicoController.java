package med.voll_api.controller;

import jakarta.validation.Valid;
import med.voll_api.medico.DatosActualizacionMedico;
import med.voll_api.medico.DatosListaMedico;
import med.voll_api.medico.DatosRegistroMedico;
import med.voll_api.medico.Medico;
import med.voll_api.medico.MedicoRepository;
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
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;

    @Transactional
    @PostMapping
    public void registrarMedico(@RequestBody @Valid DatosRegistroMedico datos) {
        medicoRepository.save(new Medico(datos));


    }

    @GetMapping
    public Page<DatosListaMedico> listarMedicos(@PageableDefault(size = 10, sort = {"nombre"}) Pageable paginacion) {
        return medicoRepository.findAllByActivoTrue(paginacion).map(DatosListaMedico::new);
    }

    @Transactional
    @PutMapping
    public void actualizarMedico(@RequestBody @Valid DatosActualizacionMedico datos) {
        var medico = medicoRepository.getReferenceById(datos.id());
        medico.actualizarDatos(datos);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public void eliminarMedico(@PathVariable Long id) {
        var medico = medicoRepository.getReferenceById(id);
        medico.eliminar();
    }


}
