package com.obisip.digital_library.controller;

import com.obisip.digital_library.entity.BorrowRecord;
import com.obisip.digital_library.service.BorrowService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/borrow")
public class BorrowController {

    private final BorrowService borrowService;

    public BorrowController(BorrowService borrowService) {
        this.borrowService = borrowService;
    }

    // Issue a book
    @PostMapping("/issue/{userId}/{bookId}")
    public BorrowRecord issueBook(
            @PathVariable Long userId,
            @PathVariable Long bookId) {

        return borrowService.issueBook(userId, bookId);
    }

    // Return a book
    @PutMapping("/return/{borrowId}")
    public BorrowRecord returnBook(
            @PathVariable Long borrowId) {

        return borrowService.returnBook(borrowId);
    }

    // Get all borrow records - Admin
    @GetMapping
    public List<BorrowRecord> getAllBorrowRecords() {

        return borrowService.getAllBorrowRecords();
    }

    // Get borrow records for a particular user
    @GetMapping("/user/{userId}")
    public List<BorrowRecord> getUserBorrowRecords(
            @PathVariable Long userId) {

        return borrowService.getUserBorrowRecords(userId);
    }

    // Get one borrow record
    @GetMapping("/{borrowId}")
    public BorrowRecord getBorrowRecord(
            @PathVariable Long borrowId) {

        return borrowService.getBorrowRecordById(borrowId);
    }

    // Admin marks fine as paid
    @PutMapping("/fine-paid/{borrowId}")
    public BorrowRecord markFinePaid(
            @PathVariable Long borrowId) {

        return borrowService.markFinePaid(borrowId);
    }
}