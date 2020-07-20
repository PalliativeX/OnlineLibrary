package com.base.onlinelib

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import javax.persistence.*
import javax.persistence.CascadeType.*

@Entity
data class Author(var name: String,
                  @ManyToMany(fetch = FetchType.LAZY, cascade = [ALL]) val books: MutableSet<Book> = mutableSetOf(),
                  @GeneratedValue @Id val id: Long = -1) {
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

    override fun toString(): String {
        return "Author(name='$name', id=$id)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Author

        if (name != other.name) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + id.hashCode()
        return result
    }

}


@Entity
data class Book(var title: String,
           @ManyToMany(mappedBy = "books") val authors: MutableSet<Author> = mutableSetOf(),
           @GeneratedValue @Id val id: Long = -1) {
    fun addAuthor(author: Author) {
        authors += author
        author.books += this
    }

    fun removeAuthor(author: Author) {
        authors -= author
        author.books -= this
    }

    override fun toString(): String {
        return "Book(title='$title', id=$id)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Book

        if (title != other.title) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + id.hashCode()
        return result
    }

}
