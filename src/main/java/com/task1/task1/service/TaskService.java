package com.task1.task1.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.task1.task1.model.Task;
import com.task1.task1.model.TaskExecution;
import com.task1.task1.repository.TaskRepository;

@Service
public class TaskService {

	private final TaskRepository taskRepository;
	private final CommandValidator commandValidator;

	public TaskService(TaskRepository taskRepository, CommandValidator commandValidator) {
		this.taskRepository = taskRepository;
		this.commandValidator = commandValidator;
	}

	public List<Task> getAll() {
		return taskRepository.findAll();
	}

	public Optional<Task> getById(String id) {
		return taskRepository.findById(id);
	}

	public List<Task> findByNameContains(String namePart) {
		if (!StringUtils.hasText(namePart)) {
			return List.of();
		}
		return taskRepository.findByNameContainingIgnoreCase(namePart);
	}

	public Task createOrUpdate(Task task) {
		commandValidator.validate(task.getCommand());
		return taskRepository.save(task);
	}

	public void delete(String id) {
		taskRepository.deleteById(id);
	}

	public TaskExecution runAndRecord(String taskId) throws IOException, InterruptedException {
		Task task = taskRepository.findById(taskId).orElseThrow(() -> new IllegalArgumentException("Task not found"));
		commandValidator.validate(task.getCommand());

		Instant start = Instant.now();

		ProcessBuilder builder = new ProcessBuilder();
		// Use shell for cross-platform simple commands. On Windows PowerShell, use powershell -Command
		if (isWindows()) {
			builder.command("powershell.exe", "-NoProfile", "-NonInteractive", "-Command", task.getCommand());
		} else {
			builder.command("/bin/sh", "-c", task.getCommand());
		}
		builder.redirectErrorStream(true);
		Process process = builder.start();
		StringBuilder output = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
			String line;
			while ((line = reader.readLine()) != null) {
				output.append(line).append(System.lineSeparator());
			}
		}
		int exit = process.waitFor();
		Instant end = Instant.now();

		TaskExecution execution = new TaskExecution();
		execution.setStartTime(start);
		execution.setEndTime(end);
		execution.setOutput(output.toString().trim());

		task.getTaskExecutions().add(execution);
		taskRepository.save(task);

		if (exit != 0) {
			throw new IllegalStateException("Command exited with non-zero code: " + exit);
		}

		return execution;
	}

	private boolean isWindows() {
		String os = System.getProperty("os.name");
		return os != null && os.toLowerCase().contains("win");
	}
}


