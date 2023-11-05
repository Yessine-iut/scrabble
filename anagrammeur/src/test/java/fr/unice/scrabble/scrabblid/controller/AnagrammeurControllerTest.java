package fr.unice.scrabble.scrabblid.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
class AnagrammeurControllerTest {

    @Autowired
    AnagrammeurController anagrammeurController;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testDonnerAnagrammes() throws Exception {
        char[] lettresRequestBody = {'m','o','t','o','x','y','z'};
        String requestBody = objectMapper.writeValueAsString(lettresRequestBody);

        MvcResult result = this.mockMvc.perform(
                        post("/donnerAnagrammes")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper mapper = new ObjectMapper();

        ArrayList<String> anagrammes = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<ArrayList<String> >() {});

        // les mots que peuvent former les lettres données dans lettresRequestBody sont: "moto", "mot", "otto" et "tom"
        log.debug("lettres données en requestBody: "+String.valueOf(lettresRequestBody));
        log.debug("les mots que peuvent former les lettres données dans lettresRequestBody sont normalement:\n \"moto\", \"mot\", \"otto\" et \"tom\"\n");
        for(String anagramme : anagrammes)
            log.debug("anagramme récupéré dans la requête post: "+anagramme);

        assertEquals(4, anagrammes.size());
        assertTrue(anagrammes.contains("moto"));
        assertTrue(anagrammes.contains("mot"));
        assertTrue(anagrammes.contains("otto"));
        assertTrue(anagrammes.contains("tom"));
    }
}