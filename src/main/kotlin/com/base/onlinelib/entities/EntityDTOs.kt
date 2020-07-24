package com.base.onlinelib.entities

import com.base.onlinelib.entities.Author
import com.base.onlinelib.entities.Book
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

data class AuthorDTO(var name: String,
                     @JsonIgnoreProperties("authors") val books: MutableSet<Book> = mutableSetOf(),
                     val id: Long = -1)

data class BookDTO(var title: String,
                   @JsonIgnoreProperties("books") val authors: MutableSet<Author> = mutableSetOf(),
                   val id: Long = -1)
