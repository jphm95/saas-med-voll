package med.voll_api.controller;

import jakarta.validation.Valid;
import med.voll_api.domain.usuario.DatosAutentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager manager;

    @PostMapping
    public ResponseEntity login(@RequestBody @Valid DatosAutentication datos) {
        var token = new UsernamePasswordAuthenticationToken(datos.login(), datos.password());
        var authentication = manager.authenticate(token);

        return ResponseEntity.ok().build();
    }
}
