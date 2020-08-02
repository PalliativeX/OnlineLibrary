package com.base.onlinelib

import com.base.onlinelib.entities.AuthorRepository
import com.base.onlinelib.entities.BookGenre
import com.base.onlinelib.entities.BookService
import com.base.onlinelib.security.UserRepository
import io.kotest.assertions.assertSoftly
import io.kotest.matchers.shouldNotBe
import org.assertj.core.api.Assertions.assertThat
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.test.context.junit4.SpringRunner
import java.time.LocalDate
import java.time.Month


@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class OnlineLibAppTests {

    //val url = "http://localhost:8080/"
    //val testRestTemplate: TestRestTemplate = TestRestTemplate("user", passwordEncoder.encodePassword("password"))

    @Autowired
    lateinit var authorController: AuthorController
    @Autowired
    lateinit var bookController: BookController
    @Autowired
    lateinit var libraryController: LibraryController

    @Autowired
    lateinit var userRepository: UserRepository
    @Autowired
    lateinit var authorRepository: AuthorRepository
    @Autowired
    lateinit var bookService: BookService

    @Test
    fun contextLoads() {
        assertSoftly {
            authorController shouldNotBe (null)
            bookController shouldNotBe (null)
            libraryController shouldNotBe (null)
        }
    }

    @Test
    fun testGetBooksEndpoint() {
        val pageable = PageRequest.of(0, 10)
        val filter = BookFilter(null, null, null, BookGenre.Drama)
        val page = bookController.getAllBooks(filter, pageable)

        page.forEach { book -> println(book) }

        assertThat(page).isNotEmpty
    }

    @Test
    fun testGetAuthorsEndpoint() {
        val pageable = PageRequest.of(0, 10)
        val filter = AuthorFilter(null, "Homer",
                BirthdateRequest(LocalDate.of(0, Month.APRIL, 1), BirthdateRequest.Period.After))
        val page = authorController.getAuthors(filter, pageable)

        page.forEach { author -> println(author) }

        assertThat(page).isNotEmpty
    }

    @Test
    fun testQueryBooksByGenre() {
        val pageable = PageRequest.of(0, 10)
        val books = bookService.findByGenre(BookGenre.Drama, pageable)
        assertTrue(books[0].genre == BookGenre.Drama)
    }

    @Test
    fun testQueryAuthorRepositoryByNamePenname() {
        val sortedByName = PageRequest.of(0, 10, Sort.by("name"))
        val authorsByNamePenname = authorRepository.findByNameAndPenname("Homer", "Homer", sortedByName)

        assertTrue(authorsByNamePenname[0].name == "Homer" && authorsByNamePenname[0].penname == "Homer")
    }

    @Test
    fun testQueryFindBeforeBirthdate() {
        val sortedByBirthate = PageRequest.of(0, 10, Sort.by("birthdate"))
        val authorsByBirthdate = authorRepository.findBeforeBirthdate(LocalDate.of(2000, Month.JANUARY, 10), sortedByBirthate)

        assertTrue(authorsByBirthdate.isNotEmpty())
    }

    @Test
    fun testQueryFindAfterBirthdate() {
        val sortedByBirthate = PageRequest.of(0, 10, Sort.by("birthdate"))
        val authorsAfterBirthdate = authorRepository.findAfterBirthdate(LocalDate.of(0, Month.JANUARY, 10), sortedByBirthate)

        assertTrue(authorsAfterBirthdate.isNotEmpty())
    }

    @Test
    fun assertUserRepositoryNotEmpty() {
        assertTrue(userRepository.count() != 0L)
    }

}
