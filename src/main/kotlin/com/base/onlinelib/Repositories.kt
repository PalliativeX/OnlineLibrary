package com.base.onlinelib

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthorRepository : JpaRepository<Author, Int>

@Repository
interface BookRepository : JpaRepository<Book, Int>