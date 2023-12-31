package com.example.finalprojectbinar.util

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class AuthVerificationTest {

    @Test
    fun checkEmailValid() {
        val email = "someMail@gmail.com"
        val result = AuthVerification.emailVerification(email)

        assertThat(result).isTrue()
    }

    @Test
    fun checkEmailIsEmpty() {
        val email = ""
        val result = AuthVerification.emailVerification(email)

        assertThat(result).isFalse()
    }

    @Test
    fun checkEmailIsNotValid() {
        val email = "someMail.gmail.com"
        val result = AuthVerification.emailVerification(email)

        assertThat(result).isFalse()
    }

    @Test
    fun checkEmailDoubleDotReturnFalse() {
        val email = "email@gmail..com"
        val result = AuthVerification.emailVerification(email)

        assertThat(result).isFalse()
    }

    @Test
    fun checkEmailWithoutDomainReturnFalse() {
        val email = "email@gmail"
        val result = AuthVerification.emailVerification(email)

        assertThat(result).isFalse()
    }


    @Test
    fun checkPasswordIsValid() {
        val password = "password123"
        val result = AuthVerification.passwordVerification(password)

        assertThat(result).isTrue()
    }

    @Test
    fun checkPasswordIsEmpty() {
        val password = ""
        val result = AuthVerification.passwordVerification(password)

        assertThat(result).isFalse()
    }

    @Test
    fun checkPasswordLessThanSixCharacters() {
        val password = "lei"
        val result = AuthVerification.passwordVerification(password)

        assertThat(result).isFalse()
    }
}