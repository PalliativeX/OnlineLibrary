package com.base.onlinelib.entities

import org.springframework.data.jpa.domain.Specification
import java.time.LocalDate
import java.time.Year
import javax.persistence.*
import javax.persistence.CascadeType.ALL
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Root

@Entity
data class Author(var name: String,
                  val birthdate: LocalDate,
                  var penname: String,
                  @ManyToMany(fetch = FetchType.EAGER, cascade = [ALL]) val books: MutableSet<Book> = mutableSetOf(),
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
        return "Author(name='$name', birthdate=$birthdate, penname='$penname', id=$id)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Author

        if (name != other.name) return false
        if (birthdate != other.birthdate) return false
        if (penname != other.penname) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + birthdate.hashCode()
        result = 31 * result + penname.hashCode()
        result = 31 * result + id.hashCode()
        return result
    }

}

enum class  BookGenre {
    Drama, Crime, Fantasy, Horror, Action
}

@Entity
data class Book(var title: String,
                val publicationYear: Year,
                val genre: BookGenre,
                @ManyToMany(fetch = FetchType.EAGER, mappedBy = "books") val authors: MutableSet<Author> = mutableSetOf(),
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
        return "Book(title='$title', publicationYear=$publicationYear, genre=$genre, id=$id)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Book

        if (title != other.title) return false
        if (publicationYear != other.publicationYear) return false
        if (genre != other.genre) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + publicationYear.hashCode()
        result = 31 * result + genre.hashCode()
        result = 31 * result + id.hashCode()
        return result
    }

}
