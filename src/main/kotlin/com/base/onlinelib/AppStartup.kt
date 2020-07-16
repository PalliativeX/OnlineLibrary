package com.base.onlinelib

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class AppStartup(@Autowired val authorRepository: AuthorRepository,
                 @Autowired val bookRepository: BookRepository) : CommandLineRunner {
    override fun run(vararg args: String) {

    }
}