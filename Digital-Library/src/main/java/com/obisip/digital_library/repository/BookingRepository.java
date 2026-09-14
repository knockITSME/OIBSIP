package com.obisip.digital_library.repository;

import com.obisip.digital_library.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByUserId(Long userId);

    List<Booking> findByBookId(Long bookId);

    List<Booking> findByStatus(String status);
}