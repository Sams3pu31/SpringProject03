package com.myproject.library.repository;

import com.myproject.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
        @Query(nativeQuery = true, value = "SELECT * FROM BOOK WHERE name = ?")
        Optional<Book> findBookByNameBySql(String name);
        Optional<Book> findBookByName(String name);
}

