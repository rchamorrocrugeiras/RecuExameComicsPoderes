package services;

import config.HibernateConfig;
import model.Protagonista;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ServicioProtagonista {
    public void actualizarProtagonista(Long id, String novoNome) {
        Transaction transaction = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Protagonista protagonista = session.get(Protagonista.class, id);
            if (protagonista != null) {
                protagonista.setNome(novoNome);
                session.update(protagonista);
                transaction.commit();
            } else {
                System.out.println("Non se atopou o Protagonista co id " + id);
                transaction.rollback();
            }
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Erro ao actualizar o Protagonista: " + e.getMessage());
        }
    }

    public void eliminarProtagonista(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Protagonista protagonista = session.get(Protagonista.class, id);
            if (protagonista != null) {
                session.delete(protagonista);
                transaction.commit();
            } else {
                System.out.println("Non se atopou o Protagonista co id " + id);
                transaction.rollback();
            }
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Erro ao eliminar o Protagonista: " + e.getMessage());
        }
    }
}