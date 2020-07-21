package com.base.onlinelib.security

import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<UserCredentials, Long> {
    fun findByUsername(username: String): UserCredentials?

    fun removeByUsername(username: String)
}