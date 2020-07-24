package com.base.onlinelib

import com.base.onlinelib.entities.AuthorService
import com.base.onlinelib.entities.BookService
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito.mock


class ControllerTests {

    private val authorService = mock(AuthorService::class.java)
    private val bookService = mock(BookService::class.java)

    private val libController = LibraryController(authorService, bookService)

    @Test
    fun testSavePage() {
        val saved = libController.saveSomeData()
        println(saved)
        assertTrue(saved == "Saved!")
    }
}