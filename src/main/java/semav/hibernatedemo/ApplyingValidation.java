package semav.hibernatedemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import semav.hibernatedemo.entiry.Item;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

@SpringBootApplication
public class ApplyingValidation {

    public static void main(String[] args) {
        SpringApplication.run(ApplyingValidation.class, args);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Item item = new Item()
                .setName("A")
                .setAuctionEnd(LocalDate.now());

        Set<ConstraintViolation<Item>> violations = validator.validate(item);
        violations.forEach(v -> System.out.println(v.getMessage()));
    }
}
