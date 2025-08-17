package med.voll_api.paciente;

public record datosListaPaciente(
        Long id,
        String nombre,
        String email,
        String telefono,
        String documento
) {
    public datosListaPaciente(Paciente paciente) {
        this(paciente.getId(), paciente.getNombre(), paciente.getEmail(), paciente.getTelefono(), paciente.getDocumentoIdentidad());
    }
}
