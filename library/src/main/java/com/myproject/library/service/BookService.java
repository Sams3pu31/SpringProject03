package com.myproject.library.service;
import com.myproject.library.dto.BookDto;
import com.myproject.library.dto.BookCreateDto;
import com.myproject.library.dto.BookUpdateDto;

import java.util.List;

public interface BookService {
    BookDto getByNameV1(String name);

    BookDto getByNameV2(String name);

    BookDto getByNameV3(String name);


    BookDto createBook(BookCreateDto bookCreateDto);


    BookDto updateBook(BookUpdateDto bookUpdateDto);

    void deleteBook(Long id);

    List<BookDto> getAllBooks();
}


