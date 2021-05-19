package semav.hibernatedemo;

import org.hibernate.SessionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import semav.hibernatedemo.entiry.User;
import semav.hibernatedemo.util.RandomEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

@SpringBootApplication
public class EmbeddableClass {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(EmbeddableClass.class, args);
		SessionFactory sessionFactory = applicationContext.getBean(SessionFactory.class);

		store(sessionFactory);
		load(sessionFactory);

		sessionFactory.close();
	}

	private static void store(SessionFactory sessionFactory) {
		EntityManager entityManager = sessionFactory.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		User user = new User().setHomeAddress(RandomEntity.getAddress());

		transaction.begin();
		entityManager.persist(user);

		transaction.commit();
		entityManager.close();
	}

	private static void load(SessionFactory sessionFactory) {
		EntityManager entityManager = sessionFactory.createEntityManager();

		List<User> users = entityManager.createQuery("select u from User u", User.class).getResultList();
		User user = users.get(0);
		System.out.println(user);

		entityManager.close();
	}
}
