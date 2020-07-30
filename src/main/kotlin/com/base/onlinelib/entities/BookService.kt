package com.base.onlinelib.entities

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.Year

@Service
class BookService(@Autowired val bookRepository: BookRepository) {

    fun getAll(): List<Book> {
        return bookRepository.findAll()
    }

    fun getAll(pageable: Pageable): List<Book> {
        return bookRepository.findAll()
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