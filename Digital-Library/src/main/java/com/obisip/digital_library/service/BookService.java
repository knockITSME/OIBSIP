package com.obisip.digital_library.service;

import com.obisip.digital_library.entity.Book;
import com.obisip.digital_library.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public Book createBook(Book book) {
        book.setAvailableQuantity(book.getQuantity());
        return bookRepository.save(book);
    }

    public Book updateBook(Long id, Book updatedBook) {

        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        int issuedCopies =
                existingBook.getQuantity() - existingBook.getAvailableQuantity();

        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setAuthor(updatedBook.getAuthor());
        existingBook.setIsbn(updatedBook.getIsbn());
        existingBook.setCategory(updatedBook.getCategory());
        existingBook.setQuantity(updatedBook.getQuantity());

        int newAvailableQuantity =
                updatedBook.getQuantity() - issuedCopies;

        if (newAvailableQuantity < 0) {
            newAvailableQuantity = 0;
        }

        existingBook.setAvailableQuantity(newAvailableQuantity);

        return bookRepository.save(existingBook);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public List<Book> searchByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Book> searchByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author);
    }

    public List<Book> filterByCategory(String category) {
        return bookRepository.findByCategory(category);
    }
}