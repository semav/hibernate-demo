package semav.hibernatedemo.entiry;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    List<Bid> bids = new ArrayList<>();
}
