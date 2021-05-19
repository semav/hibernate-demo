package semav.hibernatedemo.entiry;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Table(name = "USERS")
@Data
@Accessors(chain = true)
@FieldDefaults(level= AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue
    Long id;

    // We use ImplicitNamingStrategyComponentPathImpl to use multiple Embedded classes.
    // Otherwise you have to use @AttributeOverrides for all columns.
    Address homeAddress;
    Address billingAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    BillingDetails billingDetails;
}
