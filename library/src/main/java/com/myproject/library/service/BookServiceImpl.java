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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.myproject.library.model.Book;
import com.myproject.library.repository.BookRepository;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    @Override
    public BookDto getByNameV1(String name) {
        Book book = bookRepository.findBookByName(name).orElseThrow();
        return convertEntityToDto(book);
    }
    @Override
    public BookDto getByNameV2(String name) {
        Book book = bookRepository.findBookByNameBySql(name).orElseThrow();
        return convertEntityToDto(book);
    }
    @Override
    public BookDto getByNameV3(String name) {
        Specification<Book> specification = Specification.where(new Specification<Book>() {
            @Override
            public Predicate toPredicate(Root<Book> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder cb) {
                return cb.equal(root.get("name"), name);
            }
        });

        Book book = bookRepository.findOne(specification).orElseThrow();
        return convertEntityToDto(book);
    }
    @Override
    public BookDto createBook(BookCreateDto bookCreateDto) {
        Book book = convertDtoToEntity(bookCreateDto);
        book = bookRepository.save(book);
        return convertEntityToDto(book);
    }

    @Override
    public BookDto updateBook(BookUpdateDto bookUpdateDto) {
        Book book = bookRepository.findById(bookUpdateDto.getId()).orElseThrow();
        book.setName(bookUpdateDto.getName());
        book = bookRepository.save(book);
        return convertEntityToDto(book);
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
    private BookDto convertEntityToDto(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .genre(book.getGenre().getName())
                .name(book.getName())
                .build();
    }

    private Book convertDtoToEntity(BookCreateDto bookCreateDto) {
        Book book = new Book();
        book.setName(bookCreateDto.getName());

        // Установка жанра книги
        Genre genre = new Genre();
        genre.setId(bookCreateDto.getGenreId());
        book.setGenre(genre);

        return book;
    }
}
