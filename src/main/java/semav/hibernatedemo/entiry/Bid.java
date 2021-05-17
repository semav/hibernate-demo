package semav.hibernatedemo.entiry;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@org.hibernate.annotations.Immutable
@Data
@Accessors(chain = true)
@FieldDefaults(level= AccessLevel.PRIVATE)
public class Bid {
    @Id
    @GeneratedValue
    Long id;

    BigDecimal amount;
    LocalDate createdOn;
}
