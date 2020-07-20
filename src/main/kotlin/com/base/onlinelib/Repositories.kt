package com.base.onlinelib

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthorRepository : JpaRepository<Author, Long>

@Repository
interface BookRepository : JpaRepository<Book, Long>