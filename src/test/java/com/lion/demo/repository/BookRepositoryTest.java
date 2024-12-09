package com.lion.demo.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.lion.demo.entity.Book;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@DataJdbcTest
public class BookRepositoryTest {

    @Autowired private BookRepository bookRepository;

    @Test
    void testSaveAndFindBookByTitle(){
        // Given
        Book book = new Book(0L, "title", "author", "company", 20000, "Img", "summary");

        // When
        bookRepository.save(book);

        // Then
        Pageable pageable = PageRequest.of(0, 10);
        List<Book> bookList = bookRepository.findByTitleContaining("title", pageable).getContent();
        int size = bookList.size();
        System.out.println("size: "+size);

        assertThat(bookList).hasSize(1);
        assertThat(bookList.get(0).getTitle()).isEqualTo("title");
        assertThat(bookList.get(0).getPrice()).isEqualTo(20000);

    }

}
