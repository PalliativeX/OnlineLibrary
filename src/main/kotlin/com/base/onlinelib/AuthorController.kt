package com.base.onlinelib

import com.base.onlinelib.entities.Author
import com.base.onlinelib.entities.AuthorDTO
import com.base.onlinelib.entities.AuthorService
import com.base.onlinelib.entities.DTOConverter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView

class AuthorRequest(val name: String)

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
        return dtoConverter.convertAuthorToDto(author)
    }

    @PostMapping("/", "")
    fun addAuthor(@RequestBody name: String): Author {
        val author = Author(name)
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