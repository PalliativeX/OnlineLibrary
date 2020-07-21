package com.base.onlinelib.entities

import com.base.onlinelib.entities.Author
import com.base.onlinelib.entities.AuthorDTO
import com.base.onlinelib.entities.Book
import com.base.onlinelib.entities.BookDTO
import org.springframework.stereotype.Component

@Component
class DTOConverter
{
    fun convertBookToDto(book: Book?): BookDTO? {
        return if (book != null)
            BookDTO(book.title, book.authors, book.id)
        else null
    }

    fun convertBookDTOToEntity(bookDTO: BookDTO?): Book? {
        return if (bookDTO != null)
            Book(bookDTO.title, bookDTO.authors, bookDTO.id)
        else null
    }

    fun convertAuthorToDto(author: Author?): AuthorDTO? {
        return if (author != null)
            AuthorDTO(author.name, author.books, author.id)
        else null
    }

    fun convertAuthorDTOToEntity(authorDTO: AuthorDTO?): Author? {
        return if (authorDTO != null)
            Author(authorDTO.name, authorDTO.books, authorDTO.id)
        else null
    }

}