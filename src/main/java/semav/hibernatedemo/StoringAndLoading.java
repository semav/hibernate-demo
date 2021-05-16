package semav.hibernatedemo;

import org.hibernate.SessionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import semav.hibernatedemo.entiry.Message;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

@SpringBootApplication
public class StoringAndLoading {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(StoringAndLoading.class, args);
		SessionFactory sessionFactory = applicationContext.getBean(SessionFactory.class);

		store(sessionFactory);
		load(sessionFactory);

		sessionFactory.close();
	}

	private static void store(SessionFactory sessionFactory) {
		EntityManager entityManager = sessionFactory.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		Message message = new Message().setText("some text");

		transaction.begin();
		entityManager.persist(message);

		transaction.commit();
		entityManager.close();
	}

	private static void load(SessionFactory sessionFactory) {
		EntityManager entityManager = sessionFactory.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();

		List<Message> messages = entityManager.createQuery("select m from Message m", Message.class).getResultList();
		Message message = messages.get(0);
		System.out.println(message);

		message.setText("Take me to your leader!");

		transaction.commit();
		entityManager.close();
	}
}
