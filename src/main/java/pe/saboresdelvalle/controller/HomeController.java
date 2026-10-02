package pe.saboresdelvalle.controller;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pe.saboresdelvalle.model.Plato;
import pe.saboresdelvalle.model.Reserva;
import pe.saboresdelvalle.model.Testimonio;

/**
 * Controlador de vistas web (devuelve plantillas HTML de Thymeleaf).
 * La persistencia es temporal: listas en memoria que se reinician al apagar la aplicación.
 */
@Controller
public class HomeController {

    // ===== "Base de datos" en memoria =====
    private final List<Plato> platos = new ArrayList<>();
    private final List<Reserva> reservas = new ArrayList<>();
    private final List<Testimonio> testimonios = new ArrayList<>();
    private final List<String> galeria = List.of("img1.jpg", "img2.jpg", "img3.jpg", "img4.jpg");
    private final List<String> opcionesPersonas =
            List.of("1 - 2 personas", "3 - 4 personas", "5 - 6 personas", "Más de 6 personas");
    private final List<String> zonas = List.of("Salón principal", "Terraza", "Zona de bar");

    private long siguienteIdReserva = 1;

    // Tiempo que ocupa una reserva: no se acepta otra dentro de este intervalo
    private static final int DURACION_RESERVA_MIN = 60;
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("HH:mm");

    /** Datos iniciales cargados al arrancar el controlador. */
    public HomeController() {
        platos.add(new Plato(1L, "Causa rellena de pollo", "Entrada",
                "Papa amarilla, ají amarillo y pollo deshilachado.", 15, "Causa.jpg", true, 20));
        platos.add(new Plato(2L, "Seco de cordero con frejoles", "Fondo",
                "Cordero cocido a fuego lento con culantro y frejoles canarios.", 28, "Seco.jpg", true, 15));
        platos.add(new Plato(3L, "Tallarín saltado de res", "Fondo",
                "Salteado de carne, verduras y salsa de sillao.", 24, "Tallarin.jpg", false, 12));
        platos.add(new Plato(4L, "Suspiro a la limeña", "Postre",
                "Manjar blanco con merengue al oporto.", 12, "Suspiro.jpg", true, 4));
        platos.add(new Plato(5L, "Chicha morada", "Bebida",
                "Preparada con maíz morado, piña y especias.", 8, "Chicha.jpg", false, 0));

        testimonios.add(new Testimonio("Carla M.",
                "Reservé desde el celular y la mesa estaba lista apenas llegamos. Mucho más simple que llamar."));
        testimonios.add(new Testimonio("Jorge P.",
                "Me gustó poder elegir la zona de terraza directamente en el formulario."));
        testimonios.add(new Testimonio("Rosa V.",
                "La confirmación llegó rápido y no tuvimos que esperar mesa un sábado por la noche."));

        // Reserva de ejemplo para que el panel no aparezca vacío en la demo
        reservas.add(new Reserva(siguienteIdReserva++, "Ana Torres", "987654321",
                LocalDate.now().plusDays(1), LocalTime.of(20, 0),
                "3 - 4 personas", "Terraza", "Cumpleaños", "Confirmada"));
    }

    // ============ Objeto del formulario, disponible en TODAS las vistas ============
    // Se ejecuta antes de cada @GetMapping / @PostMapping de este controlador,
    // así th:object="${reserva}" nunca queda vacío.
    @ModelAttribute("reserva")
    public Reserva reservaVacia() {
        return new Reserva();
    }

    // ================== GET: página principal ==================
    @GetMapping("/")
    public String inicio(Model model) {
        cargarDatosVista(model);
        return "index"; // -> src/main/resources/templates/index.html
    }

    // ================== POST: registrar reserva ==================
    @PostMapping("/reservas")
    public String registrarReserva(@ModelAttribute("reserva") Reserva reserva,
                                   Model model,
                                   RedirectAttributes redirectAttributes) {

        // Validación sencilla en el servidor (el HTML ya valida con "required")
        String error = validar(reserva);
        if (error != null) {
            cargarDatosVista(model);
            model.addAttribute("mensajeError", error);
            return "index"; // se vuelve a mostrar el formulario con los datos ingresados
        }

        reserva.setId(siguienteIdReserva++);
        reserva.setEstado(reserva.getPersonas().startsWith("Más de 6")
                ? "Requiere confirmación" : "Pendiente");
        reservas.add(reserva);

        // Patrón Post-Redirect-Get: evita que la reserva se duplique al recargar la página
        redirectAttributes.addFlashAttribute("mensajeExito",
                "¡Reserva registrada! Te confirmaremos por teléfono en menos de 24 horas.");
        redirectAttributes.addFlashAttribute("reservaConfirmada", reserva);
        return "redirect:/#reservar";
    }

    // ================== Métodos auxiliares ==================

    /** Envía al modelo todo lo que la vista necesita renderizar. */
    private void cargarDatosVista(Model model) {
        model.addAttribute("platos", platos);
        model.addAttribute("platosDestacados",
                platos.stream().filter(Plato::isDestacado).toList());
        model.addAttribute("reservas", reservas);
        model.addAttribute("testimonios", testimonios);
        model.addAttribute("galeria", galeria);
        model.addAttribute("opcionesPersonas", opcionesPersonas);
        model.addAttribute("zonas", zonas);
        model.addAttribute("hoy", LocalDate.now());
    }

    private String validar(Reserva r) {
        if (r.getNombre() == null || r.getNombre().isBlank()) {
            return "Ingresa tu nombre completo.";
        }
        if (r.getTelefono() == null || !r.getTelefono().replace(" ", "").matches("\\d{6,15}")) {
            return "Ingresa un teléfono válido (solo números, entre 6 y 15 dígitos).";
        }
        if (r.getFecha() == null || r.getFecha().isBefore(LocalDate.now())) {
            return "Selecciona una fecha de hoy en adelante.";
        }
        if (r.getHora() == null) {
            return "Selecciona una hora.";
        }
        if (r.getPersonas() == null || r.getPersonas().isBlank()) {
            return "Selecciona el número de personas.";
        }

        // Evita que dos clientes reserven en el mismo horario
        Reserva choque = buscarReservaEnConflicto(r.getFecha(), r.getHora());
        if (choque != null) {
            return "Ya hay una reserva el " + choque.getFecha().format(FORMATO_FECHA)
                    + " a las " + choque.getHora().format(FORMATO_HORA)
                    + ". Elige otro horario con al menos " + DURACION_RESERVA_MIN
                    + " minutos de diferencia.";
        }
        return null;
    }

    /**
     * Devuelve la reserva que ocupa ese horario, o null si está libre.
     * Se considera ocupado si ya existe una reserva el mismo día
     * con menos de DURACION_RESERVA_MIN minutos de diferencia.
     */
    private Reserva buscarReservaEnConflicto(LocalDate fecha, LocalTime hora) {
        for (Reserva existente : reservas) {
            if (existente.getFecha().equals(fecha)) {
                long diferencia = Math.abs(Duration.between(existente.getHora(), hora).toMinutes());
                if (diferencia < DURACION_RESERVA_MIN) {
                    return existente;
                }
            }
        }
        return null;
    }

    // Getters usados por el controlador REST para compartir las mismas listas en memoria
    public List<Plato> getPlatos() { return platos; }
    public List<Reserva> getReservas() { return reservas; }
}