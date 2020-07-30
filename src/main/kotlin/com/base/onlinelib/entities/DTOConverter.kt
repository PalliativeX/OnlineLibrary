package com.base.onlinelib.entities

import org.springframework.stereotype.Component

@Component
class DTOConverter
{
    fun convertBookToDto(book: Book): BookDTO {
        return BookDTO(book.title, book.publicationYear, book.genre, book.authors, book.id)
    }

    fun convertBookDTOToEntity(bookDTO: BookDTO): Book {
        return Book(bookDTO.title, bookDTO.publicationYear, bookDTO.genre,  bookDTO.authors, bookDTO.id)
    }

    fun convertBookListToDTOList(bookList: List<Book>): List<BookDTO> {
        val bookDTOs = mutableListOf<BookDTO>()

        for (bookEntity in bookList) {
            bookDTOs += convertBookToDto(bookEntity)
        }

        return bookDTOs.toList()
    }

    fun convertAuthorToDto(author: Author): AuthorDTO {
        return AuthorDTO(author.name, author.birthdate, author.penname, author.books, author.id)
    }

    fun convertAuthorDTOToEntity(authorDTO: AuthorDTO): Author {
        return Author(authorDTO.name, authorDTO.birthdate, authorDTO.penname, authorDTO.books, authorDTO.id)
    }

    fun convertAuthorListToDTOList(authorList: List<Author>): List<AuthorDTO> {
        val authorDTOs = mutableListOf<AuthorDTO>()

        for (authorEntity in authorList) {
            authorDTOs += convertAuthorToDto(authorEntity)
        }

        return authorDTOs.toList()
    }

}