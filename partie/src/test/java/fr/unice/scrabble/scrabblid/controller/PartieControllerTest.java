package fr.unice.scrabble.scrabblid.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import fr.unice.scrabble.scrabblid.model.Partie;
import fr.unice.scrabble.scrabblid.model.plateau.Plateau;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PartieControllerTest {

    @Autowired
    PartieController partieController;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


    /**
     * test création partie, appariement du joueur à la partie, vérifier joueur actuel, obtenir le plateau et mettre fin au tour
     * @throws Exception
     */
    @Test
    void testDeroulerPartie() throws Exception {
        testCreerPartie();
        testApparierPartie();
        testVerifierJoueurActuel();
        testGetPlateau();
        testFinTour();
    }

    void testCreerPartie() throws Exception {
        int idJoueur = 1;
        String requestBody = objectMapper.writeValueAsString(idJoueur);

        this.mockMvc.perform(
                        post("/creerPartie/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andExpect(status().isCreated());
    }

    void testApparierPartie() throws Exception {
        int idPartie = 1;
        String requestBody = objectMapper.writeValueAsString(idPartie);

        this.mockMvc.perform(
                        post("/apparierPartie/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andExpect(status().isOk());
    }

    void testVerifierJoueurActuel() throws Exception {
        int idJoueur = 1;
        String requestBody = objectMapper.writeValueAsString(idJoueur);
        MvcResult result = this.mockMvc.perform( MockMvcRequestBuilders
                        .post("/verifierJoueurActuel/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals("true",result.getResponse().getContentAsString());
    }

    void testGetPlateau() throws Exception {
        MvcResult result = this.mockMvc.perform( MockMvcRequestBuilders
                        .get("/getPlateau/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    private void testFinTour() throws Exception {
        Plateau plateau=new Plateau();
        String requestBody = objectMapper.writeValueAsString(plateau);
        this.mockMvc.perform(
                        post("/finTour/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andExpect(status().isOk());
    }

}