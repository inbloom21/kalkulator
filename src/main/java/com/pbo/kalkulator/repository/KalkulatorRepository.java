package com.pbo.kalkulator.repository;

import com.pbo.kalkulator.model.Kalkulator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KalkulatorRepository extends JpaRepository<Kalkulator, Long> {
}