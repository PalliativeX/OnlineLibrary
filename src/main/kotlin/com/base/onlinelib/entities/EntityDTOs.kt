package com.base.onlinelib.entities

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.time.LocalDate
import java.time.Year

data class AuthorDTO(var name: String,
                     val birthdate: LocalDate,
                     var penname: String,
                     @JsonIgnoreProperties("authors") val books: MutableSet<Book> = mutableSetOf(),
                     val id: Long = -1)

data class BookDTO(var title: String,
                   val publicationYear: Year,
                   val genre: BookGenre,
                   @JsonIgnoreProperties("books") val authors: MutableSet<Author> = mutableSetOf(),
                   val id: Long = -1)
