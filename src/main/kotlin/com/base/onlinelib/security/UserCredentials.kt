package com.base.onlinelib.security

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class UserCredentials(@Id val username: String,
                           var password: String,
                           var role: String,
                           var active: Boolean)