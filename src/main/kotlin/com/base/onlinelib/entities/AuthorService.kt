package com.base.onlinelib.entities

import au.com.console.jpaspecificationdsl.and
import au.com.console.jpaspecificationdsl.equal
import au.com.console.jpaspecificationdsl.greaterThanOrEqualTo
import au.com.console.jpaspecificationdsl.lessThanOrEqualTo
import com.base.onlinelib.AuthorFilter
import com.base.onlinelib.BirthdateRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class AuthorService(@Autowired val authorRepository: AuthorRepository) {

    fun getAll(filter: AuthorFilter, pageable: Pageable): Page<Author> {

        var nameEqual     = Specification.where<Author>(null)
        var pennameEqual  = Specification.where<Author>(null)
        var birthdateSpec = Specification.where<Author>(null)

        if (filter.name != null) {
            nameEqual = Author::name.equal(filter.name)
        }
        if (filter.penname != null) {
            pennameEqual = Author::penname.equal(filter.penname)
        }
        if (filter.birthdateRequest != null) {
            birthdateSpec = if (filter.birthdateRequest.period == BirthdateRequest.Period.Before) {
                Author::birthdate.lessThanOrEqualTo(filter.birthdateRequest.birthdate)
            } else {
                Author::birthdate.greaterThanOrEqualTo(filter.birthdateRequest.birthdate)
            }
        }

        return authorRepository.findAll(Specification.where(nameEqual!! and pennameEqual!! and birthdateSpec!!), pageable)
    }

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
        return authorRepository.findByNameAndPenname(name, penname, pageable)
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