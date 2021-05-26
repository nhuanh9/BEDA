package com.example.datn.repository;

import com.example.datn.model.ITUTC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITUTCRepository extends JpaRepository<ITUTC, Long> {
}
