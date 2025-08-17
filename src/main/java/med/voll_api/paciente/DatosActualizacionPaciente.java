package med.voll_api.paciente;

import jakarta.validation.constraints.NotNull;
import med.voll_api.direccion.DatosDireccion;

public record DatosActualizacionPaciente(
        @NotNull Long id,
        String nombre,
        String telefono,
        DatosDireccion datosDireccion
) {
}
