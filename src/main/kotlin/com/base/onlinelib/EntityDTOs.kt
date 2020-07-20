package com.base.onlinelib

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

data class AuthorDTO(var name: String,
                     @JsonIgnoreProperties("authors") val books: MutableSet<Book>,
                     val id: Long)

data class BookDTO(var title: String,
                   @JsonIgnoreProperties("books") val authors: MutableSet<Author>,
                   val id: Long)
