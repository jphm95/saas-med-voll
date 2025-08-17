package med.voll_api.domain.medico;

import jakarta.validation.constraints.NotNull;
import med.voll_api.domain.direccion.DatosDireccion;

public record DatosActualizacionMedico(
        @NotNull Long id,
        String nombre,
        String telefono,
        DatosDireccion datosDireccion
) {
}
