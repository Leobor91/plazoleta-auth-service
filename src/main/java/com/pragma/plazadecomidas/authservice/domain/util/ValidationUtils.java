package com.pragma.plazadecomidas.authservice.domain.util;

import java.util.regex.Matcher;
import java.time.LocalDate;
import com.pragma.plazadecomidas.authservice.domain.model.MessageEnum;
import org.springframework.stereotype.Component;

@Component
public class ValidationUtils {

    public boolean isValid(String value) {
        return value != null && !value.trim().isEmpty();
    }

    public  boolean isValidEmailStructure(String email) {
        if (email == null) return false;
        Matcher matcher = java.util.regex.Pattern.compile(MessageEnum.EMAIL_STRUCTURE.getMessage()).matcher(email);
        return matcher.matches();
    }

    public  boolean isValidPhoneStructure(String phone) {
        if (phone == null) return false;
        Matcher matcher = java.util.regex.Pattern.compile(MessageEnum.PHONE_STRUCTURE.getMessage()).matcher(phone);
        return matcher.matches();
    }

    public boolean isBirthDateNotFuture(LocalDate birthDate) {
        return birthDate != null && !birthDate.isAfter(LocalDate.now());
    }

    public boolean isAdult(LocalDate birthDate) {
        return birthDate != null && birthDate.isBefore(LocalDate.now().minusYears(Integer.parseInt(MessageEnum.EIGHTEENNUMBER.getMessage())));
    }

    public boolean containsOnlyNumbers(String identificationNumber) {
        if (identificationNumber == null) return false;
        Matcher matcher = java.util.regex.Pattern.compile(MessageEnum.NUMBER_FORMAT.getMessage()).matcher(identificationNumber);
        return matcher.matches();
    }

    public boolean isValidDateFormat(String date) {
        if (date == null) return false;
        String regex = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$";
        return java.util.regex.Pattern.matches(regex, date);
    }

}
