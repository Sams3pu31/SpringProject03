package com.myproject.library.service;


import com.myproject.library.dto.AuthorDto;

public interface AuthorService {
    AuthorDto getAuthorById(Long id);
}
