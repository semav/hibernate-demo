package semav.hibernatedemo.entiry;

import lombok.AccessLevel;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import semav.hibernatedemo.enums.AuctionType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@org.hibernate.annotations.Immutable
@Data
@Accessors(chain = true)
@FieldDefaults(level=AccessLevel.PRIVATE)
public class Bid {
    @Id
    @GeneratedValue
    Long id;

    @NotNull
    BigDecimal amount;

    @NotNull
    LocalDate createdOn;

    @NotNull
    @Enumerated(EnumType.STRING)
    AuctionType auctionType = AuctionType.HIGHEST_BID;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    Item item;
}
