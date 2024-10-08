package ru.netology.moneytransferservice.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferMoneyData {
    @NotBlank(message = "Enter card number")
    @Size(min = 16, max = 16, message = "Length of card number must be 16")
    private String cardFromNumber;

    @NotBlank(message = "Enter card expiration date")
    // Регулярное выражение ("(0[1-9]|1[0-2])" - месяц от "01" до "12") ("/([0-9]{2})" -  год в формате "00" до "99")
    @Pattern(regexp = "(0[1-9]|1[0-2])/([0-9]{2})", message = "Incorrect date")
    private String cardFromValidTill;

    @NotBlank(message = "Enter card cvv")
    @Size(min = 3, max = 3, message = "CVV's length must be 3")
    private String cardFromCVV;

    @NotBlank(message = "Enter card number")
    @Size(min = 16, max = 16, message = "Length of card number must be 16")
    private String cardToNumber;

    @Valid
    private Amount amount;
    private String id;

    @Override
    public String toString() {
        return "TransferMoneyData{" +
                "cardFromNumber='" + cardFromNumber + '\'' +
                ", cardFromValidTill='" + cardFromValidTill + '\'' +
                ", cardFromCVV='" + cardFromCVV + '\'' +
                ", cardToNumber='" + cardToNumber + '\'' +
                '}';
    }
}