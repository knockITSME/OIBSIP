package com.obisip.digital_library.repository;

import com.obisip.digital_library.entity.ContactQuery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactQueryRepository extends JpaRepository<ContactQuery, Long> {
}