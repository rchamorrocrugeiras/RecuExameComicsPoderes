package services;

import config.HibernateConfig;
import model.Comic;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.ArrayList;

public class ServicioComic {
    public void crearComic(Comic comic) {
        Transaction transaction = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(comic);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Erro ao crear o Comic: " + e.getMessage());
        }
    }

    public Comic lerComic(Long id) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            return session.get(Comic.class, id);
        } catch (Exception e) {
            System.out.println("Erro ao ler o Comic: " + e.getMessage());
            return null;
        }
    }

    public ArrayList<Comic> listarComics() {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            return (ArrayList<Comic>) session.createQuery("FROM Comic", Comic.class).getResultList();
        } catch (Exception e) {
            System.out.println("Erro ao listar Comics: " + e.getMessage());
            return null;
        }
    }

    public void eliminarTodosOsComics() {
        Transaction transaction = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM Comic").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Erro ao eliminar os Comics: " + e.getMessage());
        }
    }
}