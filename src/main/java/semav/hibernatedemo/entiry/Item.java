package semav.hibernatedemo.entiry;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Data
@Accessors(chain = true)
@FieldDefaults(level= AccessLevel.PRIVATE)
public class Item {
    @Id
    @GeneratedValue
    Long id;

    @NotNull
    @Size(
            min = 2,
            max = 255,
            message = "Name is required, maximum 255 characters."
    )
    String name;

    @Future
    LocalDate auctionEnd;
}
