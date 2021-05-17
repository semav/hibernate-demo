package semav.hibernatedemo;

import org.hibernate.SessionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import semav.hibernatedemo.entiry.Bid;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootApplication
public class ImmutableEntity {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(ImmutableEntity.class, args);
		SessionFactory sessionFactory = applicationContext.getBean(SessionFactory.class);

		Long id = createBid(sessionFactory);
		editBid(sessionFactory, id);
		checkBid(sessionFactory, id);

		sessionFactory.close();
	}

	private static Long createBid(SessionFactory sessionFactory) {
		EntityManager entityManager = sessionFactory.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		Bid bid = new Bid()
				.setAmount(BigDecimal.TEN)
				.setCreatedOn(LocalDate.now());

		transaction.begin();
		entityManager.persist(bid);
		transaction.commit();
		entityManager.close();

		return bid.getId();
	}

	private static void editBid(SessionFactory sessionFactory, Long id) {
		EntityManager entityManager = sessionFactory.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();
		Bid bid = entityManager.find(Bid.class, id);
		bid.setAmount(BigDecimal.ONE);
		entityManager.persist(bid);
		transaction.commit();
		// Hibernate never modifies Bid instance

		entityManager.close();
	}

	private static void checkBid(SessionFactory sessionFactory, Long id) {
		EntityManager entityManager = sessionFactory.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();
		Bid bid = entityManager.find(Bid.class, id);
		System.out.println(bid);
		transaction.commit();

		entityManager.close();
	}

}
