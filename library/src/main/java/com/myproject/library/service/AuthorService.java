package com.myproject.library.service;

import com.myproject.library.dto.AuthorDto;


public interface AuthorService {
    AuthorDto getAuthorById(Long id);

    AuthorDto getAuthorByNameV1(String name);
        AuthorDto getAuthorByNameV2(String name);
        AuthorDto getAuthorByNameV3(String name);
    }


