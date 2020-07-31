package com.base.onlinelib

import com.base.onlinelib.entities.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import java.time.LocalDate
import java.time.Month
import java.time.Year

@RestController
class LibraryController(@Autowired val authorService: AuthorService,
                        @Autowired val bookService: BookService) {

    @GetMapping("/", "/hello")
    fun greeting(): ModelAndView {
        return ModelAndView("hello")
    }

    @GetMapping("/home")
    fun mainPage(): ModelAndView {
        return ModelAndView("home")
    }

    @GetMapping("/login")
    fun loginPage(): ModelAndView {
        return ModelAndView("login")
    }

    @GetMapping("/all")
    fun getAll(): ModelAndView {
        val authors = authorService.getAll()
        val maw = ModelAndView("authorBookList")
        maw.addObject("authors", authors)

        return maw
    }

    @DeleteMapping("/clear")
    fun clearAll() {
        authorService.deleteAllInBatch()
        bookService.deleteAllInBatch()
    }

    @PostMapping("/save")
    fun saveSomeData() {
        val author = Author("Aldous Leonard Huxley", LocalDate.of(1894, Month.JULY, 26), "Huxley")


        val book1 = Book("Brave New World", Year.of(1932), BookGenre.Drama)
        val book2 = Book("Island", Year.of(1962), BookGenre.Drama)

        author.books += book1
        author.books += book2

        authorService.add(author)
    }

}