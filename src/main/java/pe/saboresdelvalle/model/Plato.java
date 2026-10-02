package pe.saboresdelvalle.model;

/**
 * Entidad del dominio: un plato de la carta del restaurante.
 */
public class Plato {

    private Long id;
    private String nombre;
    private String categoria;
    private String descripcion;
    private double precio;
    private String imagen;      // nombre del archivo dentro de static/img/
    private boolean destacado;  // se muestra en las tarjetas principales
    private int stock;          // porciones disponibles del día

    public Plato() {
    }

    public Plato(Long id, String nombre, String categoria, String descripcion,
                 double precio, String imagen, boolean destacado, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.precio = precio;
        this.imagen = imagen;
        this.destacado = destacado;
        this.stock = stock;
    }

    // ===== Métodos de apoyo para la vista (Thymeleaf los lee como propiedades) =====

    /** ${plato.agotado} */
    public boolean isAgotado() {
        return stock <= 0;
    }

    /** ${plato.pocasUnidades} */
    public boolean isPocasUnidades() {
        return stock > 0 && stock <= 5;
    }

    // ===== Getters y Setters =====

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public String getImagen() { return imagen; }
    public void setImagen(String imagen) { this.imagen = imagen; }

    public boolean isDestacado() { return destacado; }
    public void setDestacado(boolean destacado) { this.destacado = destacado; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}
