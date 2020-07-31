package com.base.onlinelib

import com.base.onlinelib.entities.*
import com.base.onlinelib.security.PasswordEncoder
import com.base.onlinelib.security.UserCredentials
import com.base.onlinelib.security.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Configuration
import java.time.LocalDate
import java.time.Month
import java.time.Year


@Configuration
class AppStartup(@Autowired val userService: UserService,
                 @Autowired val authorService: AuthorService,
                 @Autowired val bookService: BookService,
                 @Autowired val passwordEncoder: PasswordEncoder) : CommandLineRunner {

    val clearAllEntityData = true
    val preloadUser = true
    val preloadData = true

    override fun run(vararg args: String) {

        if (clearAllEntityData)
        {
            authorService.deleteAllInBatch()
            bookService.deleteAllInBatch()
        }

        // NOTE: Adding a default user to a db
        if (preloadUser) {
            userService.add(UserCredentials("user", passwordEncoder.encode("password"), "user", true))
        }

        if (preloadData) {
            val author = Author("Homer", LocalDate.of(867, Month.APRIL, 24), "Homer")

            val book1 = Book("Odyssey", Year.of(842), BookGenre.Drama)
            val book2 = Book("Illiad", Year.of(834), BookGenre.Drama)

            author.books += book1
            author.books += book2

            authorService.add(author)
        }
    }

}