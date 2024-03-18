package com.myproject.library.service;

import com.myproject.library.dto.AuthorCreateDto;
import com.myproject.library.dto.AuthorDto;
import com.myproject.library.dto.AuthorUpdateDto;
import com.myproject.library.model.Author;
import com.myproject.library.model.Book;
import com.myproject.library.repository.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorServiceImpl authorService;

    @Test
    public void testGetAuthorById() {
        Long id = 1L;
        String name = "John";
        String surname = "Doe";
        Set<Book> books = new HashSet<>();
        Author author = new Author(id, name, surname, books);

        when(authorRepository.findById(id)).thenReturn(Optional.of(author));

        AuthorDto authorDto = authorService.getAuthorById(id);

        verify(authorRepository).findById(id);
        assertEquals(authorDto.getId(), author.getId());
        assertEquals(authorDto.getName(), author.getName());
        assertEquals(authorDto.getSurname(), author.getSurname());
    }
    @Test
    public void testGetAuthorByIdNotFound() {
        Long id = 1L;
        when(authorRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> authorService.getAuthorById(id));
        verify(authorRepository).findById(id);
    }

    @Test
    public void testGetAuthorByNameV1() {
        String name = "John";
        String surname = "Doe";
        Set<Book> books = new HashSet<>();
        Author author = new Author(1L, name, surname, books);

        when(authorRepository.findAuthorsByName(name)).thenReturn(Collections.singletonList(author));

        AuthorDto authorDto = authorService.getAuthorByNameV1(name);

        verify(authorRepository).findAuthorsByName(name);
        assertEquals(authorDto.getName(), author.getName());
        assertEquals(authorDto.getSurname(), author.getSurname());
    }
    @Test
    public void testGetAuthorByNameV1NotFound() {
        String name = "John";
        when(authorRepository.findAuthorsByName(name)).thenReturn(Collections.emptyList());
        assertThrows(NoSuchElementException.class, () -> authorService.getAuthorByNameV1(name));
        verify(authorRepository).findAuthorsByName(name);
    }

    @Test
    public void testGetAuthorByNameV2() {
        String name = "John";
        String surname = "Doe";
        Set<Book> books = new HashSet<>();
        Author author = new Author(1L, name, surname, books);

        when(authorRepository.findAuthorsByNameUsingQuery(name)).thenReturn(Collections.singletonList(author));

        AuthorDto authorDto = authorService.getAuthorByNameV2(name);

        verify(authorRepository).findAuthorsByNameUsingQuery(name);
        assertEquals(authorDto.getName(), author.getName());
        assertEquals(authorDto.getSurname(), author.getSurname());
    }
    @Test
    public void testGetAuthorByNameV2NotFound() {
        String name = "John";
        when(authorRepository.findAuthorsByNameUsingQuery(name)).thenReturn(Collections.emptyList());
        assertThrows(NoSuchElementException.class, () -> authorService.getAuthorByNameV2(name));
        verify(authorRepository).findAuthorsByNameUsingQuery(name);
    }

    @Test
    public void testGetAuthorByNameV3() {
        String name = "John";
        String surname = "Doe";
        Set<Book> books = new HashSet<>();
        Author author = new Author(1L, name, surname, books);

        when(authorRepository.findByName(name)).thenReturn(Collections.singletonList(author));

        AuthorDto authorDto = authorService.getAuthorByNameV3(name);

        verify(authorRepository).findByName(name);
        assertEquals(authorDto.getName(), author.getName());
        assertEquals(authorDto.getSurname(), author.getSurname());
    }
    @Test
    public void testGetAuthorByNameV3NotFound() {
        String name = "John";
        when(authorRepository.findByName(name)).thenReturn(Collections.emptyList());
        assertThrows(NoSuchElementException.class, () -> authorService.getAuthorByNameV3(name));
        verify(authorRepository).findByName(name);
    }

    @Test
    public void testCreateAuthor() {
        String name = "John";
        String surname = "Doe";
        AuthorCreateDto authorCreateDto = new AuthorCreateDto(name, surname);
        Author author = new Author(1L, name, surname, new HashSet<>());

        when(authorRepository.save(any())).thenReturn(author);

        AuthorDto authorDto = authorService.createAuthor(authorCreateDto);

        verify(authorRepository).save(any());
        assertEquals(authorDto.getName(), name);
        assertEquals(authorDto.getSurname(), surname);
    }

    @Test
    public void testUpdateAuthor() {
        Long id = 1L;
        String name = "John";
        String surname = "Doe";
        AuthorUpdateDto authorUpdateDto = new AuthorUpdateDto(id, "Updated " + name, "Updated " + surname);
        Author author = new Author(id, name, surname, new HashSet<>());

        when(authorRepository.findById(id)).thenReturn(Optional.of(author));
        when(authorRepository.save(any())).thenReturn(author);

        AuthorDto updatedAuthorDto = authorService.updateAuthor(authorUpdateDto);

        verify(authorRepository).findById(id);
        verify(authorRepository).save(any());
        assertEquals(updatedAuthorDto.getName(), "Updated " + name);
        assertEquals(updatedAuthorDto.getSurname(), "Updated " + surname);
    }
    @Test
    public void testUpdateAuthorNotFound() {
        Long id = 1L;
        AuthorUpdateDto authorUpdateDto = new AuthorUpdateDto(id, "Updated John", "Updated Doe");
        when(authorRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> authorService.updateAuthor(authorUpdateDto));
        verify(authorRepository).findById(id);
    }

    @Test
    public void testDeleteAuthor() {
        Long id = 1L;

        authorService.deleteAuthor(id);

        verify(authorRepository).deleteById(id);
    }
}
