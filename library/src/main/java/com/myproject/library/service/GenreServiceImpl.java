package com.myproject.library.service;

import com.myproject.library.dto.AuthorDto;
import com.myproject.library.dto.BookDto;
import com.myproject.library.dto.GenreResponseDto;
import com.myproject.library.model.Genre;
import com.myproject.library.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    public GenreResponseDto getGenreById(Long id) {
        log.info("Trying to find genre by ID: {}", id);
        Genre genre = genreRepository.findById(id).orElseThrow();
        log.info("Genre found: {}", genre);
        return convertToDto(genre);
    }

    private GenreResponseDto convertToDto(Genre genre) {
        List<BookDto> bookDtoList = genre.getBooks()
                .stream()
                .map(book -> {
                    List<AuthorDto> authorDtoList = book.getAuthors()
                            .stream()
                            .map(author -> AuthorDto.builder()
                                    .id(author.getId())
                                    .name(author.getName())
                                    .surname(author.getSurname())
                                    .build())
                            .collect(Collectors.toList());

                    return BookDto.builder()
                            .id(book.getId())
                            .name(book.getName())
                            .authors(authorDtoList)
                            .build();
                })
                .collect(Collectors.toList());

        return GenreResponseDto.builder()
                .id(genre.getId())
                .genre(genre.getName())
                .books(bookDtoList)
                .build();
    }
}
