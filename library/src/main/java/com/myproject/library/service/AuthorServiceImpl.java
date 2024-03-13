package com.myproject.library.service;

import com.myproject.library.dto.AuthorCreateDto;
import com.myproject.library.dto.AuthorDto;
import com.myproject.library.dto.AuthorUpdateDto;
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
    public List<AuthorDto> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        return authors.stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    @Override
    public AuthorDto getAuthorById(Long id) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new RuntimeException("Author not found"));
        return convertEntityToDto(author);
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

    @Override
    public AuthorDto createAuthor(AuthorCreateDto authorCreateDto) {
        Author author = authorRepository.save(convertDtoToEntity(authorCreateDto));
        return convertEntityToDto(author);
    }

    @Override
    public AuthorDto updateAuthor(AuthorUpdateDto authorUpdateDto) {
        Author author = authorRepository.findById(authorUpdateDto.getId()).orElseThrow(() -> new RuntimeException("Author not found"));
        author.setName(authorUpdateDto.getName());
        author.setSurname(authorUpdateDto.getSurname());
        Author updatedAuthor = authorRepository.save(author);
        return convertEntityToDto(updatedAuthor);
    }

    @Override
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
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
