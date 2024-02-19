package com.myproject.library.service;
import com.myproject.library.dto.BookDto;
public interface BookService {
        BookDto getByNameV1(String name);

    BookDto getByNameV2(String name);

    BookDto getByNameV3(String name);
}

