package oodbSite.repository;

import oodbSite.domain.Bank;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Component
public class BankRepository {
    @PersistenceContext(name = "entityManagerFactory")
    protected EntityManager entityManager;
    @Autowired
    private SessionFactory sessionFactory;

    public List<Bank> findAll() {

        Query query = entityManager.createQuery(
                "select b from Bank b ", Bank.class);

        return (List<Bank>) query.getResultList();
    }

    public Bank findByName(String name) {
        Query query = entityManager.createQuery(
                "select b from Bank b where b.name like :name ", Bank.class);

        query.setParameter("name", name);

        return (Bank) query.getSingleResult();
    }

    public void save(Bank bank) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        if (bank.getId() != null) {
            session.persist(bank);
        } else {
            session.merge(bank);
        }
        transaction.commit();
        session.close();
    }
}
