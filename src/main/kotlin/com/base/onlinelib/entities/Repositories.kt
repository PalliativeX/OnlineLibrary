package com.base.onlinelib.entities

import com.base.onlinelib.entities.Author
import com.base.onlinelib.entities.Book
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthorRepository : JpaRepository<Author, Long>

@Repository
interface BookRepository : JpaRepository<Book, Long>