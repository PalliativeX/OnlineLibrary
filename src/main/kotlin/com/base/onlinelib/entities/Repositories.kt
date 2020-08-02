package com.base.onlinelib.entities

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.time.Year
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.TypedQuery


@Repository
interface AuthorRepository : JpaRepository<Author, Long>, JpaSpecificationExecutor<Author> {

    fun findByNameAndPenname(name: String, penname: String, pageable: Pageable): List<Author>

    @Query(value="FROM Author a WHERE a.birthdate< ?1")
    fun findBeforeBirthdate(birthdate: LocalDate, pageable: Pageable): List<Author> // NOTE: before the date

    @Query(value="FROM Author a WHERE a.birthdate> ?1")
    fun findAfterBirthdate(birthdate: LocalDate, pageable: Pageable): List<Author> // NOTE: all authors born after the provided date
}

@Repository
interface BookRepository : JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    fun findByPublicationYear(year: Year, pageable: Pageable): List<Book>
    fun findByGenre(bookGenre: BookGenre, pageable: Pageable): List<Book>
}