package semav.hibernatedemo;

import org.hibernate.SessionFactory;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import semav.hibernatedemo.entiry.Message;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

@SpringBootApplication
public class Envers {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(Envers.class, args);
		SessionFactory sessionFactory = applicationContext.getBean(SessionFactory.class);

		createMessage(sessionFactory);
		showHistory(sessionFactory);

		sessionFactory.close();
	}

	private static void createMessage(SessionFactory sessionFactory) {
		EntityManager entityManager = sessionFactory.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		Message message = new Message().setText("some text");

		transaction.begin();
		entityManager.persist(message);
		transaction.commit();

		transaction.begin();
		entityManager.persist(message.setText("new text"));
		transaction.commit();

		entityManager.close();
	}

	private static void showHistory(SessionFactory sessionFactory) {
		Long id = 1L;
		EntityManager entityManager = sessionFactory.createEntityManager();
		AuditReader auditReader = AuditReaderFactory.get(entityManager);
		List<Number> revisions = auditReader.getRevisions(Message.class, id);

		revisions.forEach(number -> {
			Message message = auditReader.find(Message.class, id, number);
			System.out.println("Revision " + number + " " + message);
		});

		entityManager.close();
	}
}
