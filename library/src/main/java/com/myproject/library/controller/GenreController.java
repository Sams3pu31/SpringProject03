package com.myproject.library.controller;

import com.myproject.library.dto.GenreResponseDto;
import com.myproject.library.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @GetMapping("/genre/{id}")
    GenreResponseDto getGenreById(@PathVariable("id") Long id) {
        return genreService.getGenreById(id);
    }
}


