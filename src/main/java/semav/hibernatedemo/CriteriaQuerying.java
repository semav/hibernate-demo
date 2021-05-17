package semav.hibernatedemo;

import org.hibernate.SessionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import semav.hibernatedemo.entiry.Message;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.List;

@SpringBootApplication
public class CriteriaQuerying {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(CriteriaQuerying.class, args);
		SessionFactory sessionFactory = applicationContext.getBean(SessionFactory.class);

		store(sessionFactory);
		load(sessionFactory);

		sessionFactory.close();
	}

	private static void store(SessionFactory sessionFactory) {
		EntityManager entityManager = sessionFactory.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();

		for (int i = 0; i < 10; i++) {
			Message message = new Message().setText("some text " + i);
			entityManager.persist(message);
		}

		transaction.commit();
		entityManager.close();
	}

	private static void load(SessionFactory sessionFactory) {
		EntityManager entityManager = sessionFactory.createEntityManager();
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Message> query = criteriaBuilder.createQuery(Message.class);
		Root<Message> fromMessage = query.from(Message.class);
		Path<Long> idPath = fromMessage.get("id");

		query.select(fromMessage);
		query.where(criteriaBuilder.greaterThan(idPath,5L));
		List<Message> items = entityManager.createQuery(query).getResultList();
		items.forEach(System.out::println);

		entityManager.close();
	}
}
