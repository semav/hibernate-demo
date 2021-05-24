package semav.hibernatedemo;

import org.hibernate.SessionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import semav.hibernatedemo.entiry.Item;
import semav.hibernatedemo.util.RandomEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

@SpringBootApplication
public class PrefetchingWithSubselects {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(PrefetchingWithSubselects.class, args);
		SessionFactory sessionFactory = applicationContext.getBean(SessionFactory.class);

		createItems(sessionFactory);
		getItems(sessionFactory);

		sessionFactory.close();
	}

	private static void getItems(SessionFactory sessionFactory) {
		EntityManager entityManager = sessionFactory.createEntityManager();

		List<Item> items = entityManager.createQuery("select i from Item i", Item.class).getResultList();

		items.forEach(System.out::println);

		entityManager.close();
	}

	private static void createItems(SessionFactory sessionFactory) {
		EntityManager entityManager = sessionFactory.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();

		for (int i = 0; i < 10; i++) {
			Item item = RandomEntity.getItem();
			item.getBids().addAll(RandomEntity.getBids(item, 10));
			entityManager.persist(item);
		}
		transaction.commit();
		entityManager.close();
	}

}
