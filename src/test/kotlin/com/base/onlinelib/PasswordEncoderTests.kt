package com.base.onlinelib

import com.base.onlinelib.security.PasswordEncoder
import org.junit.Assert
import org.junit.Test

class PasswordEncoderTests {

    @Test
    fun testPasswordEncoderVersion() {
        val passwordEncoder = PasswordEncoder()

        val encodedPassword = passwordEncoder.encodePassword("password")
        Assert.assertTrue(encodedPassword.startsWith("$2a"))
    }
}