package oodbSite.repository;

import oodbSite.domain.Bank;
import oodbSite.domain.Client;
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
public class ClientRepository {
    @PersistenceContext(name = "entityManagerFactory")
    protected EntityManager entityManager;
    @Autowired
    private SessionFactory sessionFactory;

    public List<Client> findByBank(String name) {
        Query query = entityManager.createQuery(
                "select c from Client c where c.bank.name like :name", Client.class);
        query.setParameter("name", name);

        return (List<Client>) query.getResultList();
    }

    public Client findById(Long id) {
        Query query = entityManager.createQuery(
                "select c from Client c where c.id = :id", Client.class);
        query.setParameter("id", id);

        return (Client) query.getSingleResult();
    }

    public void save(Client client) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        if (client.getId() != null) {
            session.merge(client);
        } else {
            session.persist(client);
        }
        transaction.commit();
        session.close();
    }
}
