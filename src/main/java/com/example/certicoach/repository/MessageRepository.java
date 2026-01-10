package com.example.certicoach.repository;

import com.example.certicoach.model.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Long> {
}