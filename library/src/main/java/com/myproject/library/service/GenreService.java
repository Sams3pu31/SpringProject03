package com.myproject.library.service;

import com.myproject.library.dto.GenreResponseDto;

public interface GenreService {
    GenreResponseDto getGenreById(Long id);
}

