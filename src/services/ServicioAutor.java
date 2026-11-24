package services;

import config.HibernateConfig;
import model.Autor;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ServicioAutor {
    public void actualizarAutor(Long id, String novoNome, String novosApelidos) {
        Transaction transaction = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Autor autor = session.get(Autor.class, id);
            if (autor != null) {
                autor.setNome(novoNome);
                autor.setApelidos(novosApelidos);
                session.update(autor);
                transaction.commit();
            } else {
                System.out.println("Non se atopou o Autor co id " + id);
                transaction.rollback();
            }
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Erro ao actualizar o Autor: " + e.getMessage());
        }
    }

    public void eliminarAutor(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Autor autor = session.get(Autor.class, id);
            if (autor != null) {
                session.delete(autor);
                transaction.commit();
            } else {
                System.out.println("Non se atopou o Autor co id " + id);
                transaction.rollback();
            }
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Erro ao eliminar o Autor: " + e.getMessage());
        }
    }
}