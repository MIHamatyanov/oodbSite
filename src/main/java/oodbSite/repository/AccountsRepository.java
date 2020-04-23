package oodbSite.repository;

import oodbSite.domain.BankAccount;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Component
public class AccountsRepository {
    @PersistenceContext(name = "entityManagerFactory")
    protected EntityManager entityManager;
    @Autowired
    private SessionFactory sessionFactory;

    public void save(BankAccount bankAccount) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        if (bankAccount.getId() != null) {
            session.persist(bankAccount);
        } else {
            session.merge(bankAccount);
        }
        transaction.commit();
        session.close();
    }

    public BankAccount getByAccountNumber(long accountNumber) {
        Query query = entityManager.createQuery(
                "select a from BankAccount a where a.accountNumber=:accountNumber", BankAccount.class);
        query.setParameter("accountNumber", accountNumber);

        return (BankAccount) query.getSingleResult();
    }
}
