package com.base.onlinelib

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView

class BookRequest(val title: String)

@RestController
@RequestMapping("/books")
class BookController(@Autowired val bookService: BookService,
                     @Autowired val dtoConverter: DTOConverter) {

    @GetMapping("")
    fun getAllBooks(): ModelAndView {
        val maw = ModelAndView("bookList")
        val books = bookService.getAll()
        maw.addObject("books", books)

        return maw
    }

    @GetMapping("/{id}")
    fun getBookById(@PathVariable id: Long): BookDTO? {
        val book = bookService.getByIdOrNull(id)
        return dtoConverter.convertBookToDto(book)
    }

    @PostMapping("/", "")
    fun addBook(@RequestBody title: String): String {
        val book = Book(title)
        bookService.add(book)

        return "The book: \n $book \n has been successfully added!"
    }

    @PatchMapping("/{id}")
    fun updateBookTitle(@PathVariable id: Long, @RequestBody bookRequest: BookRequest): String {
        val book = bookService.getByIdOrNull(id)

        if (book != null) {
            book.title = bookRequest.title
            bookService.add(book)
            return "The book has been updated <br> $book"
        }

        return "The book with id:$id can not be updated!"
    }

    @DeleteMapping("/{id}")
    fun removeBookById(@PathVariable id: Long): String {
        val book = bookService.getByIdOrNull(id)
        if (book != null) {
            for (author in book.authors) {
                println(author.toString())
                book.removeAuthor(author)
            }
            bookService.deleteById(id)
            return "The book with id:$id has been removed!"
        }

        return "The book with id:$id can not be removed!"
    }

}