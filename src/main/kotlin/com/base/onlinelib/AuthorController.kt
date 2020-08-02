package com.base.onlinelib

import com.base.onlinelib.entities.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

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
        return author?.let { dtoConverter.convertAuthorToDto(author) }
    }

    @GetMapping("/{id}/genres")
    fun getAuthorGenres(@PathVariable id: Long): Set<BookGenre>? {
        val author = authorService.getByIdOrNull(id)

        author?.let {
            val authorGenres = mutableSetOf<BookGenre>()
            author.books.forEach {
                authorGenres += it.genre
            }
            return authorGenres
        }
        return null
    }

    @PostMapping
    fun addAuthor(@RequestBody authorDTO: AuthorDTO): AuthorDTO {
        authorService.add(dtoConverter.convertAuthorDTOToEntity(authorDTO))
        return authorDTO
    }

    @PatchMapping("/{id}")
    fun updateAuthorName(@PathVariable id: Long, @RequestBody authorRequest: AuthorRequest): AuthorDTO? {
        val author = authorService.getByIdOrNull(id)

        author?.let {
            it.name = authorRequest.name
            authorService.add(it)
            return dtoConverter.convertAuthorToDto(it)
        }

        return null
    }

    @DeleteMapping("/{id}")
    fun removeAuthorById(@PathVariable id: Long): AuthorDTO? {
        val author = authorService.getByIdOrNull(id)

        author?.let {
            author.books.forEach {
                it.removeAuthor(author)
            }
            authorService.deleteById(id)
            return dtoConverter.convertAuthorToDto(author)
        }

        return null
    }

}