package com.base.onlinelib

import com.base.onlinelib.security.UserCredentials
import com.base.onlinelib.security.UserRepository
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class JpaTests {

    @Test
    fun testUserRepository() {
        val user = UserCredentials("login", "login", "user", false)
        val userRepository = mock(UserRepository::class.java)
        `when` (userRepository.findByUsername("login")).thenReturn(user)

        val testUser = userRepository.findByUsername("login")

        assertTrue(testUser?.password.equals("login"))
    }
}
