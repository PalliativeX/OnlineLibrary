package com.base.onlinelib

import com.base.onlinelib.entities.Author
import com.base.onlinelib.entities.AuthorDTO
import com.base.onlinelib.entities.DTOConverter
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class DTOEntityConversionTests {

    @Test
    fun testEntityToDtoConversion() {
        val author = Author("Petr")

        val dtoConverter = mock(DTOConverter::class.java)
        `when` (dtoConverter.convertAuthorToDto(author)).thenReturn(AuthorDTO(author.name, author.books, author.id))

        val authorDTO = dtoConverter.convertAuthorToDto(author)
        assertTrue(author.name == authorDTO?.name)
    }

    @Test
    fun testDtoToEntityConversion() {
        val authorDto = AuthorDTO("Petr")

        val dtoConverter = mock(DTOConverter::class.java)
        `when` (dtoConverter.convertAuthorDTOToEntity(authorDto)).thenReturn(Author(authorDto.name, authorDto.books, authorDto.id))

        val author = dtoConverter.convertAuthorDTOToEntity(authorDto)
        assertTrue(author?.name == authorDto.name)
    }
}