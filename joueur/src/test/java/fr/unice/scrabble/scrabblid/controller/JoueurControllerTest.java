package fr.unice.scrabble.scrabblid.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.scrabble.scrabblid.model.Joueur;
import fr.unice.scrabble.scrabblid.model.plateau.Plateau;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class JoueurControllerTest {

    @Autowired
    JoueurController joueurController;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    static Joueur joueur;

    @BeforeAll
    public static void setUp() {
        joueur = mock(Joueur.class);
        ArrayList<Character> chevalet = new ArrayList<>(Arrays.asList('m', 'o', 't', 'w', 'x', 'y','z'));
        Plateau pl = new Plateau();
        when(joueur.getPlateau()).thenReturn(pl);
        when(joueur.getIdPartie()).thenReturn(1);
        when(joueur.getIdJoueur()).thenReturn(1);
        when(joueur.getChevalet()).thenReturn(chevalet);
    }

    /**
     *  il faut run JoueurApplication ET AppariementApplication pour ce test
     * @throws Exception
     */
    @Test
    void testSeConnecter() throws Exception {
        MvcResult result = this.mockMvc.perform( MockMvcRequestBuilders
                        .get("/seConnecter")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        //vérifie que l'id du joueur retourné lors de la connexion soit non null et bien un entier
        assertTrue(result.getResponse().getContentAsString().matches("\\d*"));
    }

    @Test
    void testMettreAJourPlateau() throws Exception {

        MvcResult idJoueur = this.mockMvc.perform( MockMvcRequestBuilders
                        .get("/seConnecter")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        this.mockMvc.perform( MockMvcRequestBuilders
                        .get("/mettreAJourPlateau/" + idJoueur.getResponse().getContentAsString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void testPasserSonTour() throws Exception {
        MvcResult idJoueur = this.mockMvc.perform( MockMvcRequestBuilders
                        .get("/seConnecter")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        this.mockMvc.perform( MockMvcRequestBuilders
                        .get("/passerSonTour/" + idJoueur.getResponse().getContentAsString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void testPiocher() throws Exception {
        MvcResult idJoueur = this.mockMvc.perform( MockMvcRequestBuilders
                        .get("/seConnecter")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        this.mockMvc.perform( MockMvcRequestBuilders
                        .get("/piocher/" + idJoueur.getResponse().getContentAsString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void testAfficherScore() throws Exception {
        this.mockMvc.perform( MockMvcRequestBuilders
                        .get("/afficherScore")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void testJouer() throws Exception {
        MvcResult idJoueur = this.mockMvc.perform( MockMvcRequestBuilders
                        .get("/seConnecter")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        this.mockMvc.perform( MockMvcRequestBuilders
                        .get("/jouer/" + idJoueur.getResponse().getContentAsString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }
    @Test
    void testDistributionChevalet() throws Exception {
        HashMap<Integer, Integer> joueurs = new HashMap<>();
        joueurs.put(1, 0);
        joueurs.put(2, 0);
        joueurs.put(3, 0);
        joueurs.put(4, 0);
        MvcResult result;
        for (Integer numJoueur : joueurs.keySet()) {
            result = this.mockMvc.perform( MockMvcRequestBuilders
                            .get("/seConnecter")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
            String reponse= result.getResponse().getContentAsString();
            System.out.println(reponse);
            assertTrue(reponse.matches("\\d*"));
            joueurs.put(numJoueur, Integer.parseInt(reponse));
        }
        for (Integer numJoueur : joueurs.keySet()) {
            this.mockMvc.perform( MockMvcRequestBuilders
                            .get("/mettreAJourPlateau/"+joueurs.get(numJoueur))
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
             this.mockMvc.perform( MockMvcRequestBuilders
                            .get("/piocher/"+joueurs.get(numJoueur))
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
            this.mockMvc.perform( MockMvcRequestBuilders
                            .get("/passerSonTour/" + joueurs.get(numJoueur))
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
        }
    }
}