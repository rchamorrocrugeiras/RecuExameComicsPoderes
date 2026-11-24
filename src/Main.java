import model.Autor;
import model.Comic;
import model.PoderUnico;
import model.Protagonista;
import services.ServicioAutor;
import services.ServicioComic;
import services.ServicioPoderUnico;
import services.ServicioProtagonista;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    private static final ServicioComic servicioComic = new ServicioComic();
    private static final ServicioAutor servicioAutor = new ServicioAutor();
    private static final ServicioProtagonista servicioProtagonista = new ServicioProtagonista();
    private static final ServicioPoderUnico servicioPoderUnico = new ServicioPoderUnico();

    public static void main(String[] args) {
        //Agrega os datos dados nas táboas
        Autor stanLee = new Autor("Stan", "Lee", "estadounidense");
        Autor alanMoore = new Autor("Alan", "Moore", "británica");
        Autor frankMiller = new Autor("Frank", "Miller", "estadounidense");
        Autor hergeRemi = new Autor("Hergé", "Remi", "belga");

        Protagonista spiderMan = new Protagonista("Spider-Man");
        Protagonista batman = new Protagonista("Batman");
        Protagonista superman = new Protagonista("Superman");
        Protagonista wonderWoman = new Protagonista("Wonder Woman");
        Protagonista tintin = new Protagonista("Tintín");
        Protagonista daredevil = new Protagonista("Daredevil");

        List<Comic> comicsAInserir = Arrays.asList(
                new Comic("The Amazing Spider-Man", 1963, stanLee, spiderMan),
                new Comic("Batman: The Killing Joke", 1988, alanMoore, batman),
                new Comic("Superman: The Man of Steel", 1986, frankMiller, superman),
                new Comic("Wonder Woman: Gods and Mortals", 1987, alanMoore, wonderWoman),
                new Comic("The Adventures of Tintin", 1929, hergeRemi, tintin),
                new Comic("Daredevil: Born Again", 1986, stanLee, daredevil),
                new Comic("Batman: Year One", 1987, frankMiller, batman),
                new Comic("Spider-Man: Blue", 2002, stanLee, spiderMan)
        );

        //Listado de protagonistas e os seus ids
        System.out.println("\nLISTADO DE PROTAGONISTAS E OS SEUS IDS:");
        Map<String, Long> mapaProtagonistas = obterProtagonistasUnicos();
        for (Map.Entry<String, Long> entry : mapaProtagonistas.entrySet()) {
            System.out.println("ID: " + entry.getValue() + " - Nome: " + entry.getKey());
        }

        //Inserir poderes na táboa PoderUnico asociandoos aos protagonistas
        inserirPoderSeExiste(mapaProtagonistas, "Spider-Man", "Lanzamento de telarañas");
        inserirPoderSeExiste(mapaProtagonistas, "Spider-Man", "Sentido arácnido");
        inserirPoderSeExiste(mapaProtagonistas, "Batman", "Diñeiro");
        inserirPoderSeExiste(mapaProtagonistas, "Batman", "Detectivismo avanzado");
        inserirPoderSeExiste(mapaProtagonistas, "Superman", "Visión calorífica");
        inserirPoderSeExiste(mapaProtagonistas, "Superman", "Alento xélido");
        inserirPoderSeExiste(mapaProtagonistas, "Wonder Woman", "Lazo da Verdade");
        inserirPoderSeExiste(mapaProtagonistas, "Wonder Woman", "Brazaletes indestrutibles");
        inserirPoderSeExiste(mapaProtagonistas, "Tintín", "Intelixencia investigadora");
        inserirPoderSeExiste(mapaProtagonistas, "Daredevil", "Ecolocalización por radar");

        //Lista os contidos das táboas seguindo o formato indicado
        System.out.println("\nLISTAXE INICIAL:");
        listarFormatoRequerido();

        //Actualiza o autor "Stan Lee"
        Long idAutor = buscarIdAutor(nomeAutorBuscar);
        if (idAutor != null) {
            servicioAutor.actualizarAutor(idAutor, "Satan", "Leee");
        }

        //Actualiza o protagonista "Spider-Man"
        Long idProtagonista = buscarIdProtagonista(nomeProtagonistaBuscar);
        if (idProtagonista != null) {
            servicioProtagonista.actualizarProtagonista(idProtagonista, "Spooder-Mon");
        }

        //Lista os contidos das táboas actualizadas seguindo o formato indicado
        System.out.println("\nLISTAXE ACTUALIZADA:");
        listarFormatoRequerido();

        //Borra o contido de todas as táboas
        List<Comic> todosOsComics = servicioComic.listarComics();
        Set<Long> idsAutores = new HashSet<>();
        Set<Long> idsProtagonistas = new HashSet<>();

        if (todosOsComics != null) {
            for (Comic c : todosOsComics) {
                idsAutores.add(c.getAutor().getId());
                idsProtagonistas.add(c.getProtagonista().getId());
            }
        }

        servicioComic.eliminarTodosOsComics();
        servicioPoderUnico.eliminarTodosOsPoderesUnicos();
        for (Long id : idsAutores) {
            servicioAutor.eliminarAutor(id);
        }
        for (Long id : idsProtagonistas) {
            servicioProtagonista.eliminarProtagonista(id);
        }

        //Lista o contido de todas as táboas seguindo o formato indicado
        System.out.println("\nLISTAXE DESPOIS DO BORRADO:");
        listarFormatoRequerido();
    }

    private static Map<String, Long> obterProtagonistasUnicos() {
        Map<String, Long> protas = new LinkedHashMap<>();
        List<Comic> comics = servicioComic.listarComics();
        if (comics != null) {
            for (Comic c : comics) {
                Protagonista p = c.getProtagonista();
                protas.putIfAbsent(p.getNome(), p.getId());
            }
        }
        return protas;
    }

    private static void inserirPoderSeExiste(Map<String, Long> mapaProtas, String nomeProta, String nomePoder) {
        Long id = mapaProtas.get(nomeProta);
        if (id != null) {
            servicioPoderUnico.crearPoderUnico(nomePoder, id);
        }
    }

    private static void listarFormatoRequerido() {
        List<Comic> comics = servicioComic.listarComics();
        if (comics == null || comics.isEmpty()) {
            System.out.println("(Táboas baleiras)");
            return;
        }

        Map<Long, List<Comic>> comicsPorAutor = new LinkedHashMap<>();
        for (Comic c : comics) {
            Long idAutor = c.getAutor().getId();
            comicsPorAutor.computeIfAbsent(idAutor, k -> new ArrayList<>()).add(c);
        }

        for (List<Comic> listaAutor : comicsPorAutor.values()) {
            Autor autor = listaAutor.get(0).getAutor();
            System.out.println("Autor:");
            System.out.println(autor.getNome() + " " + autor.getApelidos());
            System.out.println("Comics:");
            for (Comic c : listaAutor) {
                System.out.println(c.getTitulo() + " (" + c.getAno() + ")");
                System.out.println("Protagonista: " + c.getProtagonista().getNome());
                System.out.println("Poderes únicos:");
                for (PoderUnico p : c.getProtagonista().getPoderesUnicos()) {
                    System.out.println("- " + p.getNomePoder());
                }
            }
        }
    }

    private static Long buscarIdAutor(String nomeCompleto) {
        List<Comic> comics = servicioComic.listarComics();
        if (comics != null && nomeCompleto != null) {
            for (Comic c : comics) {
                String completo = c.getAutor().getNome() + " " + c.getAutor().getApelidos();
                if (completo.equalsIgnoreCase(nomeCompleto.trim())) {
                    return c.getAutor().getId();
                }
            }
        }
        return null;
    }

    private static Long buscarIdProtagonista(String nome) {
        List<Comic> comics = servicioComic.listarComics();
        if (comics != null && nome != null) {
            for (Comic c : comics) {
                if (c.getProtagonista().getNome().equalsIgnoreCase(nome.trim())) {
                    return c.getProtagonista().getId();
                }
            }
        }
        return null;
    }
}