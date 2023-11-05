package fr.unice.scrabble.scrabblid.controller;

import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;

public class AppariementControllerTest {
    MockWebServer mockWebServer;

    @BeforeEach
    void setUp()throws Exception {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        //id = new Identification("Utilisateur factice",
         //       "http://"+mockWebServer.getHostName()+":"+mockWebServer.getPort());
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.close();
    }

}
