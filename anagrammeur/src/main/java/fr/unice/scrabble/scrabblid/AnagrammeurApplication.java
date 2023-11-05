package fr.unice.scrabble.scrabblid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class AnagrammeurApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnagrammeurApplication.class, args);
    }
}