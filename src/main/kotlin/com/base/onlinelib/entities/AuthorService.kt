package com.base.onlinelib.entities

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class AuthorService(@Autowired val authorRepository: AuthorRepository) {

    fun getAll(): List<Author> {
        return authorRepository.findAll()
    }

    fun getAll(pageable: Pageable): List<Author> {
        return authorRepository.findAll(pageable).toList()
    }

    fun getByIdOrNull(id: Long): Author? {
        return authorRepository.findByIdOrNull(id)
    }

    fun findByNamePenname(name: String, penname: String, pageable: Pageable): List<Author> {
        return authorRepository.findByNamePenname(name, penname, pageable)
    }

    fun findBeforeBirthdate(birthdate: LocalDate, pageable: Pageable): List<Author> {
        return authorRepository.findBeforeBirthdate(birthdate, pageable)
    }

    fun findAfterBirthdate(birthdate: LocalDate, pageable: Pageable): List<Author> {
        return authorRepository.findAfterBirthdate(birthdate, pageable)
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