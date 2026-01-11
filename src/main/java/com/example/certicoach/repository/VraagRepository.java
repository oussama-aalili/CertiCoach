package com.example.certicoach.repository;

import com.example.certicoach.model.Vraag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VraagRepository extends JpaRepository<Vraag, Long> {
}