package com.base.onlinelib.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService(@Autowired val userRepository: UserRepository,
                  @Autowired val passwordEncoder: PasswordEncoder) {

    fun findByUsername(username: String): UserCredentials? {
        return userRepository.findByUsername(username)
    }

    fun add(user: UserCredentials) {
        userRepository.save(user)
    }

    fun add(username: String, password: String, role: String, active: Boolean = true) {
        val user = UserCredentials(username, passwordEncoder.encode(password), role, active)
        userRepository.save(user)
    }

    fun changeRole(username: String, newRole: String) {
        val user = userRepository.findByUsername(username)
        if (user != null) {
            user.role = newRole
            userRepository.save(user)
        }
    }

    fun changeActive(username: String, active: Boolean) {
        val user = userRepository.findByUsername(username)
        if (user != null) {
            user.active = active
            userRepository.save(user)
        }
    }

    fun removeByUsername(username: String): Boolean {
        val user = userRepository.findByUsername(username)
        if (user != null) {
            userRepository.delete(user)
            return true
        }

        return false
    }

}