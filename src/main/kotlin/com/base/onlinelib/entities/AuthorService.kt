package com.base.onlinelib.entities

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class AuthorService(@Autowired val authorRepository: AuthorRepository) {
    fun getByIdOrNull(id: Long): Author? {
        return authorRepository.findByIdOrNull(id)
    }

    fun getAll(): List<Author> {
        return authorRepository.findAll()
    }

    fun add(author: Author) {
        authorRepository.save(author)
    }

    fun updateName(id: Long, name: String) {
        val author = authorRepository.findByIdOrNull(id)

        if (author != null) {
            author.name = name
            authorRepository.save(author)
        }
    }

    fun deleteAllInBatch() {
        authorRepository.deleteAllInBatch()
    }

    fun delete(author: Author) {
        authorRepository.delete(author)
    }

    fun deleteById(id: Long) {
        authorRepository.deleteById(id)
    }
}