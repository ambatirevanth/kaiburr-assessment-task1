package com.task1.task1.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.task1.task1.model.Task;

public interface TaskRepository extends MongoRepository<Task, String> {
	List<Task> findByNameContainingIgnoreCase(String namePart);
}


