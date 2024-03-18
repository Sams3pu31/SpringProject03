package com.myproject.library.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myproject.library.dto.BookCreateDto;
import com.myproject.library.dto.BookDto;
import com.myproject.library.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookRestController.class)
@AutoConfigureMockMvc(addFilters = false)
public class BookRestControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetBookByName() throws Exception {
        String bookName = "The Great Gatsby";
        BookDto bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setName(bookName);
        when(bookService.getByNameV1(bookName)).thenReturn(bookDto);
        mockMvc.perform(get("/book")
                        .param("name", bookName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(bookName));
    }
    @Test
    void testGetBookByNameV2() throws Exception {
        String bookName = "The Great Gatsby";
        BookDto bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setName(bookName);
        when(bookService.getByNameV2(bookName)).thenReturn(bookDto);
        mockMvc.perform(get("/book/v2")
                        .param("name", bookName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(bookName));
    }

    @Test
    void testGetBookByNameV3() throws Exception {
        String bookName = "The Great Gatsby";
        BookDto bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setName(bookName);
        when(bookService.getByNameV3(bookName)).thenReturn(bookDto);
        mockMvc.perform(get("/book/v3")
                        .param("name", bookName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(bookName));
    }

    @Test
    void testCreateBook() throws Exception {
        BookCreateDto bookCreateDto = new BookCreateDto();
        bookCreateDto.setName("To Kill a Mockingbird");
        bookCreateDto.setGenreId(1L);
        BookDto bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setName(bookCreateDto.getName());
        when(bookService.createBook(any(BookCreateDto.class))).thenReturn(bookDto);
        mockMvc.perform(post("/book/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookCreateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(bookCreateDto.getName()));
    }

    @Test
    void testUpdateBook() throws Exception {
        BookDto bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setName("1984");
        when(bookService.updateBook(any())).thenReturn(bookDto);
        mockMvc.perform(put("/book/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(bookDto.getName()));
    }
    @Test
    void testDeleteBook() throws Exception {
        Long bookId = 1L;
        mockMvc.perform(delete("/book/delete/{id}", bookId))
                .andExpect(status().isOk());
        verify(bookService, times(1)).deleteBook(bookId);
    }

}
