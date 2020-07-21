package com.base.onlinelib.security

import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component

@Component
class UserDetailsMapper {

    fun toUserDetails(userCredentials: UserCredentials): UserDetails {
        return User.withUsername(userCredentials.username)
                .password(userCredentials.password)
                .roles(userCredentials.role)
                .build()
    }
}