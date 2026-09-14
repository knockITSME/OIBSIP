package com.obisip.digital_library.controller;

import com.obisip.digital_library.entity.Booking;
import com.obisip.digital_library.service.BookingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/book")
    public Booking createBooking(@RequestParam Long userId,
                                 @RequestParam Long bookId) {
        return bookingService.createBooking(userId, bookId);
    }

    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/user/{userId}")
    public List<Booking> getUserBookings(@PathVariable Long userId) {
        return bookingService.getUserBookings(userId);
    }

    @GetMapping("/pending")
    public List<Booking> getPendingBookings() {
        return bookingService.getPendingBookings();
    }
}