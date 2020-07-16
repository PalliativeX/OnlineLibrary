package com.base.onlinelib

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.persistence.*
import javax.persistence.CascadeType.*

@Entity
data class Author(val name: String,
                  @ManyToMany(fetch = FetchType.LAZY, cascade = [ALL]) val books: MutableSet<Book> = mutableSetOf(),
                  @GeneratedValue @Id val id: Int = -1)
{
    // FIXME: Maybe use @JsonIgnoreProperty
    override fun toString(): String {
        return "Author(name='$name', id=$id)"
    }

    fun addBook(book: Book)
    {
        books += book
        book.authors += this
    }

    fun removeBook(book: Book)
    {
        books -= book
        book.authors -= this
    }
}

@Entity
data class Book(val title: String,
           @ManyToMany(mappedBy = "books") val authors: MutableSet<Author> = mutableSetOf(),
           @GeneratedValue @Id val id: Int = -1)
{
    override fun toString(): String {
        return "Book(title='$title', id=$id)"
    }

    fun addAuthor(author: Author)
    {
        authors += author
        author.books += this
    }

    fun removeAuthor(author: Author)
    {
        authors -= author
        author.books -= this
    }
}
