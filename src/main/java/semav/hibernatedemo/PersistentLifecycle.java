package semav.hibernatedemo;

import org.hibernate.SessionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import semav.hibernatedemo.entiry.Message;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

@SpringBootApplication
public class PersistentLifecycle {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(PersistentLifecycle.class, args);
		SessionFactory sessionFactory = applicationContext.getBean(SessionFactory.class);
		EntityManager entityManager = sessionFactory.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();

		/***********************************/
		transaction.begin();

		// Create an instance in a persistent state
		Message message = new Message().setText("some text");

		// Make the transient instance of persistent
		entityManager.persist(message);

		// When we get the message by id the entity manager returns it from the first-level cache
		Message sameMessage = entityManager.find(Message.class, message.getId());
		System.out.println("The same instance = " + (message == sameMessage));

		// Commit the transaction and save the instance to a database
		transaction.commit();

		/***********************************/

		transaction.begin();
		// Removes and makes the instance transient
		entityManager.remove(message);
		System.out.println("The message is in managed persistent state in this persistence context = " +  entityManager.contains(message));

		// Commit the transaction and delete the instance to a database
		transaction.commit();

		/***********************************/

		// Make an instance of Message in a detached state
		message = new Message().setText("some text");
		transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(message);
		transaction.commit();
		entityManager.close();

		// Create another persistent context
		entityManager = sessionFactory.createEntityManager();
		transaction = entityManager.getTransaction();

		// Check if the message is in the persistent context
		System.out.println("The message is in the persistent context = " + entityManager.contains(message));

		message.setText("New text");

		transaction.begin();
		entityManager.merge(message);

		// Commit the transaction and save new changes of the message instance to a database
		transaction.commit();

		sessionFactory.close();
	}

}
