package com.base.onlinelib.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class DatabaseUserDetailsService(@Autowired val userService: UserService,
                                 @Autowired val userDetailsMapper: UserDetailsMapper) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails? {
        val userCredentials = userService.findByUsername(username)
        return if (userCredentials != null)
            userDetailsMapper.toUserDetails(userCredentials)
        else
            null
    }
}