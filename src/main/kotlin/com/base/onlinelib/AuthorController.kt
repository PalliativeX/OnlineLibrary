package com.base.onlinelib

import com.base.onlinelib.entities.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import java.time.LocalDate
import java.time.Year

class AuthorRequest(val name: String)

class BirthdateRequest(val birthdate: LocalDate, val period: Period) {
    enum class Period {
        Before, After
    }
}

class AuthorFilter(val name: String? = null, val penname: String? = null, val birthdateRequest: BirthdateRequest? = null)

@RestController
@RequestMapping("/authors")
class AuthorController(@Autowired val authorService: AuthorService,
                       @Autowired val dtoConverter: DTOConverter) {

    @GetMapping
    fun getAuthors(filter: AuthorFilter, pageable: Pageable): Page<AuthorDTO> {
        return authorService.getAll(filter, pageable).map(dtoConverter::convertAuthorToDto)
    }

    @GetMapping("/{id}")
    fun getAuthorById(@PathVariable id: Long): AuthorDTO? {
        val author = authorService.getByIdOrNull(id)
        return if (author != null)
            dtoConverter.convertAuthorToDto(author)
        else
            null
    }

    @GetMapping("/{id}/genres")
    fun getAuthorGenres(@PathVariable id: Long): Set<BookGenre>? {
        val author = authorService.getByIdOrNull(id)
        if (author != null) {
            val authorGenres = mutableSetOf<BookGenre>()
            for (book in author.books) {
                authorGenres += book.genre
            }
            return authorGenres
        }

        return null
    }

    @PostMapping
    fun addAuthor(@RequestBody authorDTO: AuthorDTO): AuthorDTO {
        val author = dtoConverter.convertAuthorDTOToEntity(authorDTO)
        authorService.add(author)
        return authorDTO
    }

    @PatchMapping("/{id}")
    fun updateAuthorName(@PathVariable id: Long, @RequestBody authorRequest: AuthorRequest): AuthorDTO? {
        val author = authorService.getByIdOrNull(id)

        if (author != null) {
            author.name = authorRequest.name
            authorService.add(author)
            return dtoConverter.convertAuthorToDto(author)
        }

        return null
    }

    @DeleteMapping("/{id}")
    fun removeAuthorById(@PathVariable id: Long): AuthorDTO? {
        val author = authorService.getByIdOrNull(id)
        if (author != null) {
            for (book in author.books) {
                book.removeAuthor(author)
            }
            authorService.deleteById(id)
            return dtoConverter.convertAuthorToDto(author)
        }

        return null
    }

}