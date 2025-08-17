package med.voll_api.domain.paciente;

public record DatosListaPaciente(
        Long id,
        String nombre,
        String email,
        String telefono,
        String documento
) {
    public DatosListaPaciente(Paciente paciente) {
        this(paciente.getId(), paciente.getNombre(), paciente.getEmail(), paciente.getTelefono(), paciente.getDocumentoIdentidad());
    }
}
