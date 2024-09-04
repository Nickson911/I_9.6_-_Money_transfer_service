package ru.netology.moneytransferservice.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record Amount(@Positive(message = "The amount cannot be zero or negative") Integer value,
                     @NotBlank(message = "Enter currency") String currency) {

    @Override
    public String toString() {
        return "Amount{" +
                "value=" + value +
                ", currency='" + currency + '\'' +
                '}';
    }
}
