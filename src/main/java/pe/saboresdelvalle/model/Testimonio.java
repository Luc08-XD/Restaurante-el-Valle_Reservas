package pe.saboresdelvalle.model;

/**
 * Entidad del dominio: opinión de un cliente mostrada en la página principal.
 */
public class Testimonio {

    private String autor;
    private String comentario;

    public Testimonio() {
    }

    public Testimonio(String autor, String comentario) {
        this.autor = autor;
        this.comentario = comentario;
    }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }
}
