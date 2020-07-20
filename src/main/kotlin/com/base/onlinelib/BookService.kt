package com.base.onlinelib

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthorService(@Autowired val authorRepository: AuthorRepository) {
    fun getAuthorById(id: Long): Author?
    {
        return authorRepository.findByIdOrNull(id)
    }

    fun getAllAuthors(): List<Author> {
        return authorRepository.findAll()
    }

    fun addAuthor(author: Author) {
        authorRepository.save(author)
    }

    fun updateAuthorName(id: Long, name: String) {
        val author = authorRepository.findByIdOrNull(id)

        if (author != null) {
            author.name = name
            authorRepository.save(author)
        }
    }

    fun clear() {
        authorRepository.deleteAllInBatch()
    }

    fun deleteAuthor(author: Author) {
        authorRepository.delete(author)
    }

    fun deleteAuthor(id: Long) {
        authorRepository.deleteById(id)
    }
}