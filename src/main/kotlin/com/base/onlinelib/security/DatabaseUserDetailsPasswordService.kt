package com.base.onlinelib.security

import org.springframework.security.core.userdetails.UserDetails

import org.springframework.security.core.userdetails.UserDetailsPasswordService
import org.springframework.stereotype.Service
import javax.transaction.Transactional


@Transactional
@Service
class DatabaseUserDetailPasswordService(val userService: UserService,
                                        val userDetailsMapper: UserDetailsMapper) : UserDetailsPasswordService {

    override fun updatePassword(user: UserDetails, newPassword: String): UserDetails? {
        val userCredentials = userService.findByUsername(user.username)
        return if (userCredentials != null) {
            userCredentials.password = newPassword
            userDetailsMapper.toUserDetails(userCredentials)
        }
        else null
    }

}