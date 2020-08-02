package com.base.onlinelib

import com.base.onlinelib.entities.*
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito
import java.time.LocalDate
import java.time.Month
import java.time.Year

class AuthorControllerTests {

    private val authorService = Mockito.mock(AuthorService::class.java)
    private val dtoConverter = DTOConverter()
    private val authorController = AuthorController(authorService, dtoConverter)

    @Test
    fun testGetAuthor() {
        Mockito.`when`(authorService.getByIdOrNull(10)).
            thenReturn(Author("Publius Vergilius Maro", LocalDate.of(70, Month.OCTOBER, 15), "Virgil"))

        val virgil = authorController.getAuthorById(10)
        println(virgil)
        assertTrue(virgil?.penname == "Virgil")
    }

    @Test
    fun testUpdateAuthorName() {
        Mockito.`when`(authorService.getByIdOrNull(10)).
            thenReturn(Author("Publius Vergilius Maro", LocalDate.of(70, Month.OCTOBER, 15), "Virgil"))

        val lucius = authorController.updateAuthorName(10, AuthorRequest("Lucius"))
        println(lucius)
        assertTrue(lucius?.name == "Lucius")
    }

    @Test
    fun testGetAuthorGenres() {
        Mockito.`when`(authorService.getByIdOrNull(10)).
            thenReturn(Author("Publius Vergilius Maro", LocalDate.of(70, Month.OCTOBER, 15), "Virgil",
                                     mutableSetOf(Book("Odyssey", Year.of(842), BookGenre.Drama)), 10))

        val bookGenres = authorController.getAuthorGenres(10)
        assertTrue(bookGenres?.contains(BookGenre.Drama)!!)
    }



}