package semav.hibernatedemo;

import org.hibernate.SessionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import semav.hibernatedemo.entiry.Message;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class ExplicitPessimisticLocking {

	public static void main(String[] args) throws InterruptedException {
		ApplicationContext applicationContext = SpringApplication.run(ExplicitPessimisticLocking.class, args);
		SessionFactory sessionFactory = applicationContext.getBean(SessionFactory.class);
		ExecutorService executorService = Executors.newFixedThreadPool(2);

		Long id = prepareData(sessionFactory);

		executorService.submit(() -> thread(sessionFactory, id, 1));
		executorService.submit(() -> thread(sessionFactory, id, 2));

		executorService.shutdown();
		executorService.awaitTermination(3000, TimeUnit.SECONDS);

		sessionFactory.close();
	}

	private static Long prepareData(SessionFactory sessionFactory) {
		EntityManager entityManager = sessionFactory.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		Message message = new Message().setText("initial value");

		transaction.begin();

		entityManager.persist(message);

		transaction.commit();
		entityManager.close();

		return message.getId();
	}

	private static void thread(SessionFactory sessionFactory, Long id, int threadNumber) {
		EntityManager entityManager = sessionFactory.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();

		Message message = entityManager.find(Message.class, id, LockModeType.PESSIMISTIC_READ);
		System.out.println("The message found in thread " + threadNumber + " " + message);

		System.out.println("Do long processing in thread " + threadNumber);

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		message.setText("Thread " + threadNumber);

		transaction.commit();
		entityManager.close();

		System.out.println("Thread " + threadNumber + " has finished");
	}
}
