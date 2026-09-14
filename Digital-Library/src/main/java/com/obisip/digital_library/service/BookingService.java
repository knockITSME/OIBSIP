package com.obisip.digital_library.service;

import com.obisip.digital_library.entity.Book;
import com.obisip.digital_library.entity.Booking;
import com.obisip.digital_library.entity.User;
import com.obisip.digital_library.repository.BookRepository;
import com.obisip.digital_library.repository.BookingRepository;
import com.obisip.digital_library.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public BookingService(BookingRepository bookingRepository,
                          BookRepository bookRepository,
                          UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public Booking createBooking(Long userId, Long bookId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (book.getAvailableQuantity() > 0) {
            throw new RuntimeException(
                    "Book is currently available. Advance booking is not required."
            );
        }

        List<Booking> existingBookings = bookingRepository.findByUserId(userId);

        for (Booking booking : existingBookings) {
            if (booking.getBook().getId().equals(bookId)
                    && "PENDING".equals(booking.getStatus())) {
                throw new RuntimeException(
                        "You already have a pending booking for this book."
                );
            }
        }

        Booking booking = new Booking();

        booking.setUser(user);
        booking.setBook(book);
        booking.setBookingDate(LocalDate.now());
        booking.setStatus("PENDING");

        return bookingRepository.save(booking);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public List<Booking> getUserBookings(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    public List<Booking> getPendingBookings() {
        return bookingRepository.findByStatus("PENDING");
    }
}