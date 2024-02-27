package com.myproject.library.controller.rest;

import com.myproject.library.dto.BookCreateDto;
import com.myproject.library.dto.BookUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.myproject.library.dto.BookDto;
import com.myproject.library.service.BookService;

@RestController
@RequiredArgsConstructor
public class BookRestController {

    private final BookService bookService;

    @GetMapping("/book")
    BookDto getBookByName(@RequestParam("name") String name) {
        return bookService.getByNameV1(name);
    }
    @GetMapping("/book/v2")
    BookDto getBookByNameV2(@RequestParam("name") String name) {
        return bookService.getByNameV2(name);
    }
    @GetMapping("/book/v3")
    BookDto getBookByNameV3(@RequestParam("name") String name) {
        return bookService.getByNameV3(name);
    }
    @PostMapping("/book/create")
    BookDto createBook(@RequestBody BookCreateDto bookCreateDto) {
        return bookService.createBook(bookCreateDto);
    }
    @PutMapping("/book/update")
    BookDto updateBook(@RequestBody BookUpdateDto bookUpdateDto) {
        return bookService.updateBook(bookUpdateDto);
    }
    @DeleteMapping("/book/delete/{id}")
    void deleteBook(@PathVariable("id") Long id) {
        bookService.deleteBook(id);
    }



}
