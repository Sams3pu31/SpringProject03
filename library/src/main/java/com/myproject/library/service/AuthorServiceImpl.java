package com.myproject.library.service;

import com.myproject.library.dto.AuthorCreateDto;
import com.myproject.library.dto.AuthorDto;
import com.myproject.library.dto.AuthorUpdateDto;
import com.myproject.library.dto.BookDto;
import com.myproject.library.model.Author;
import com.myproject.library.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public List<AuthorDto> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        return authors.stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }


    @Override
    public AuthorDto getAuthorById(Long id) {
        log.info("Try to find author by id {}", id);
        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent()) {
            AuthorDto authorDto = convertEntityToDto(author.get());
            log.info("Author: {}", authorDto.toString());
            return authorDto;
        } else {
            log.error("Author with id {} not found", id);
            throw new NoSuchElementException("No value present");
        }
    }

    @Override
    public AuthorDto getAuthorByNameV1(String name) {
        log.info("Try to find author by name: {}", name);
        List<Author> authors = authorRepository.findAuthorsByName(name);
        AuthorDto authorDto = convertToDto(authors);
        if (authorDto != null) {
            log.info("Author found: {}", authorDto.toString());
        } else {
            log.error("Author with name {} not found", name);
        }
        return authorDto;
    }

    @Override
    public AuthorDto getAuthorByNameV2(String name) {
        log.info("Try to find author by name using query: {}", name);
        List<Author> authors = authorRepository.findAuthorsByNameUsingQuery(name);
        AuthorDto authorDto = convertToDto(authors);
        if (authorDto != null) {
            log.info("Author found: {}", authorDto.toString());
        } else {
            log.error("Author with name {} not found", name);
        }
        return authorDto;
    }

    @Override
    public AuthorDto getAuthorByNameV3(String name) {
        log.info("Try to find author by name using named query: {}", name);
        List<Author> authors = authorRepository.findByName(name);
        AuthorDto authorDto = convertToDto(authors);
        if (authorDto != null) {
            log.info("Author found: {}", authorDto.toString());
        } else {
            log.error("Author with name {} not found", name);
        }
        return authorDto;
    }

    @Override
    public AuthorDto createAuthor(AuthorCreateDto authorCreateDto) {
        log.info("Creating author: {}", authorCreateDto.toString());
        Author author = authorRepository.save(convertDtoToEntity(authorCreateDto));
        AuthorDto authorDto = convertEntityToDto(author);
        log.info("Author created: {}", authorDto.toString());
        return authorDto;
    }

    @Override
    public AuthorDto updateAuthor(AuthorUpdateDto authorUpdateDto) {
        log.info("Updating author with ID: {}", authorUpdateDto.getId());
        Author author = authorRepository.findById(authorUpdateDto.getId()).orElseThrow(() -> new RuntimeException("Author not found"));
        author.setName(authorUpdateDto.getName());
        author.setSurname(authorUpdateDto.getSurname());
        Author updatedAuthor = authorRepository.save(author);
        AuthorDto updatedAuthorDto = convertEntityToDto(updatedAuthor);
        log.info("Author updated: {}", updatedAuthorDto.toString());
        return updatedAuthorDto;
    }


    @Override
    public void deleteAuthor(Long id) {
        log.info("Deleting author with ID: {}", id);
        authorRepository.deleteById(id);
        log.info("Author deleted with ID: {}", id);
    }

    private AuthorDto convertEntityToDto(Author author) {
        List<BookDto> bookDtoList;
        if (author.getBooks() != null && !author.getBooks().isEmpty()) {
            bookDtoList = author.getBooks()
                    .stream()
                    .map(book -> BookDto.builder()
                            .genre(book.getGenre().getName())
                            .name(book.getName())
                            .id(book.getId())
                            .build())
                    .collect(Collectors.toList());
        } else {
            bookDtoList = null;
        }
        return AuthorDto.builder()
                .id(author.getId())
                .name(author.getName())
                .surname(author.getSurname())
                .books(bookDtoList)
                .build();
    }

    private Author convertDtoToEntity(AuthorCreateDto authorCreateDto) {
        return Author.builder()
                .name(authorCreateDto.getName())
                .surname(authorCreateDto.getSurname())
                .build();
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
