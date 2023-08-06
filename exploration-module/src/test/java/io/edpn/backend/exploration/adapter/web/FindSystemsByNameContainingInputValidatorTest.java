package io.edpn.backend.exploration.adapter.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class FindSystemsByNameContainingInputValidatorTest {

    private FindSystemsByNameContainingInputValidator validator;

    @BeforeEach
    void setUp() {
        validator = new FindSystemsByNameContainingInputValidator();
    }

    @Test
    void testValidateSubString_shouldThrowOnNullSubString() {
        assertThrows(IllegalArgumentException.class, () -> validator.validateSubString(null), "subString must not be null or empty");
    }

    @Test
    void testValidateSubString_shouldThrowOnEmptySubString() {
        assertThrows(IllegalArgumentException.class, () -> validator.validateSubString(""), "subString must not be null or empty");
    }

    @Test
    void testValidateSubString_shouldNotThrowOnValidSubString() {
        validator.validateSubString("validString"); // No exception expected
    }

    @Test
    void testValidateAmount_shouldThrowOnStrictPositiveAmount() {
        validator.validateAmount(1); // No exception expected
    }

    @Test
    void testValidateAmount_shouldNotThrowOnZero() {
        assertThrows(IllegalArgumentException.class, () -> validator.validateAmount(0), "Amount must not be strict positive");
    }

    @Test
    void testValidateAmount_shouldNotThrowOnNegativeAmount() {
        assertThrows(IllegalArgumentException.class, () -> validator.validateAmount(-1), "Amount must not be strict positive");
    }
}
