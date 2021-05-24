package semav.hibernatedemo.util;

import lombok.experimental.UtilityClass;
import semav.hibernatedemo.entiry.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@UtilityClass
public class RandomEntity {
    public static Address getAddress() {
        return new Address()
                .setCity("New York")
                .setStreet("Wall street")
                .setZipCode("1234567");
    }

    public static CreditCard getCreditCard() {
        return (CreditCard) new CreditCard()
                .setCardNumber("13123123")
                .setExpMonth("04")
                .setExpYear("2023")
                .setOwner("Owner");
    }

    public static Item getItem() {
       return new Item()
                .setName("Item")
                .setAuctionEnd(LocalDate.now().plusDays(1))
                .setImages(Collections.singletonList(new Image()
                        .setFileName("filename.png")));
    }

    public static Bid getBid(Item item) {
        return new Bid()
                .setAmount(BigDecimal.TEN)
                .setCreatedOn(LocalDate.now())
                .setItem(item);
    }

    public static List<Bid> getBids(Item item, int amount) {
        return IntStream
                .rangeClosed(0, amount)
                .mapToObj(i -> new Bid()
                        .setAmount(BigDecimal.valueOf(i))
                        .setCreatedOn(LocalDate.now())
                        .setItem(item))
                .collect(Collectors.toList());
    }
}
