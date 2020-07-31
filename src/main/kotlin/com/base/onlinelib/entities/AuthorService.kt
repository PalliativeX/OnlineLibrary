package com.base.onlinelib.entities

import au.com.console.jpaspecificationdsl.*
import com.base.onlinelib.AuthorFilter
import com.base.onlinelib.BirthdateRequest
import com.base.onlinelib.BookFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDate

class AuthorFilter(val name: String? = null, val penname: Author? = null, val birthdateRequest: BirthdateRequest? = null)

@Service
class AuthorService(@Autowired val authorRepository: AuthorRepository) {

    fun getAll(filter: AuthorFilter, pageable: Pageable): Page<Author> {

        var nameEqual: Specification<Author>? = Specification.where(null)
        var pennameEqual: Specification<Author>? = Specification.where(null)
        var birthdateSpec: Specification<Author>? = Specification.where(null)

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