package com.myproject.library.service;

import com.myproject.library.dto.BookCreateDto;
import com.myproject.library.dto.BookDto;
import com.myproject.library.dto.BookUpdateDto;
import com.myproject.library.model.Genre;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.myproject.library.model.Book;
import com.myproject.library.repository.BookRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    @Override
    public BookDto getByNameV1(String name) {
        log.info("Trying to find book by name (V1): {}", name);
        Book book = bookRepository.findBookByName(name).orElseThrow();
        log.info("Book found: {}", book);
        return convertEntityToDto(book);
    }
    @Override
    public BookDto getByNameV2(String name) {
        log.info("Trying to find book by name using SQL query (V2): {}", name);
        Book book = bookRepository.findBookByNameBySql(name).orElseThrow();
        log.info("Book found: {}", book);
        return convertEntityToDto(book);
    }
    @Override
    public BookDto getByNameV3(String name) {
        log.info("Trying to find book by name using specification (V3): {}", name);
        Specification<Book> specification = (root, query, cb) -> cb.equal(root.get("name"), name);
        Optional<Book> optionalBook = bookRepository.findOne(specification);
        Book book = optionalBook.orElseThrow(() -> new NoSuchElementException("Book not found with name: " + name));

        log.info("Book found: {}", book);
        return convertEntityToDto(book);
    }

    @Override
    public BookDto createBook(BookCreateDto bookCreateDto) {
        log.info("Creating book: {}", bookCreateDto);
        Book book = convertDtoToEntity(bookCreateDto);
        book = bookRepository.save(book);
        log.info("Book created: {}", book);
        return convertEntityToDto(book);
    }

    @Override
    public BookDto updateBook(BookUpdateDto bookUpdateDto) {
        log.info("Updating book with ID: {}", bookUpdateDto.getId());
        Book book = bookRepository.findById(bookUpdateDto.getId()).orElseThrow();
        book.setName(bookUpdateDto.getName());
        book = bookRepository.save(book);
        log.info("Book updated: {}", book);
        return convertEntityToDto(book);
    }

    @Override
    public void deleteBook(Long id) {
        log.info("Deleting book with ID: {}", id);
        bookRepository.deleteById(id);
        log.info("Book deleted with ID: {}", id);
    }

    public BookDto convertEntityToDto(Book book) {
        if (book == null) {
            return null;
        }

        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setName(book.getName());

        if (book.getGenre() != null) {
            bookDto.setGenre(book.getGenre().getName());
        } else {
            bookDto.setGenre(null);
        }


        return bookDto;
    }

    private Book convertDtoToEntity(BookCreateDto bookCreateDto) {
        Book book = new Book();
        book.setName(bookCreateDto.getName());
        Genre genre = new Genre();
        genre.setId(bookCreateDto.getGenreId());
        book.setGenre(genre);

        return book;
    }
    @Override
    public List<BookDto> getAllBooks() {
        log.info("Getting all books");
        List<Book> books = bookRepository.findAll();
        log.info("Retrieved {} books", books.size());
        return books.stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }
}
