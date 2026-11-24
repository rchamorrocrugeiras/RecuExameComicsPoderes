package model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Autor")
public class Autor implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false, length = 50)
    private String nome;

    @Column(name = "apelidos", nullable = false, length = 100)
    private String apelidos;

    @Column(name = "nacionalidade", length = 50)
    private String nacionalidade;

    @OneToMany(mappedBy = "autor", fetch = FetchType.EAGER)
    private List<Comic> comics = new ArrayList<>();

    public Autor() {
    }

    public Autor(String nome, String apelidos, String nacionalidade) {
        this.nome = nome;
        this.apelidos = apelidos;
        this.nacionalidade = nacionalidade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getApelidos() {
        return apelidos;
    }

    public void setApelidos(String apelidos) {
        this.apelidos = apelidos;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public List<Comic> getComics() {
        return comics;
    }

    public void setComics(List<Comic> comics) {
        this.comics = comics;
        if (comics != null) {
            for (Comic c : comics) {
                c.setAutor(this);
            }
        }
    }
}