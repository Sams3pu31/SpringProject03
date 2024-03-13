package com.myproject.library.controller.rest;

import com.myproject.library.dto.GenreResponseDto;
import com.myproject.library.service.GenreService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GenreRestController {

    private final GenreService genreService;

    @GetMapping("/genre/{id}")
    GenreResponseDto getGenreById(@PathVariable("id") Long id) {
        return genreService.getGenreById(id);
    }
}


