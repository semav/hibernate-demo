package semav.hibernatedemo.util;

import lombok.experimental.UtilityClass;
import semav.hibernatedemo.entiry.Address;
import semav.hibernatedemo.entiry.CreditCard;

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
}
