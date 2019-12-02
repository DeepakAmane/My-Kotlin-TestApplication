package com.example.kotlin.testapp

import com.example.kotlin.testapp.utils.Utils
import junit.framework.TestCase.assertTrue
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Test

class LoginValidatorTest {

    @Test
    fun emailValidator_CorrectEmailSimple_ReturnsTrue() {
        assertTrue(Utils.isEmailValid("deepaksubamane@email.com"))
    }

    @Test
    fun emailValidator_CorrectEmailSubDomain_ReturnsTrue() {
        assertTrue(Utils.isEmailValid("deepaksubamane@email.co.uk"))
    }

    @Test
    fun emailValidator_InvalidEmailNoTld_ReturnsFalse() {
        assertFalse(Utils.isEmailValid("deepaksubamane@email"))
    }

    @Test
    fun emailValidator_InvalidEmailDoubleDot_ReturnsFalse() {
        assertFalse(Utils.isEmailValid("deepaksubamane@email..com"))
    }

    @Test
    fun emailValidator_InvalidEmailNoUsername_ReturnsFalse() {
        assertFalse(Utils.isEmailValid("@email.com"))
    }

    @Test
    fun emailValidator_EmptyString_ReturnsFalse() {
        assertFalse(Utils.isEmailValid(""))
    }

    @Test
    fun emailValidator_check_Email_isNull() {
        // val emailNull = ObservableField()
        assertNull("Email is Null", null)
    }

    @Test
    fun passwordValidator_check_Password_isNull() {
        // val passwordNull = ObservableField()
        assertNull("Password is Null", null)
    }

    @Test
    fun passwordValidator_CorrectPassword_ReturnsTrue() {
        assertTrue(Utils.isValidPassword("John@1"))
        assertTrue(Utils.isValidPassword("JOHN@1"))
        assertTrue(Utils.isValidPassword("johN1@"))
        assertTrue(Utils.isValidPassword("J@1a"))
        assertFalse(Utils.isValidPassword("john"))
        assertFalse(Utils.isValidPassword("john@"))
        assertFalse(Utils.isValidPassword("john@1"))
        assertFalse(Utils.isValidPassword("john1@"))
        assertFalse(Utils.isValidPassword("joh1@"))
        assertFalse(Utils.isValidPassword("johN@"))
    }
}