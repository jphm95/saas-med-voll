package med.voll_api.domain.paciente;

import med.voll_api.domain.direccion.Direccion;

public record DatosDetallePaciente(
        Long id,
        String nombre,
        String email,
        String telefono,
        String documento_identidad,
        Direccion direccion
) {
    public DatosDetallePaciente(Paciente paciente) {
        this(paciente.getId(), paciente.getNombre(), paciente.getEmail(),
                paciente.getTelefono(), paciente.getDocumentoIdentidad(),
                paciente.getDireccion());
    }
}
