package com.base.onlinelib

import com.base.onlinelib.entities.Author
import com.base.onlinelib.entities.AuthorDTO
import com.base.onlinelib.entities.DTOConverter
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDate
import java.time.Month

class DTOEntityConversionTests {

    val dtoConverter = DTOConverter()

    @Test
    fun testEntityToDtoConversion() {
        val author = Author("Petr", LocalDate.of(1923, Month.MARCH, 10), "Ivanov")

        val authorDTO = dtoConverter.convertAuthorToDto(author)
        assertTrue(author.name == authorDTO.name)
    }

    @Test
    fun testDtoToEntityConversion() {
        val authorDto = AuthorDTO("Petr", LocalDate.of(1923, Month.MARCH, 10), "Ivanov")

        val author = dtoConverter.convertAuthorDTOToEntity(authorDto)
        assertTrue(author.name == authorDto.name)
    }
}