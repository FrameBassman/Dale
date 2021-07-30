package tech.romashov.dale.application.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
public class App {
    public String getGreeting() {
        return "Hello world.";
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(App.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", System.getProperty("PORT", "8080")));
        app.run(args);
    }
}
