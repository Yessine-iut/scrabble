package fr.unice.scrabble.scrabblid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;


import java.util.HashMap;

@Slf4j
@SpringBootApplication
public class AppariementApplication {
    public static void main(String[] args) {
        SpringApplication.run(AppariementApplication.class, args);
    }

    @Bean
    public static CommandLineRunner aGame() {
        return args -> {
            String urlAnagrammeur = "http://172.128.0.84:8084";
            String urlPartie = "http://172.128.0.81:8081";
            String urlJoueur = "http://172.128.0.82:8082";
            String urlAppariement = "http://172.128.0.83:8083";
            WebClient webJoueur = WebClient.create(urlJoueur);
            log.debug("Web client du Joueur créé sur url: " + urlJoueur);
            WebClient webAnagarammeur = WebClient.create(urlAnagrammeur);
            log.debug("Web client d'Anagarammeur créé sur url: " + urlAnagrammeur);
            WebClient webPartie = WebClient.create(urlPartie);
            log.debug("Web client de partie créé sur url: " + urlPartie);
            WebClient webAppariement = WebClient.create(urlAppariement);
            log.debug("Web client d'appariement créé sur url: " + urlAppariement);
            HashMap<Integer, Integer> joueurs = new HashMap<>();
            joueurs.put(1, 0);
            joueurs.put(2, 0);
            joueurs.put(3, 0);
            joueurs.put(4, 0);
            for (Integer numJoueur : joueurs.keySet()) {
                Integer integer = webJoueur.get()
                        .uri("/seConnecter")
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve().bodyToMono(Integer.class)
                        .block();
                log.debug("Joueur " + integer + " s'est connecté ");

                joueurs.put(numJoueur, integer);
                Thread.sleep(2000);
            }
            log.debug("Liste joueurs ID: " + joueurs);
            log.debug("Get ID Partie");

            int idPartie= webJoueur.get().uri("/getPartie/" + joueurs.get(1)).accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(Integer.class).block();
            log.debug("Distribution des chevalets");
            for (Integer numJoueur : joueurs.keySet()) {
                webJoueur.get().uri("/mettreAJourPlateau/" + joueurs.get(numJoueur)).accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(Void.class).block();
                Thread.sleep(1000);
                webJoueur.get().uri("/piocher/" + joueurs.get(numJoueur)).accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(Void.class).block();
                Thread.sleep(1000);
                webJoueur.get().uri("/passerSonTour/" + joueurs.get(numJoueur)).accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(Void.class).block();
                Thread.sleep(1000);
            }
            boolean finPartie=false;
            while(!finPartie) {
                for (Integer numJoueur : joueurs.keySet()) {
                    webJoueur.get().uri("/mettreAJourPlateau/" + joueurs.get(numJoueur)).accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(Void.class).block();
                    log.debug("Joueur " + joueurs.get(numJoueur) + ": requête mettre à jour plateau");
                    Thread.sleep(1000);
                    webJoueur.get().uri("/jouer/" + joueurs.get(numJoueur)).accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(Void.class).block();
                    log.debug("Joueur " + joueurs.get(numJoueur) + ": requête jouer mot");
                    Thread.sleep(1000);
                    webJoueur.get().uri("/piocher/" + joueurs.get(numJoueur)).accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(Void.class).block();
                    log.debug("Joueur " + joueurs.get(numJoueur) + ": requête piocher");
                    Thread.sleep(5000);
                    webJoueur.get().uri("/passerSonTour/" + joueurs.get(numJoueur)).accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(Void.class).block();
                    Thread.sleep(1000);
                    log.debug("Joueur " + joueurs.get(numJoueur) + ": requête passer son tour");
                }
    Thread.sleep(1000);
    finPartie=webPartie.get().uri("/finPartie/" + idPartie).accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(Boolean.class).block();
}
            webJoueur.get().uri("/afficherScore/").accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(Void.class).block();

            log.debug("ARRÊT PARTIE");
            webPartie.post().uri("/actuator/shutdown/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve().bodyToMono(String.class).block();
            webJoueur.post().uri("/actuator/shutdown/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve().bodyToMono(String.class).block();
            webAppariement.post().uri("/actuator/shutdown/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve().bodyToMono(String.class).block();
            webAnagarammeur.post().uri("/actuator/shutdown/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve().bodyToMono(String.class).block();
        };
    }

}