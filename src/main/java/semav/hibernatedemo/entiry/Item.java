package semav.hibernatedemo.entiry;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    @GenericGenerator(name="itemGenerator", strategy="sequence")
    @ElementCollection
    @CollectionTable(name = "IMAGE")
    @CollectionId(
            columns = @Column(name = "IMAGE_ID"),
            type = @org.hibernate.annotations.Type(type = "long"),
            generator = "itemGenerator")
    Collection<Image> images = new ArrayList<>();

    // CascadeType.PERSIST is used to avoid persisting of each bid
    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @Fetch(FetchMode.SUBSELECT)
    List<Bid> bids = new ArrayList<>();
}
