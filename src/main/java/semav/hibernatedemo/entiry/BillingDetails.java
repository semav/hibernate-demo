package semav.hibernatedemo.entiry;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
@FieldDefaults(level= AccessLevel.PRIVATE)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
abstract class BillingDetails {
    @Id
    @GeneratedValue
    Long id;

    @NotNull
    String owner;
}
