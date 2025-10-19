package com.task1.task1.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.task1.task1.model.Task;
import com.task1.task1.model.TaskExecution;
import com.task1.task1.service.InMemoryTaskService;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final InMemoryTaskService taskService;

    public TaskController(InMemoryTaskService taskService) {
        this.taskService = taskService;
    }

    // GET /tasks and GET /tasks?id=xxx
    @GetMapping
    public ResponseEntity<?> getTasks(@RequestParam(value = "id", required = false) String id) {
        if (id == null || id.isBlank()) {
            List<Task> all = taskService.getAll();
            return ResponseEntity.ok(all); // returns empty array if no tasks
        }
        return taskService.getById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Task with id " + id + " not found")));
    }

    // PUT /tasks
    @PutMapping
    public ResponseEntity<?> putTask(@RequestBody Task task) {
        try {
            Task saved = taskService.createOrUpdate(task);
            return ResponseEntity.ok(saved); // return full task JSON
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }

    // DELETE /tasks/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable String id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // GET /tasks/search?name=foo
    @GetMapping("/search")
    public ResponseEntity<?> searchByName(@RequestParam("name") String namePart) {
        List<Task> results = taskService.findByNameContains(namePart);
        if (results.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "No tasks found containing '" + namePart + "'"));
        }
        return ResponseEntity.ok(results);
    }

    // PUT /tasks/{id}/execute
    @PutMapping("/{id}/execute")
    public ResponseEntity<?> execute(@PathVariable String id) {
        try {
            TaskExecution exec = taskService.runAndRecord(id);
            return ResponseEntity.ok(exec);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        } catch (IOException | InterruptedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Execution failed: " + e.getMessage()));
        }
    }
}
