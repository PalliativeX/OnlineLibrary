package com.base.onlinelib

import com.base.onlinelib.entities.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import java.time.LocalDate

class AuthorRequest(val name: String)

class AuthorNamePennameRequest(val name: String, val penname: String)

class BirthdateRequest(val birthdate: LocalDate, val period: Period) {
    enum class Period {
        Before, After
    }
}

@RestController
@RequestMapping("/authors")
class AuthorController(@Autowired val authorService: AuthorService,
                       @Autowired val dtoConverter: DTOConverter) {

    @GetMapping("")
    fun getAllAuthors(): ModelAndView {
        val maw = ModelAndView("authorList")
        val authors = authorService.getAll()
        maw.addObject("authors", authors)

        return maw
    }

    @GetMapping("/{id}")
    fun getAuthorById(@PathVariable id: Long): AuthorDTO? {
        val author = authorService.getByIdOrNull(id)
        return if (author != null)
            dtoConverter.convertAuthorToDto(author)
        else
            null
    }

    @GetMapping("/bynamepenname")
    fun getAuthorsByNameAndPenname(@RequestBody request: AuthorNamePennameRequest): List<AuthorDTO>? {
        val pageable = PageRequest.of(0, 10, Sort.by("name"))
        val authors = authorService.findByNamePenname(request.name, request.penname, pageable)

        return dtoConverter.convertAuthorListToDTOList(authors)
    }

    @GetMapping("/{id}/bygenre")
    fun getAuthorBooksByGenre(@PathVariable id: Long, @RequestBody genre: BookGenre): List<BookDTO>? {
        val author = authorService.getByIdOrNull(id) ?: return null

        val books: MutableList<BookDTO>? = mutableListOf()

        for (book in author.books) {
            if (book.genre == genre)
                books?.add(dtoConverter.convertBookToDto(book))
        }

        return books?.toList()
    }

    @GetMapping("/bybirthdate")
    fun getAuthorsByBirthdate(@RequestBody birthdateRequest: BirthdateRequest): List<AuthorDTO> {

        val pageable = PageRequest.of(0, 10, Sort.by("birthdate"))
        val authorsByBirthdate = if (birthdateRequest.period == BirthdateRequest.Period.Before) {
            authorService.findBeforeBirthdate(birthdateRequest.birthdate, pageable)
        }
        else {
            authorService.findAfterBirthdate(birthdateRequest.birthdate, pageable)
        }

        return dtoConverter.convertAuthorListToDTOList(authorsByBirthdate)
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

    @PostMapping("/", "")
    fun addAuthor(@RequestBody authorDTO: AuthorDTO): Author {
        val author = dtoConverter.convertAuthorDTOToEntity(authorDTO)
        authorService.add(author)
        return author
    }

    @PatchMapping("/{id}")
    fun updateAuthorName(@PathVariable id: Long, @RequestBody authorRequest: AuthorRequest): String {
        val author = authorService.getByIdOrNull(id)

        if (author != null) {
            author.name = authorRequest.name
            authorService.add(author)
            return "Author has been updated <br> $author"
        }

        return "The author with id:$id can not be updated!"
    }

    @DeleteMapping("/{id}")
    fun removeAuthorById(@PathVariable id: Long): String {
        val author = authorService.getByIdOrNull(id)
        if (author != null) {
            for (book in author.books) {
                book.removeAuthor(author)
            }
            authorService.deleteById(id)
            return "The author with id:$id has been removed!"
        }

        return "The author with id:$id can not be removed!"
    }

}