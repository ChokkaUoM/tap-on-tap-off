package au.com.littlepay.tap.pricing.enums;

import lombok.Getter;

@Getter
public enum TripStatus {

    COMPLETED("COMPLETED"),
    INCOMPLETE("INCOMPLETE"),
    CANCELLED("CANCELLED"),
    INVALID("INVALID");

    private final String name;

    TripStatus(String name) {
        this.name = name;
    }
}
