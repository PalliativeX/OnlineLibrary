package com.base.onlinelib.entities

import com.base.onlinelib.entities.Author
import com.base.onlinelib.entities.Book
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

data class AuthorDTO(var name: String,
                     @JsonIgnoreProperties("authors") val books: MutableSet<Book>,
                     val id: Long)

data class BookDTO(var title: String,
                   @JsonIgnoreProperties("books") val authors: MutableSet<Author>,
                   val id: Long)
