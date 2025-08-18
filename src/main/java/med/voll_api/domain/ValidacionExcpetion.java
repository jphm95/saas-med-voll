package med.voll_api.domain;

public class ValidacionExcpetion extends RuntimeException {

    public ValidacionExcpetion(String mensaje) {
        super(mensaje);
    }
}
