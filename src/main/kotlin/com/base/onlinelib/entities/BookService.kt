package com.base.onlinelib.entities

import com.base.onlinelib.BookFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.Year
import au.com.console.jpaspecificationdsl.*

@Service
class BookService(@Autowired val bookRepository: BookRepository) {

    fun getAll(): List<Book> {
        return bookRepository.findAll()
    }

    fun getAll(pageable: Pageable): List<Book> {
        return bookRepository.findAll()
    }

    fun getAll(filter: BookFilter, pageable: Pageable): Page<Book> {

        val genreEqual = if (filter.genre != null) {
            Book::genre.equal(filter.genre)
        } else {
            Specification.where<Book>(null)
        }

        val titleEqual = if (filter.title != null) {
            Book::title.equal(filter.title)
        } else {
            Specification.where<Book>(null)
        }

        val publicationYearEqual = if (filter.year != null) {
            Book::publicationYear.equal(filter.year)
        } else {
            Specification.where<Book>(null)
        }

        val authorsEqual = if (filter.author != null) {
            Book::authors.isMember(filter.author)
        } else {
            Specification.where<Book>(null)
        }

        return bookRepository.findAll(
              Specification.where(genreEqual!! and titleEqual!! and publicationYearEqual!! and authorsEqual!!), pageable)
    }

    fun getByIdOrNull(id: Long): Book? {
        return bookRepository.findByIdOrNull(id)
    }

    fun findByPublicationYear(year: Year, pageable: Pageable): List<Book> {
        return bookRepository.findByPublicationYear(year, pageable)
    }

    fun findByGenre(bookGenre: BookGenre, pageable: Pageable): List<Book> {
        return bookRepository.findByGenre(bookGenre, pageable)
    }

    fun add(author: Book) {
        bookRepository.save(author)
    }

    fun updateTitle(id: Long, title: String) {
        val book = bookRepository.findByIdOrNull(id)

        if (book != null) {
            book.title = title
            bookRepository.save(book)
        }
    }

    fun deleteAllInBatch() {
        bookRepository.deleteAllInBatch()
    }

    fun delete(book: Book) {
        bookRepository.delete(book)
    }

    fun deleteById(id: Long) {
        bookRepository.deleteById(id)
    }
}