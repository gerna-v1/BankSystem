package com.gerna_v1.banksystem;

import com.gerna_v1.banksystem.services.SessionManager;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication(scanBasePackages = "com.gerna_v1.banksystem")
public class BankSystem {

	@Autowired
	private SessionManager sessionManager;

	private static ApplicationContext context;

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(BankSystem.class);
		context = app.run(args);
		app.addListeners((event) -> {
			if (event instanceof org.springframework.context.event.ContextClosedEvent) {
				BankSystem bankSystem = context.getBean(BankSystem.class);
				bankSystem.cleanUp();
			}
		});
	}

	private void cleanUp() {
		System.out.println("Performing cleanup tasks...");
		sessionManager.deleteAllSessions();
	}
}