package oodbSite.repository;

import oodbSite.domain.Bank;
import oodbSite.domain.Worker;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Component
public class WorkerRepository {
    @PersistenceContext(name = "entityManagerFactory")
    protected EntityManager entityManager;
    @Autowired
    private SessionFactory sessionFactory;

    public List<Worker> findByBank(String name) {
        Query query = entityManager.createQuery(
                "select w from Worker w where w.bank.name like :name", Worker.class);
        query.setParameter("name", name);

        return (List<Worker>) query.getResultList();
    }

    public void save(Worker worker) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        if (worker.getId() != null) {
            session.persist(worker);
        } else {
            session.merge(worker);
        }
        transaction.commit();
        session.close();
    }
}
