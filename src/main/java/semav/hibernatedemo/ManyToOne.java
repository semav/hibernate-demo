package semav.hibernatedemo;

import org.hibernate.SessionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import semav.hibernatedemo.entiry.BillingDetails;
import semav.hibernatedemo.entiry.User;
import semav.hibernatedemo.util.RandomEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

@SpringBootApplication
public class ManyToOne {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(ManyToOne.class, args);
		SessionFactory sessionFactory = applicationContext.getBean(SessionFactory.class);

		Long id = store(sessionFactory);
		load(sessionFactory, id);

		sessionFactory.close();
	}

	private static Long store(SessionFactory sessionFactory) {
		EntityManager entityManager = sessionFactory.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		BillingDetails creditCard = RandomEntity.getCreditCard();
		User user = new User().setBillingDetails(creditCard);

		transaction.begin();

		entityManager.persist(creditCard);
		entityManager.persist(user);

		transaction.commit();
		entityManager.close();

		return user.getId();
	}

	private static void load(SessionFactory sessionFactory, Long id) {
		EntityManager entityManager = sessionFactory.createEntityManager();

		User user = entityManager.find(User.class, id);
		System.out.println(user);

		entityManager.close();
	}
}
