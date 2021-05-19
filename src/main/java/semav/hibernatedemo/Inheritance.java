package semav.hibernatedemo;

import org.hibernate.SessionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import semav.hibernatedemo.entiry.CreditCard;
import semav.hibernatedemo.util.RandomEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

@SpringBootApplication
public class Inheritance {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(Inheritance.class, args);
		SessionFactory sessionFactory = applicationContext.getBean(SessionFactory.class);

		Long id = store(sessionFactory);
		load(sessionFactory, id);

		sessionFactory.close();
	}

	private static Long store(SessionFactory sessionFactory) {
		EntityManager entityManager = sessionFactory.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		CreditCard creditCard = RandomEntity.getCreditCard();

		transaction.begin();

		entityManager.persist(creditCard);

		transaction.commit();
		entityManager.close();

		return creditCard.getId();
	}

	private static void load(SessionFactory sessionFactory, Long id) {
		EntityManager entityManager = sessionFactory.createEntityManager();

		CreditCard creditCard = entityManager.find(CreditCard.class, id);
		System.out.println(creditCard);

		entityManager.close();
	}
}
