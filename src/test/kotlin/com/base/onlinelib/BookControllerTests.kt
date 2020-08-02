package com.base.onlinelib

import com.base.onlinelib.entities.Book
import com.base.onlinelib.entities.BookGenre
import com.base.onlinelib.entities.BookService
import com.base.onlinelib.entities.DTOConverter
import io.kotest.assertions.assertSoftly
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito
import java.time.Year

class BookControllerTests {

    private val bookService = Mockito.mock(BookService::class.java)
    private val dtoConverter = DTOConverter()
    private val bookController = BookController(bookService, dtoConverter)

    @Test
    fun testGetBookById() {
        Mockito.`when`(bookService.getByIdOrNull(10)).
            thenReturn(Book("Odyssey", Year.of(842), BookGenre.Drama))

        val book = bookController.getBookById(10)
        println(book)
        assertSoftly {
            book shouldNotBe null
            book?.title shouldBe "Odyssey"
            book?.publicationYear shouldBe Year.of(842)
        }
    }

    @Test
    fun testUpdateBookTitle() {
        Mockito.`when`(bookService.getByIdOrNull(10)).
            thenReturn(Book("Odyssey", Year.of(842), BookGenre.Drama))

        val book = bookController.updateBookTitle(10, BookRequest("The Netherlands"))
        assertTrue(book?.title == "The Netherlands")
    }

    @Test
    fun testRemoveBookById() {
        Mockito.`when`(bookService.getByIdOrNull(10)).
            thenReturn(Book("Odyssey", Year.of(842), BookGenre.Drama))

        val book = bookController.removeBookById(10)
        assertTrue(book?.title == "Odyssey")
    }

}