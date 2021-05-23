package semav.hibernatedemo;

import org.hibernate.SessionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import semav.hibernatedemo.entiry.Bid;
import semav.hibernatedemo.entiry.Item;
import semav.hibernatedemo.entiry.User;
import semav.hibernatedemo.util.RandomEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

@SpringBootApplication
public class UsingReferences {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(UsingReferences.class, args);
		SessionFactory sessionFactory = applicationContext.getBean(SessionFactory.class);

		Long userId = createUser(sessionFactory);
		Long itemId = createItem(sessionFactory);

		createBid(sessionFactory, userId, itemId);

		sessionFactory.close();
	}

	private static Long createUser(SessionFactory sessionFactory) {
		EntityManager entityManager = sessionFactory.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		User user = new User();

		transaction.begin();

		entityManager.persist(user);

		transaction.commit();
		entityManager.close();

		return user.getId();
	}

	private static Long createItem(SessionFactory sessionFactory) {
		EntityManager entityManager = sessionFactory.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		Item item = RandomEntity.getItem();

		transaction.begin();

		entityManager.persist(item);

		transaction.commit();
		entityManager.close();

		return item.getId();
	}

	private static void createBid(SessionFactory sessionFactory, Long userId, Long itemId) {
		EntityManager entityManager = sessionFactory.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();

		User user = entityManager.getReference(User.class, userId);
		Item item = entityManager.getReference(Item.class, itemId);

		Bid bid = RandomEntity
				.getBid(item)
				.setUser(user);

		entityManager.persist(bid);

		transaction.commit();
		entityManager.close();
	}

}
