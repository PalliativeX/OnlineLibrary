package com.base.onlinelib

import com.base.onlinelib.entities.AuthorRepository
import com.base.onlinelib.security.PasswordEncoder
import com.base.onlinelib.security.UserRepository
import io.kotest.assertions.assertSoftly
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldContain
import org.assertj.core.api.Assertions.assertThat
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner


@RunWith(SpringRunner::class)
@SpringBootTest
class OnlineLibAppTests {

	@Autowired lateinit var authorController: AuthorController
	@Autowired lateinit var bookController: BookController
	@Autowired lateinit var libraryController: LibraryController

	@Autowired lateinit var userRepository: UserRepository
	@Autowired lateinit var authorRepository: AuthorRepository

	@Autowired lateinit var passwordEncoder: PasswordEncoder

	@Test
	fun contextLoads() {

		assertSoftly {
			authorController shouldNotBe(null)
			bookController shouldNotBe(null)
			libraryController shouldNotBe(null)
		}
	}

	@Test
	fun testGetAuthorById() {
		val author = authorController.getAuthorById(24)
		assertThat(author?.name).isEqualTo("Homer")
	}

	@Test
	fun testAddAuthor() {
		val author = authorController.addAuthor("Virgil")
		assertTrue(author.name == "Virgil")
	}

	@Test
	fun assertUserRepositoryNotEmpty() {
		assertTrue(userRepository.count() != 0L)
	}

	@Test
	fun testPasswordEncoderVersion() {
		val encodedPassword = passwordEncoder.encodePassword("password")
		assertTrue(encodedPassword.startsWith("$2a"))
	}

}
