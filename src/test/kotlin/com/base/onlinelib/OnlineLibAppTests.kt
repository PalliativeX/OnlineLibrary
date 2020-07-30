package com.base.onlinelib

import com.base.onlinelib.entities.*
import com.base.onlinelib.security.PasswordEncoder
import com.base.onlinelib.security.UserRepository
import io.kotest.assertions.assertSoftly
import io.kotest.matchers.collections.beEmpty
import io.kotest.matchers.collections.shouldStartWith
import io.kotest.matchers.maps.beEmpty
import io.kotest.matchers.shouldNotBe
import org.assertj.core.api.Assertions.assertThat
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.http.client.support.BasicAuthorizationInterceptor
import org.springframework.test.context.junit4.SpringRunner
import java.time.LocalDate
import java.time.Month
import java.util.*


@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class OnlineLibAppTests {

    val url = "http://localhost:8080/"
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

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @Test
    fun contextLoads() {

        assertSoftly {
            authorController shouldNotBe (null)
            bookController shouldNotBe (null)
            libraryController shouldNotBe (null)
        }
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
        val authorsByNamePenname = authorRepository.findByNamePenname("Homer", "Homer", sortedByName)

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
    fun testGetAuthorGenres() {
        val authors = authorController.getAuthorsByBirthdate(
                BirthdateRequest(LocalDate.of(1000, Month.APRIL, 1), BirthdateRequest.Period.Before))

        assertTrue(authors.isNotEmpty())
    }

    @Test
    fun `test get authors by name and penname`() {
        val authors = authorController.getAuthorsByNameAndPenname(AuthorNamePennameRequest("Homer", "Homer"))

        assertTrue(authors?.get(0)?.name == "Homer" && authors[0].penname == "Homer")
    }

	@Test
	fun testGetAuthorById() {
		val author = authorController.getAuthorById(24)
		assertNotNull(author?.name)
	}

    @Test
    fun testAddAuthor() {
        val authorDTO = authorController.addAuthor(AuthorDTO("Publius Vergilius Maro", LocalDate.of(70, Month.OCTOBER, 15), "Virgil"))
        assertTrue(authorDTO.penname == "Virgil")
    }

    @Test
    fun assertUserRepositoryNotEmpty() {
        assertTrue(userRepository.count() != 0L)
    }

}
