package med.voll_api.domain.medico;

import med.voll_api.domain.direccion.Direccion;

public record DatosDetalleMedico(
        Long id,
        String nombre,
        String telefono,
        String email,
        String documento,
        Especialidad especialidad,
        Direccion direccion
) {
    public DatosDetalleMedico(Medico medico) {
        this(medico.getId(), medico.getNombre(), medico.getTelefono(), medico.getEmail(),
                medico.getDocumento(), medico.getEspecialidad(), medico.getDireccion());
    }
}
