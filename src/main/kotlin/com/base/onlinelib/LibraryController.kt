package com.base.onlinelib

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView

@RestController
class LibraryController(@Autowired val authorService: AuthorService,
                        @Autowired val bookService:   BookService) {

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
    fun clearAll(): String {
        authorService.deleteAllInBatch()
        bookService.deleteAllInBatch()

        return "Cleared!"
    }

    @PostMapping("/save")
    fun saveSomeData(): String {
        val author = Author("Huxley")

        val book1 = Book("Brave New World")
        val book2 = Book("Island")

        author.books += book1
        author.books += book2

        authorService.add(author)

        return "Saved!"
    }

}