package com.ambientese.grupo5.Controller.UsuarioController;

import com.ambientese.grupo5.Services.UsuarioService.DeletarUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Usuarios")
public class DeletarUsuarioController {

        private final DeletarUsuarioService usuarioService;

        @Autowired
        public DeletarUsuarioController(DeletarUsuarioService usuarioService) {
            this.usuarioService = usuarioService;
        }

    @DeleteMapping("/Delete/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        boolean deleted = usuarioService.deleteUsuario(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
