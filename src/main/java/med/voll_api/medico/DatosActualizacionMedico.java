package med.voll_api.medico;

import jakarta.validation.constraints.NotNull;
import med.voll_api.direccion.DatosDireccion;

public record DatosActualizacionMedico(
        @NotNull Long id,
        String nombre,
        String telefono,
        DatosDireccion datosDireccion
) {
}
