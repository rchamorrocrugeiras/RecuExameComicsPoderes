package services;

import config.HibernateConfig;
import model.PoderUnico;
import model.Protagonista;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ServicioPoderUnico {
    public void crearPoderUnico(String nomePoder, Long idProtagonista) {
        Transaction transaction = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Protagonista protagonista = session.get(Protagonista.class, idProtagonista);
            if (protagonista != null) {
                PoderUnico poderUnico = new PoderUnico(nomePoder, protagonista);
                session.save(poderUnico);
                transaction.commit();
            } else {
                System.out.println("Non se atopou o Protagonista co id " + idProtagonista);
                transaction.rollback();
            }
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Erro ao crear o PoderUnico: " + e.getMessage());
        }
    }

    public void eliminarTodosOsPoderesUnicos() {
        Transaction transaction = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM PoderUnico").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Erro ao eliminar os PoderesUnicos: " + e.getMessage());
        }
    }
}