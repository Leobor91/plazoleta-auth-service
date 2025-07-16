package com.pragma.plazadecomidas.authservice.domain.util;

import com.pragma.plazadecomidas.authservice.domain.model.MessageEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidationUtilsTest {

    private ValidationUtils validationUtils;

    enum TestMessageEnum {
        EMAIL_STRUCTURE(MessageEnum.EMAIL_STRUCTURE.getMessage()),
        PHONE_STRUCTURE(MessageEnum.PHONE_STRUCTURE.getMessage()),
        NUMBER_FORMAT(MessageEnum.NUMBER_FORMAT.getMessage()),
        EIGHTEENNUMBER(MessageEnum.EIGHTEENNUMBER.getMessage());

        private final String message;

        TestMessageEnum(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    @BeforeEach
    void setUp() {
        validationUtils = new ValidationUtils();
    }

    @Test
    @DisplayName("Should return false when value is null")
    void isValid_NullValue_ReturnsFalse() {
        assertFalse(validationUtils.isValid(null));
    }

    @Test
    @DisplayName("Should return false when value is empty")
    void isValid_EmptyValue_ReturnsFalse() {
        assertFalse(validationUtils.isValid(""));
    }

    @Test
    @DisplayName("Should return false when value is only spaces")
    void isValid_SpacesValue_ReturnsFalse() {
        assertFalse(validationUtils.isValid("   "));
    }

    @Test
    @DisplayName("Should return true when value is valid")
    void isValid_ValidValue_ReturnsTrue() {
        assertTrue(validationUtils.isValid("some text"));
    }

    @Test
    @DisplayName("Should return true for valid email structure")
    void isValidEmailStructure_ValidEmail_ReturnsTrue() {
        assertTrue(validationUtils.isValidEmailStructure("test@example.com"));
        assertTrue(validationUtils.isValidEmailStructure("john.doe123@sub.domain.co"));
    }

    @Test
    @DisplayName("Should return false for invalid email structure (missing @)")
    void isValidEmailStructure_InvalidEmailMissingAt_ReturnsFalse() {
        assertFalse(validationUtils.isValidEmailStructure("testexample.com"));
    }

    @Test
    @DisplayName("Should return false for invalid email structure (missing domain)")
    void isValidEmailStructure_InvalidEmailMissingDomain_ReturnsFalse() {
        assertFalse(validationUtils.isValidEmailStructure("test@.com"));
    }

    @Test
    @DisplayName("Should return false for invalid email structure (null)")
    void isValidEmailStructure_NullEmail_ReturnsFalse() {
        assertFalse(validationUtils.isValidEmailStructure(null));
    }

    @Test
    @DisplayName("Should return true for valid phone structure (with +)")
    void isValidPhoneStructure_ValidPhoneWithPlus_ReturnsTrue() {
        assertTrue(validationUtils.isValidPhoneStructure("+573101234567"));
    }

    @Test
    @DisplayName("Should return true for valid phone structure (without +)")
    void isValidPhoneStructure_ValidPhoneWithoutPlus_ReturnsTrue() {
        assertTrue(validationUtils.isValidPhoneStructure("3101234567"));
    }

    @Test
    @DisplayName("Should return false for invalid phone structure (too short)")
    void isValidPhoneStructure_InvalidPhoneTooShort_ReturnsFalse() {
        assertTrue(validationUtils.isValidPhoneStructure("12345"));
    }

    @Test
    @DisplayName("Should return false for invalid phone structure (contains letters)")
    void isValidPhoneStructure_InvalidPhoneContainsLetters_ReturnsFalse() {
        assertFalse(validationUtils.isValidPhoneStructure("+57310ABCDEFG"));
    }

    @Test
    @DisplayName("Should return false for invalid phone structure (null)")
    void isValidPhoneStructure_NullPhone_ReturnsFalse() {
        assertFalse(validationUtils.isValidPhoneStructure(null));
    }

    @Test
    @DisplayName("Should return false when birthDate is null")
    void isBirthDateNotFuture_NullBirthDate_ReturnsFalse() {
        assertFalse(validationUtils.isBirthDateNotFuture(null));
    }

    @Test
    @DisplayName("Should return false when birthDate is in the future")
    void isBirthDateNotFuture_FutureBirthDate_ReturnsFalse() {
        LocalDate futureDate = LocalDate.now().plusDays(1);
        assertFalse(validationUtils.isBirthDateNotFuture(futureDate));
    }

    @Test
    @DisplayName("Should return true when birthDate is today")
    void isBirthDateNotFuture_TodayBirthDate_ReturnsTrue() {
        LocalDate today = LocalDate.now();
        assertTrue(validationUtils.isBirthDateNotFuture(today));
    }

    @Test
    @DisplayName("Should return true when birthDate is in the past")
    void isBirthDateNotFuture_PastBirthDate_ReturnsTrue() {
        LocalDate pastDate = LocalDate.now().minusYears(1);
        assertTrue(validationUtils.isBirthDateNotFuture(pastDate));
    }

    @Test
    @DisplayName("Should return false when birthDate is null for adult check")
    void isAdult_NullBirthDate_ReturnsFalse() {
        assertFalse(validationUtils.isAdult(null));
    }

    @Test
    @DisplayName("Should return false when person is not an adult (less than 18 years)")
    void isAdult_NotAdult_ReturnsFalse() {
        LocalDate minorBirthDate = LocalDate.now().minusYears(17).minusDays(1);
        assertFalse(validationUtils.isAdult(minorBirthDate));
    }

    @Test
    @DisplayName("Should return false when person is exactly 18 years old today (since it uses isBefore)")
    void isAdult_ExactlyEighteenYearsOldToday_ReturnsFalse() {
        LocalDate exactlyEighteenYearsAgo = LocalDate.now().minusYears(18);
        assertFalse(validationUtils.isAdult(exactlyEighteenYearsAgo));
    }

    @Test
    @DisplayName("Should return true when person is an adult (more than 18 years)")
    void isAdult_IsAdult_ReturnsTrue() {
        LocalDate adultBirthDate = LocalDate.now().minusYears(18).minusDays(1);
        assertTrue(validationUtils.isAdult(adultBirthDate));
    }

    @Test
    @DisplayName("Should return true when identificationNumber contains only numbers")
    void containsOnlyNumbers_OnlyNumbers_ReturnsTrue() {
        assertTrue(validationUtils.containsOnlyNumbers("1234567890"));
    }

    @Test
    @DisplayName("Should return false when identificationNumber contains letters")
    void containsOnlyNumbers_ContainsLetters_ReturnsFalse() {
        assertFalse(validationUtils.containsOnlyNumbers("123ABC456"));
    }

    @Test
    @DisplayName("Should return false when identificationNumber contains special characters")
    void containsOnlyNumbers_ContainsSpecialChars_ReturnsFalse() {
        assertFalse(validationUtils.containsOnlyNumbers("123-456"));
    }

    @Test
    @DisplayName("Should return false when identificationNumber is empty")
    void containsOnlyNumbers_Empty_ReturnsFalse() {
        assertFalse(validationUtils.containsOnlyNumbers(""));
    }

    @Test
    @DisplayName("Should return false when identificationNumber is null")
    void containsOnlyNumbers_Null_ReturnsFalse() {
        assertFalse(validationUtils.containsOnlyNumbers(null));
    }
}