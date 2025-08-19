package med.voll_api.domain.medico;

import jakarta.persistence.EntityManager;
import med.voll_api.domain.consulta.Consulta;
import med.voll_api.domain.direccion.DatosDireccion;
import med.voll_api.domain.paciente.DatosRegistroPaciente;
import med.voll_api.domain.paciente.Paciente;
import med.voll_api.domain.paciente.PacienteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    PacienteRepository pacienteRepository;


    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("Debería devolver null cuando el médico buscado existe pero no esta disponible en esa fecha")
    void elegirMedicoAleatorioDisponibleEnLaFechaTestEscenario1() {
        //Given o arrange
        var lunesSiguiente10Am = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10, 0);
        var medico = registrarMedico("medico1", "medico@mail.com", "123456789", Especialidad.CARDIOLOGIA);
        var paciente = registrarPaciente("Paciente1", "paciente@gmail.com", "123897789");
        registrarConsulta(medico, paciente, lunesSiguiente10Am);
        //When o act
        var medicoLibre = medicoRepository.elegirMedicoAleatorioDisponibleEnLaFecha(Especialidad.CARDIOLOGIA,
                lunesSiguiente10Am);
        //Then o assert
        assertThat(medicoLibre).isNull();
    }

    @Test
    @DisplayName("Debería devolver medico cuando el médico buscado esta disponible en esa fecha")
    void elegirMedicoAleatorioDisponibleEnLaFechaTestEscenario2() {
        //Given o arrange
        var lunesSiguiente10Am = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10, 0);
        var medico = registrarMedico("medico1", "medico@mail.com", "123456789", Especialidad.CARDIOLOGIA);
        //When o act
        var medicoLibre = medicoRepository.elegirMedicoAleatorioDisponibleEnLaFecha(Especialidad.CARDIOLOGIA,
                lunesSiguiente10Am);
        //Then o assert
        assertThat(medicoLibre).isEqualTo(medico);
    }

    private void registrarConsulta(Medico medico, Paciente paciente, LocalDateTime fecha) {
        em.persist(new Consulta(null, medico, paciente, fecha));
    }

    private Medico registrarMedico(String nombre, String email, String documento, Especialidad especialidad) {
        var medico = new Medico(datosMedico(nombre, email, documento, especialidad));
        em.persist(medico);
        return medico;
    }

    private Paciente registrarPaciente(String nombre, String email, String documento) {
        var paciente = new Paciente(datosPaciente(nombre, email, documento));
        em.persist(paciente);
        return paciente;
    }

    private DatosRegistroMedico datosMedico(String nombre, String email, String documento, Especialidad especialidad) {
        return new DatosRegistroMedico(
                nombre,
                email,
                "123456789",
                documento,
                especialidad,
                datosDireccion()
        );
    }

    private DatosRegistroPaciente datosPaciente(String nombre, String email, String documento) {
        return new DatosRegistroPaciente(
                nombre,
                email,
                "1234567",
                documento,
                datosDireccion()
        );
    }

    private DatosDireccion datosDireccion() {
        return new DatosDireccion(
                "calle x",
                "123",
                "1 ",
                "zonacool",
                "ciudad z",
                "20103",
                "ALOS"
        );
    }
}