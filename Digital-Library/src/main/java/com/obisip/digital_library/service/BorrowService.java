package com.obisip.digital_library.service;

import com.obisip.digital_library.entity.Book;
import com.obisip.digital_library.entity.BorrowRecord;
import com.obisip.digital_library.entity.User;
import com.obisip.digital_library.repository.BookRepository;
import com.obisip.digital_library.repository.BorrowRecordRepository;
import com.obisip.digital_library.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class BorrowService {

    private final BorrowRecordRepository borrowRecordRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public BorrowService(
            BorrowRecordRepository borrowRecordRepository,
            BookRepository bookRepository,
            UserRepository userRepository) {

        this.borrowRecordRepository = borrowRecordRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public BorrowRecord issueBook(Long userId, Long bookId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (book.getAvailableQuantity() <= 0) {
            throw new RuntimeException("Book is currently unavailable");
        }

        book.setAvailableQuantity(book.getAvailableQuantity() - 1);
        bookRepository.save(book);

        BorrowRecord record = new BorrowRecord();

        record.setUser(user);
        record.setBook(book);
        record.setIssueDate(LocalDate.now());
        record.setDueDate(LocalDate.now().plusDays(14));
        record.setReturned(false);
        record.setFine(0);
        record.setFinePaid(false);

        return borrowRecordRepository.save(record);
    }

    public BorrowRecord returnBook(Long borrowId) {

        BorrowRecord record = borrowRecordRepository.findById(borrowId)
                .orElseThrow(() -> new RuntimeException("Borrow record not found"));

        if (record.isReturned()) {
            throw new RuntimeException("Book has already been returned");
        }

        LocalDate returnDate = LocalDate.now();

        record.setReturnDate(returnDate);
        record.setReturned(true);

        // Calculate fine: ₹5 per overdue day
        if (returnDate.isAfter(record.getDueDate())) {

            long overdueDays = ChronoUnit.DAYS.between(
                    record.getDueDate(),
                    returnDate
            );

            double fine = overdueDays * 5.0;

            record.setFine(fine);
            record.setFinePaid(false);

        } else {

            record.setFine(0);
            record.setFinePaid(false);
        }

        Book book = record.getBook();

        book.setAvailableQuantity(
                book.getAvailableQuantity() + 1
        );

        bookRepository.save(book);

        return borrowRecordRepository.save(record);
    }

    public List<BorrowRecord> getAllBorrowRecords() {
        return borrowRecordRepository.findAll();
    }

    public List<BorrowRecord> getUserBorrowRecords(Long userId) {

        return borrowRecordRepository.findByUserId(userId);
    }

    public BorrowRecord getBorrowRecordById(Long id) {

        return borrowRecordRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Borrow record not found"));
    }

    // Admin marks a fine as paid
    public BorrowRecord markFinePaid(Long borrowId) {

        BorrowRecord record = borrowRecordRepository.findById(borrowId)
                .orElseThrow(() ->
                        new RuntimeException("Borrow record not found"));

        if (record.getFine() <= 0) {
            throw new RuntimeException("No fine exists for this record");
        }

        if (record.isFinePaid()) {
            throw new RuntimeException("Fine is already marked as paid");
        }

        record.setFinePaid(true);

        return borrowRecordRepository.save(record);
    }
}