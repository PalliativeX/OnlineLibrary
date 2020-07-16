package com.base.onlinelib

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

// TODO: Probably split the LibController into BookController and AuthorController
@RestController
class LibraryController(@Autowired val authorRepository: AuthorRepository,
                        @Autowired val bookRepository: BookRepository) {

    @GetMapping("/")
    fun mainPage(): String {
        return "Online Library"
    }

    @GetMapping("/books")
    fun getAllBooks(): String {
        val books: List<Book> = bookRepository.findAll()
        var info : String = ""
        // TODO: Probably replace with templates
        books.forEach {
            info += it.toString() + "<br>"
        }

        return info
    }

    @GetMapping("/authors")
    fun getAllAuthors(): String {
        val authors: List<Author> = authorRepository.findAll()
        var info : String = ""
        // TODO: Probably replace with templates
        authors.forEach {
            info += it.toString() + "<br>"
        }

        return info
    }

    @GetMapping("/clear")
    fun clearAll(): String
    {
        bookRepository.deleteAllInBatch()
        authorRepository.deleteAllInBatch()

        return "Cleared!"
    }

    @GetMapping("/save")
    fun saveSomeData(): String
    {
        val orwell = Author("Orwell")

        val nineteen1984 = Book("1984")
        val flowers = Book("Flowers")

        orwell.books += nineteen1984
        orwell.books += flowers

        authorRepository.save(orwell)

        return "Saved!"
    }


}