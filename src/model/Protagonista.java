package model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Protagonista")
public class Protagonista implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @OneToMany(mappedBy = "protagonista", fetch = FetchType.EAGER)
    private List<PoderUnico> poderesUnicos = new ArrayList<>();

    public Protagonista() {
    }

    public Protagonista(String nome) {
        this.nome = nome;
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

    public List<PoderUnico> getPoderesUnicos() {
        return poderesUnicos;
    }

    public void setPoderesUnicos(List<PoderUnico> poderesUnicos) {
        this.poderesUnicos = poderesUnicos;
    }
}