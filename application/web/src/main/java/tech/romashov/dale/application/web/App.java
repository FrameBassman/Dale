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
        App app = new App();
        SpringApplication application = new SpringApplication(App.class);
        application.setDefaultProperties(Collections.singletonMap("server.port", app.port()));
        application.run(args);
    }

    public String port() {
        if (System.getenv("PORT") == null  || System.getenv("PORT").equals("")) {
            return "8080";
        } else {
            return System.getenv("PORT");
        }
    }
}
