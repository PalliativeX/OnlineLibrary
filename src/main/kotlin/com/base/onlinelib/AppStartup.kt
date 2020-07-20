package com.base.onlinelib

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component


@Component
class AppStartup(@Autowired val authorService: AuthorService,
                 @Autowired val bookService: BookService) : CommandLineRunner {

    val preloadData = false

    override fun run(vararg args: String) {
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