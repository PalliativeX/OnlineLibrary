package com.base.onlinelib.security

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.security.SecureRandom

@Component
class PasswordEncoder : BCryptPasswordEncoder(10) {

    fun encodePassword(plainPassword: String): String = encode(plainPassword)
}