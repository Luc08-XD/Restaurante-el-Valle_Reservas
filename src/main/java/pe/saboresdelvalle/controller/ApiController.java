package pe.saboresdelvalle.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.saboresdelvalle.model.Plato;
import pe.saboresdelvalle.model.Reserva;

/**
 * Controlador REST: devuelve JSON, no vistas.
 * @RestController = @Controller + @ResponseBody en todos sus métodos.
 * Todas sus rutas viven bajo /api/ para separarlas de las rutas de las vistas.
 */
@RestController
@RequestMapping("/api")
public class ApiController {

    private final HomeController homeController;

    // Reutiliza las listas en memoria del controlador web (en un proyecto real irían en un @Service)
    public ApiController(HomeController homeController) {
        this.homeController = homeController;
    }

    // GET http://localhost:8080/api/platos
    @GetMapping("/platos")
    public List<Plato> listarPlatos() {
        return homeController.getPlatos();
    }

    // GET http://localhost:8080/api/platos/2
    @GetMapping("/platos/{id}")
    public ResponseEntity<Plato> buscarPlato(@PathVariable Long id) {
        return homeController.getPlatos().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET http://localhost:8080/api/reservas
    @GetMapping("/reservas")
    public List<Reserva> listarReservas() {
        return homeController.getReservas();
    }
}
