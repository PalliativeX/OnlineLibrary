package com.base.onlinelib

import com.base.onlinelib.entities.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import java.time.Year

class BookRequest(val title: String)

class BookFilter(val title: String? = null, val author: Author? = null, val year: Year? = null, val genre: BookGenre? = null)

@RestController
@RequestMapping("/books")
class BookController(@Autowired val bookService: BookService,
                     @Autowired val authorService: AuthorService,
                     @Autowired val dtoConverter: DTOConverter) {

    @GetMapping
    fun getAllBooks(filter: BookFilter, pageable: Pageable): Page<BookDTO> {
        return bookService.getAll(filter, pageable).map(dtoConverter::convertBookToDto)
    }

    @GetMapping("/{id}")
    fun getBookById(@PathVariable id: Long): BookDTO? {
        val book = bookService.getByIdOrNull(id)

        return if (book != null)
            dtoConverter.convertBookToDto(book)
        else
            null
    }

    @PostMapping
    fun addBook(@RequestBody bookDTO: BookDTO): BookDTO {
        val book = dtoConverter.convertBookDTOToEntity(bookDTO)
        bookService.add(book)
        return bookDTO
    }

    @PatchMapping("/{id}")
    fun updateBookTitle(@PathVariable id: Long, @RequestBody bookRequest: BookRequest): BookDTO? {
        val book = bookService.getByIdOrNull(id)

        if (book != null) {
            book.title = bookRequest.title
            bookService.add(book)
            return dtoConverter.convertBookToDto(book)
        }

        return null
    }

    @DeleteMapping("/{id}")
    fun removeBookById(@PathVariable id: Long): BookDTO? {
        val book = bookService.getByIdOrNull(id)
        if (book != null) {
            for (author in book.authors) {
                println(author.toString())
                book.removeAuthor(author)
            }
            bookService.deleteById(id)
            return dtoConverter.convertBookToDto(book)
        }

        return null
    }

}