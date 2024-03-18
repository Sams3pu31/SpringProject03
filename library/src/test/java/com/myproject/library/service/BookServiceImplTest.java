package com.myproject.library.service;

import com.myproject.library.dto.BookCreateDto;
import com.myproject.library.dto.BookDto;
import static org.mockito.Mockito.when;

import com.myproject.library.dto.BookUpdateDto;
import com.myproject.library.model.Book;
import com.myproject.library.model.Genre;
import com.myproject.library.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    public void testGetByNameV1_WhenBookExists() {
        String bookName = "Test Book";
        Book book = new Book();
        book.setName(bookName);
        when(bookRepository.findBookByName(anyString())).thenReturn(Optional.of(book));
        BookDto result = bookService.getByNameV1(bookName);
        assertNotNull(result);
        assertEquals(bookName, result.getName());
        verify(bookRepository, times(1)).findBookByName(anyString());
    }

    @Test
    public void testGetByNameV1_WhenBookDoesNotExist() {
        String bookName = "Non-existent Book";
        when(bookRepository.findBookByName(anyString())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> bookService.getByNameV1(bookName));
        verify(bookRepository, times(1)).findBookByName(anyString());
    }


    @Test
    public void testGetByNameV2_WhenBookExists() {
        String bookName = "Test Book";
        Book book = new Book();
        book.setName(bookName);
        when(bookRepository.findBookByNameBySql(anyString())).thenReturn(Optional.of(book));
        BookDto result = bookService.getByNameV2(bookName);
        assertNotNull(result);
        assertEquals(bookName, result.getName());
        verify(bookRepository, times(1)).findBookByNameBySql(anyString());
    }

    @Test
    public void testGetByNameV2_WhenBookDoesNotExist() {
        String bookName = "Non-existent Book";
        when(bookRepository.findBookByNameBySql(anyString())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> bookService.getByNameV2(bookName));
        verify(bookRepository, times(1)).findBookByNameBySql(anyString());
    }
    @Test
    public void testGetByNameV3_WhenBookExists() {
        String bookName = "Test Book";
        Book book = new Book();
        book.setName(bookName);
        when(bookRepository.findOne(any(Specification.class))).thenReturn(Optional.of(book)); // явное указание типа
        BookDto result = bookService.getByNameV3(bookName);
        assertNotNull(result);
        assertEquals(bookName, result.getName());
        verify(bookRepository, times(1)).findOne(any(Specification.class)); // явное указание типа
    }

    @Test
    public void testGetByNameV3_WhenBookDoesNotExist() {
        String bookName = "Non-existent Book";
        when(bookRepository.findOne(any(Specification.class))).thenReturn(Optional.empty()); // явное указание типа
        assertThrows(NoSuchElementException.class, () -> bookService.getByNameV3(bookName));
        verify(bookRepository, times(1)).findOne(any(Specification.class)); // явное указание типа
    }

    @Test
    public void testCreateBook() {
        BookCreateDto bookCreateDto = new BookCreateDto();
        bookCreateDto.setName("bookname");
        bookCreateDto.setGenreId(1L);

        Book book = new Book();
        book.setName(bookCreateDto.getName());
        book.setGenre(new Genre());

        when(bookRepository.save(any(Book.class))).thenReturn(book);

        BookDto bookDto = bookService.createBook(bookCreateDto);

        assertEquals(bookDto.getName(), bookCreateDto.getName());
    }

    @Test
    public void testUpdateBook() {
        long bookId = 1L;
        String updatedName = "Updated Book";
        BookUpdateDto bookUpdateDto = new BookUpdateDto();
        bookUpdateDto.setId(bookId);
        bookUpdateDto.setName(updatedName);
        Book existingBook = new Book();
        existingBook.setName("Old Book Name");
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(any(Book.class))).thenAnswer(invocation -> invocation.getArgument(0));
        BookDto result = bookService.updateBook(bookUpdateDto);
        assertNotNull(result);
        assertEquals(updatedName, result.getName());
        verify(bookRepository, times(1)).findById(bookId);
        verify(bookRepository, times(1)).save(existingBook);
    }

    @Test
    public void testDeleteBook() {
        Long id = 1L;
        bookService.deleteBook(id);
        verify(bookRepository, times(1)).deleteById(id);
    }
}

