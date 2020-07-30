package com.base.onlinelib

import com.base.onlinelib.entities.Author
import com.base.onlinelib.entities.AuthorDTO
import com.base.onlinelib.entities.DTOConverter
import org.assertj.core.api.Assertions.assertThat
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.time.LocalDate
import java.time.Month

class DTOEntityConversionTests {

    @Test
    fun testEntityToDtoConversion() {
        val author = Author("Petr", LocalDate.of(1923, Month.MARCH, 10), "Ivanov")

        //val dtoConverter = mock(DTOConverter::class.java)
        val dtoConverter = DTOConverter()
        //`when` (dtoConverter.convertAuthorToDto(author)).
        //    thenReturn(AuthorDTO(author.name, author.birthdate, author.penname, author.books, author.id))

        val authorDTO = dtoConverter.convertAuthorToDto(author)
        assertTrue(author.name == authorDTO.name)
    }

    @Test
    fun testDtoToEntityConversion() {
        val authorDto = AuthorDTO("Petr", LocalDate.of(1923, Month.MARCH, 10), "Ivanov")

        val dtoConverter = mock(DTOConverter::class.java)
        `when` (dtoConverter.convertAuthorDTOToEntity(authorDto)).thenReturn(Author(authorDto.name, authorDto.birthdate, authorDto.penname, authorDto.books, authorDto.id))

        val author = dtoConverter.convertAuthorDTOToEntity(authorDto)
        assertTrue(author.name == authorDto.name)
    }
}