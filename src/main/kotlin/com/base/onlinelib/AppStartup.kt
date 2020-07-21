package com.base.onlinelib

import com.base.onlinelib.entities.Author
import com.base.onlinelib.entities.AuthorService
import com.base.onlinelib.entities.Book
import com.base.onlinelib.entities.BookService
import com.base.onlinelib.security.PasswordEncoder
import com.base.onlinelib.security.UserCredentials
import com.base.onlinelib.security.UserRepository
import com.base.onlinelib.security.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component


@Configuration
class AppStartup(@Autowired val userService: UserService,
                 @Autowired val authorService: AuthorService,
                 @Autowired val bookService: BookService) : CommandLineRunner {

    val preloadUser = false
    val preloadData = false

    override fun run(vararg args: String) {

        // NOTE: Adding a default user to a db
        if (preloadUser) {
            userService.add(UserCredentials("user", "password", "user", true))
        }

        if (preloadData) {
            val author = Author("Homer")

            val book1 = Book("Odyssey")
            val book2 = Book("Illiad")

            author.books += book1
            author.books += book2

            authorService.add(author)
        }
    }

}