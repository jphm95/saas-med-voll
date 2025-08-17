package med.voll_api.domain.paciente;

import jakarta.validation.constraints.NotNull;
import med.voll_api.domain.direccion.DatosDireccion;

public record DatosActualizacionPaciente(
        @NotNull Long id,
        String nombre,
        String telefono,
        DatosDireccion datosDireccion
) {
}
