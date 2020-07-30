package com.base.onlinelib

import com.base.onlinelib.entities.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import java.time.Year

class BookRequest(val title: String)

@RestController
@RequestMapping("/books")
class BookController(@Autowired val bookService: BookService,
                     @Autowired val authorService: AuthorService,
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

        return if (book != null)
            dtoConverter.convertBookToDto(book)
        else
            null
    }

    @GetMapping("/publicationyear")
    fun getBooksByPublicationYear(@RequestBody year: Year): List<BookDTO> {
        val pageable = PageRequest.of(0, 10, Sort.by("publicationYear"))
        val booksByPublicationYear = bookService.findByPublicationYear(year, pageable)

        return dtoConverter.convertBookListToDTOList(booksByPublicationYear)
    }

    @GetMapping("/bygenre")
    fun getBooksByGenre(@RequestBody genre: BookGenre): List<BookDTO>? {
        val pageable = PageRequest.of(0, 10, Sort.by("genre"))
        val booksByGenre = bookService.findByGenre(genre, pageable)

        return dtoConverter.convertBookListToDTOList(booksByGenre)
    }

    @PostMapping("/", "")
    fun addBook(@RequestBody bookDTO: BookDTO): BookDTO {
        val book = dtoConverter.convertBookDTOToEntity(bookDTO)
        bookService.add(book)
        return bookDTO
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