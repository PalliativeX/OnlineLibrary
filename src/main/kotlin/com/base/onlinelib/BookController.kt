package com.base.onlinelib

import com.base.onlinelib.entities.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import java.time.Year

class BookRequest(val title: String)

class BookFilter(val title: String? = null, val author: Author? = null, val year: Year? = null, val genre: BookGenre? = null)

@RestController
@RequestMapping("/books")
class BookController(@Autowired val bookService: BookService,
                     @Autowired val dtoConverter: DTOConverter) {

    @GetMapping
    fun getAllBooks(filter: BookFilter, pageable: Pageable): Page<BookDTO> {
        return bookService.getAll(filter, pageable).map(dtoConverter::convertBookToDto)
    }

    @GetMapping("/{id}")
    fun getBookById(@PathVariable id: Long): BookDTO? {
        val book = bookService.getByIdOrNull(id)
        return book?.let { dtoConverter.convertBookToDto(book) }
    }

    @PostMapping
    fun addBook(@RequestBody bookDTO: BookDTO): BookDTO {
        bookService.add(dtoConverter.convertBookDTOToEntity(bookDTO))
        return bookDTO
    }

    @PatchMapping("/{id}")
    fun updateBookTitle(@PathVariable id: Long, @RequestBody bookRequest: BookRequest): BookDTO? {
        val book = bookService.getByIdOrNull(id)

        book?.let {
            it.title = bookRequest.title
            bookService.add(it)
            return dtoConverter.convertBookToDto(it)
        }

        return null
    }

    @DeleteMapping("/{id}")
    fun removeBookById(@PathVariable id: Long): BookDTO? {
        val book = bookService.getByIdOrNull(id)

        book?.let {
            book.authors.forEach {
                book.removeAuthor(it)
            }
            bookService.deleteById(id)
            return dtoConverter.convertBookToDto(book)
        }

        return null
    }

}