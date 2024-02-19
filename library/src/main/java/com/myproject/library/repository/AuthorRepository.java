package com.myproject.library.repository;

import com.myproject.library.dto.AuthorDto;
import com.myproject.library.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long>, JpaSpecificationExecutor<Author> {
    AuthorDto getAuthorById(Long id);
    @Query("SELECT a FROM Author a WHERE a.name = :name")
    List<Author> findAuthorsByNameUsingQuery(String name);
    List<Author> findAuthorsByName(String name);
    List<Author> findByName(String name);
}

