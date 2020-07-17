package com.base.onlinelib

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.web.bind.annotation.*

// TODO: Probably split the LibController into BookController and AuthorController
@RestController
class LibraryController(@Autowired val authorRepository: AuthorRepository,
                        @Autowired val bookRepository: BookRepository) {

    @GetMapping("/")
    fun mainPage(): String {
        return "Welcome to our online library!"
    }

    @GetMapping("/all")
    fun getAll(): String {
        val books = bookRepository.findAll()
        val authors = authorRepository.findAll()

        var info : String = ""
        for (author in authors) {
            info += "$author<br>"
            for (book in author.books) {
                info += "<span style=margin-left:2em>$book  </span> <br>"
            }
            info += "<br>"
        }

        return info
    }

    @GetMapping("/books")
    fun getAllBooks(): String {
        val books: List<Book> = bookRepository.findAll()
        var info : String = ""
        // TODO: Probably replace with templates
        books.forEach {
            info += "$it<br>"
        }

        return info
    }

    @GetMapping("/authors")
    fun getAllAuthors(): String {
        val authors: List<Author> = authorRepository.findAll()
        var info : String = ""
        // TODO: Probably replace with templates
        authors.forEach {
            info += "$it<br>"
        }

        return info
    }

    @GetMapping("/authors{id}")
    fun getAuthorById(@PathVariable id: Int): String {
        val author = authorRepository.findByIdOrNull(id)
        return author?.toString() ?: "This author can not been found!"
    }

    @GetMapping("/books{id}")
    fun getBookById(@PathVariable id: Int): String {
        val book = bookRepository.findByIdOrNull(id)
        return book?.toString() ?: "This book can not been found!"
    }

    @PostMapping("/authors/{name}")
    fun addAuthor(@PathVariable name: String): String {
        val author = Author(name)
        authorRepository.save(author)

        // FIXME: Wrong id in the message
        return "The author: \n $author \n has been successfully added!"
    }

    @PostMapping("/books/{title}")
    fun addBook(@PathVariable title: String): String {
        val book = Book(title)
        bookRepository.save(book)

        // FIXME: Wrong id in the message
        return "The book: \n $book \n has been successfully added!"
    }

    @PostMapping("/authors/update/{id}/{name}")
    fun updateAuthorName(@PathVariable id: Int, @PathVariable name: String): String {
        val author = authorRepository.findByIdOrNull(id)

        if (author != null)
        {
            author.name = name
            authorRepository.save(author)
            return "Author has been updated <br> $author"
        }

        return "The author with id:$id can not be updated!"
    }

    @PostMapping("/books/update/{id}/{title}")
    fun updateBookTitle(@PathVariable id: Int, @PathVariable title: String): String {
        val book = bookRepository.findByIdOrNull(id)

        if (book != null)
        {
            book.title = title
            bookRepository.save(book)
            return "The book has been updated <br> $book"
        }

        return "The book with id:$id can not be updated!"
    }

    @DeleteMapping("/clear")
    fun clearAll(): String {
        bookRepository.deleteAllInBatch()
        authorRepository.deleteAllInBatch()

        return "Cleared!"
    }

    @DeleteMapping("/authors/remove/{id}")
    fun removeAuthorById(@PathVariable id: Int): String {
        val author = authorRepository.findByIdOrNull(id)
        if (author != null)
        {
            for (book in author.books) {
                book.removeAuthor(author)
            }
            authorRepository.deleteById(id)
            return "The author with id:$id has been removed!"
        }

        return "The author with id:$id can not be removed!"
    }

    @DeleteMapping("/books/remove/{id}")
    fun removeBookById(@PathVariable id: Int): String {
        val book = bookRepository.findByIdOrNull(id)
        if (book != null)
        {
            for (author in book.authors) {
                println(author.toString())
                book.removeAuthor(author)
            }
            bookRepository.deleteById(id)
            return "The book with id:$id has been removed!"
        }

        return "The book with id:$id can not be removed!"
    }

    @GetMapping("/save")
    fun saveSomeData(): String {
        val orwell = Author("Orwell")

        val nineteen1984 = Book("1984")
        val flowers = Book("Flowers")

        orwell.books += nineteen1984
        orwell.books += flowers

        authorRepository.save(orwell)

        return "Saved!"
    }


}