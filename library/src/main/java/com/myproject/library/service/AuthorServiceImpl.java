package com.myproject.library.service;

import com.myproject.library.dto.AuthorDto;
import com.myproject.library.dto.BookDto;
import com.myproject.library.model.Author;
import com.myproject.library.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    @Override
    public AuthorDto getAuthorById(Long id) {
        Author author = authorRepository.findById(id).orElseThrow();
        return convertToDto(author);
    }

    private AuthorDto convertToDto(Author author) {
        List<BookDto> bookDtoList = author.getBooks()
                .stream()
                .map(book -> BookDto.builder()
                        .genre(book.getGenre().getName())
                        .name(book.getName())
                        .id(book.getId())
                        .build()
                ).toList();
        return AuthorDto.builder()
                .books(bookDtoList)
                .id(author.getId())
                .name(author.getName())
                .surname(author.getSurname())
                .build();
    }

    @Override
    public AuthorDto getAuthorByNameV1(String name) {
        List<Author> authors = authorRepository.findAuthorsByName(name);
        return convertToDto(authors);
    }

    @Override
    public AuthorDto getAuthorByNameV2(String name) {
        List<Author> authors = authorRepository.findAuthorsByNameUsingQuery(name);
        return convertToDto(authors);
    }

    @Override
    public AuthorDto getAuthorByNameV3(String name) {
        List<Author> authors = authorRepository.findByName(name);
        return convertToDto(authors);
    }

    private AuthorDto convertToDto(List<Author> authors) {
        if (authors.isEmpty()) {
            return null;
        }

        Author author = authors.get(0);
        List<BookDto> bookDtoList = author.getBooks()
                .stream()
                .map(book -> BookDto.builder()
                        .genre(book.getGenre().getName())
                        .name(book.getName())
                        .id(book.getId())
                        .build())
                .collect(Collectors.toList());

        return AuthorDto.builder()
                .books(bookDtoList)
                .id(author.getId())
                .name(author.getName())
                .surname(author.getSurname())
                .build();
    }
}
