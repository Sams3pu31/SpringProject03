package com.myproject.library.service;

import com.myproject.library.dto.AuthorCreateDto;
import com.myproject.library.dto.AuthorDto;
import com.myproject.library.dto.AuthorUpdateDto;


public interface AuthorService {
    AuthorDto getAuthorById(Long id);

    AuthorDto getAuthorByNameV1(String name);
        AuthorDto getAuthorByNameV2(String name);
        AuthorDto getAuthorByNameV3(String name);

    AuthorDto createAuthor(AuthorCreateDto authorCreateDto);

    AuthorDto updateAuthor(AuthorUpdateDto authorUpdateDto);

    void deleteAuthor(Long id);
}



