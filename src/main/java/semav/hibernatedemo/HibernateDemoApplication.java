package semav.hibernatedemo;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class HibernateDemoApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(HibernateDemoApplication.class, args);
		SessionFactory sessionFactory = applicationContext.getBean(SessionFactory.class);
		System.out.println("sad");
	}

}
