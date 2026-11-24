package model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Comic")
public class Comic implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo", nullable = false, length = 150)
    private String titulo;

    @Column(name = "ano")
    private Integer ano;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_autor", nullable = false)
    private Autor autor;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_protagonista", nullable = false)
    private Protagonista protagonista;

    public Comic() {
    }

    public Comic(String titulo, Integer ano, Autor autor, Protagonista protagonista) {
        this.titulo = titulo;
        this.ano = ano;
        this.autor = autor;
        this.protagonista = protagonista;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Protagonista getProtagonista() {
        return protagonista;
    }

    public void setProtagonista(Protagonista protagonista) {
        this.protagonista = protagonista;
    }
}