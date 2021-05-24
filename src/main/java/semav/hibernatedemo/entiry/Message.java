package semav.hibernatedemo.entiry;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Audited
@Data
@Accessors(chain = true)
@FieldDefaults(level= AccessLevel.PRIVATE)
public class Message {
    @Id
    @GeneratedValue
    Long id;

    String text;
}
