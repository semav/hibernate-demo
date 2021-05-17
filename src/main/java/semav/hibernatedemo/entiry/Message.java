package semav.hibernatedemo.entiry;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@Accessors(chain = true)
public class Message {
    @Id
    @GeneratedValue
    private Long id;

    private String text;
}
