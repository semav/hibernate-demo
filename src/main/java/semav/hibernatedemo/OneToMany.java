package semav.hibernatedemo;

import org.hibernate.SessionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import semav.hibernatedemo.entiry.Item;
import semav.hibernatedemo.util.RandomEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

@SpringBootApplication
public class OneToMany {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(OneToMany.class, args);
		SessionFactory sessionFactory = applicationContext.getBean(SessionFactory.class);

		Long id = store(sessionFactory);
		load(sessionFactory, id);

		sessionFactory.close();
	}

	private static Long store(SessionFactory sessionFactory) {
		EntityManager entityManager = sessionFactory.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		Item item = RandomEntity.getItem();

		transaction.begin();

		entityManager.persist(item);
		item.getBids().add(RandomEntity.getBid(item));
		item.getBids().add(RandomEntity.getBid(item));

		transaction.commit();
		entityManager.close();

		return item.getId();
	}

	private static void load(SessionFactory sessionFactory, Long id) {
		EntityManager entityManager = sessionFactory.createEntityManager();

		Item item = entityManager.find(Item.class, id);
		System.out.println(item);

		entityManager.close();
	}
}
