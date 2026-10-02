package pe.saboresdelvalle.model;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * Entidad del dominio: una reserva de mesa hecha por un comensal.
 * Sus atributos se enlazan al formulario con th:object / th:field.
 */
public class Reserva {

    private Long id;
    private String nombre;
    private String telefono;

    @DateTimeFormat(pattern = "yyyy-MM-dd") // formato que envía <input type="date">
    private LocalDate fecha;

    @DateTimeFormat(pattern = "HH:mm")      // formato que envía <input type="time">
    private LocalTime hora;

    private String personas;
    private String zona;
    private String comentarios;
    private String estado;

    /** Constructor vacío: Spring lo usa para crear el objeto al recibir el formulario. */
    public Reserva() {
        this.zona = "Salón principal";
        this.estado = "Pendiente";
    }

    public Reserva(Long id, String nombre, String telefono, LocalDate fecha, LocalTime hora,
                   String personas, String zona, String comentarios, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.fecha = fecha;
        this.hora = hora;
        this.personas = personas;
        this.zona = zona;
        this.comentarios = comentarios;
        this.estado = estado;
    }

    // ===== Getters y Setters =====

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public LocalTime getHora() { return hora; }
    public void setHora(LocalTime hora) { this.hora = hora; }

    public String getPersonas() { return personas; }
    public void setPersonas(String personas) { this.personas = personas; }

    public String getZona() { return zona; }
    public void setZona(String zona) { this.zona = zona; }

    public String getComentarios() { return comentarios; }
    public void setComentarios(String comentarios) { this.comentarios = comentarios; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
