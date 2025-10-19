package com.task1.task1.service;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class CommandValidator {

	private static final List<String> BLOCKLIST = List.of(
			"rm ", "rm-", "sudo ", ";", "&&", "|", "||", ">", ">>", "<", "$(", "`", "&", "mkfs", ":(){:|:&};:", "dd ", "shutdown", "reboot", "poweroff", "init 0",
			"kill -9", ":(){:|:&};:");

	public void validate(String command) {
		if (command == null || command.isBlank()) {
			throw new IllegalArgumentException("Command must not be blank");
		}
		String normalized = command.trim();
		String lower = normalized.toLowerCase();
		for (String banned : BLOCKLIST) {
			if (lower.contains(banned)) {
				throw new IllegalArgumentException("Command contains unsafe token: " + banned);
			}
		}
		if (normalized.split("\\s+").length > 32) {
			throw new IllegalArgumentException("Command too long");
		}
	}
}


